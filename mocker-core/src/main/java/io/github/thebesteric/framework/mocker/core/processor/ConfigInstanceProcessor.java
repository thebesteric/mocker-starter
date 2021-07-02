package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.*;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import io.github.thebesteric.framework.mocker.core.filler.AttributeFiller;
import io.github.thebesteric.framework.mocker.core.filler.ComplexAttributeFiller;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ConfigInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-29 01:16
 * @since 1.0
 */
public class ConfigInstanceProcessor extends AbstractConstructorInstanceProcessor {

    public ConfigInstanceProcessor(List<AttributeFiller> attributeFillers) {
        super(attributeFillers);
    }

    @Override
    public boolean match(MockIt mockIt) {
        return mockIt.config().params().length > 0;
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        Class<?> returnCassType = method.getReturnType();
        Object mockInstance = newInstance(determineConstructor(returnCassType));

        MockItResponse mockItResponse = mockIt.config();
        MockItParam[] mockItParams = mockItResponse.params();

        List<Class<?>> usedFieldClasses = new ArrayList<>(16);
        for (MockItParam mockItParam : mockItParams) {
            String key = mockItParam.key();
            for (Field field : returnCassType.getDeclaredFields()) {
                ClassWarp classWarp = new ClassWarp(field);
                Object value;
                if (!ReflectUtils.isFinal(field) && field.getName().equals(key)) {
                    // process if simple attributes
                    value = mockItParam.value();
                    // process if complex attributes
                    if (StringUtils.isEmpty(String.valueOf(value))) {
                        value = mockItParam.clazz();
                        Class<?> clazz = (Class<?>) value;
                        if (ComplexAttributeFiller.NonType.class != clazz) {
                            // clazz is null if attribute clazz set Object[].class
                            // e.g: @MockItParam(key = "data", clazz = Order[].class)
                            if (clazz.isArray()) {
                                clazz = clazz.getComponentType();
                                classWarp.setClazz(clazz);
                                classWarp.setArray(true);
                            }
                            Constructor<?> constructor = determineConstructor(clazz);
                            value = newInstance(constructor);
                            MockItAttr[] mockItAttrs = mockItParam.attrs();
                            for (MockItAttr mockItAttr : mockItAttrs) {
                                String attrKey = mockItAttr.key();
                                Object attrValue = mockItAttr.value();
                                Class<?> attrClass = mockItAttr.clazz();
                                for (Field attrField : value.getClass().getDeclaredFields()) {
                                    ClassWarp attrClassWarp = new ClassWarp(attrField);
                                    if (attrField.getName().equals(attrKey)) {
                                        if (StringUtils.isEmpty(String.valueOf(attrValue)) && attrClass != ComplexAttributeFiller.NonType.class) {
                                            Constructor<?> attrConstructor = determineConstructor(attrClass);
                                            attrValue = newInstance(attrConstructor);
                                        }
                                        value = populateInstance(value, attrClassWarp, attrValue);
                                    }
                                }
                            }
                        }
                    }
                    mockInstance = populateInstance(mockInstance, classWarp, value);
                    // record the fields that have been used
                    usedFieldClasses.add(field.getType());
                }
            }
        }

        // populate random value for unused fields
        for (Field field : returnCassType.getDeclaredFields()) {
            if (!ReflectUtils.isFinal(field) && !usedFieldClasses.contains(field.getType())
                    && !field.isAnnotationPresent(MockIgnore.class)) {
                ClassWarp unusedClassWarp = new ClassWarp(field);
                mockInstance = populateInstance(mockInstance, unusedClassWarp, null);
            }
        }

        return mockInstance;
    }

    @Override
    public int order() {
        return InstanceProcessor.CONFIG_PROCESSOR_ORDER;
    }

}
