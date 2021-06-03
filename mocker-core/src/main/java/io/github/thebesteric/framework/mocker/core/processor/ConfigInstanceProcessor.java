package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.annotation.MockItAttr;
import io.github.thebesteric.framework.mocker.annotation.MockItParam;
import io.github.thebesteric.framework.mocker.annotation.MockItResponse;
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
                Object value;
                if (field.getName().equals(key)) {
                    // process if simple attributes
                    value = mockItParam.value();
                    // process if complex attributes
                    if (StringUtils.isEmpty(String.valueOf(value))) {
                        value = mockItParam.clazz();
                        Class<?> clazz = (Class<?>) value;
                        if (ComplexAttributeFiller.NonValue.class != clazz) {
                            Constructor<?> constructor = determineConstructor(clazz);
                            value = newInstance(constructor);
                            MockItAttr[] mockItAttrs = mockItParam.attrs();
                            for (MockItAttr mockItAttr : mockItAttrs) {
                                String attrKey = mockItAttr.key();
                                Object attrValue = mockItAttr.value();
                                Class<?> attrClass = mockItAttr.clazz();
                                for (Field attrField : value.getClass().getDeclaredFields()) {
                                    if (attrField.getName().equals(attrKey)) {
                                        if (StringUtils.isEmpty(String.valueOf(attrValue)) && attrClass != ComplexAttributeFiller.NonValue.class) {
                                            Constructor<?> attrConstructor = determineConstructor(attrClass);
                                            attrValue = newInstance(attrConstructor);
                                        }
                                        value = populateInstance(value, attrField, attrValue);
                                    }
                                }
                            }
                        }
                    }
                    mockInstance = populateInstance(mockInstance, field, value);
                    // record the fields that have been used
                    usedFieldClasses.add(field.getType());
                }
            }
        }

        // populate random value for unused fields
        for (Field field : returnCassType.getDeclaredFields()) {
            if (!usedFieldClasses.contains(field.getType())) {
                mockInstance = populateInstance(mockInstance, field, null);
            }
        }

        return mockInstance;
    }

    @Override
    public int order() {
        return 3;
    }

}