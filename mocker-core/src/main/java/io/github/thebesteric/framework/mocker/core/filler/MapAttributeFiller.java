package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * MapAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 12:35
 * @since 1.0
 */
@Slf4j
public class MapAttributeFiller extends AbstractIteratorAttributeFiller {

    @Override
    public boolean match(ClassWarp classWarp) {
        return isMap(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {

        Map<Object, Object> map = new HashMap<>(16);

        ParameterizedType parameterizedType;
        try {
            // Perhaps a subclass of map
            parameterizedType = (ParameterizedType) field.getGenericType();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        ClassWarp keyType = new ClassWarp(Class.forName(actualTypeArguments[0].getTypeName()));
        ClassWarp valueType = new ClassWarp(Class.forName(actualTypeArguments[1].getTypeName()));
        Object mapKey, mapValue = null;

        for (int i = 0; i < getProperties().getIteratorLength(); i++) {
            for (AttributeFiller keyAttributeFiller : getAttributeFillers()) {
                if (keyAttributeFiller.match(keyType)) {
                    mapKey = keyAttributeFiller.mockValue(keyType.getClazz(), mockInstance, null);
                    for (AttributeFiller valueAttributeFiller : getAttributeFillers()) {
                        if (valueAttributeFiller.match(valueType)) {
                            mapValue = valueAttributeFiller.mockValue(valueType.getClazz(), mockInstance, null);
                            break;
                        }
                    }
                    map.put(mapKey, mapValue);
                    break;
                }
            }
        }

        field.set(mockInstance, map);
    }
}
