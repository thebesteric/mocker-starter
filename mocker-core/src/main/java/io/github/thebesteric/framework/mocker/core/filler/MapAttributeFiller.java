package io.github.thebesteric.framework.mocker.core.filler;

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
public class MapAttributeFiller extends AbstractIteratorAttributeFiller {

    @Override
    public boolean match(Class<?> clazz) {
        return isMap(clazz);
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {

        Map<Object, Object> map = new HashMap<>(16);

        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        Class<?> keyType = Class.forName(actualTypeArguments[0].getTypeName());
        Class<?> valueType = Class.forName(actualTypeArguments[1].getTypeName());
        Object mapKey, mapValue = null;

        for (int i = 0; i < getProperties().getIteratorLength(); i++) {
            for (AttributeFiller keyAttributeFiller : getAttributeFillers()) {
                if (keyAttributeFiller.match(keyType)) {
                    mapKey = keyAttributeFiller.mockValue(keyType, mockInstance, null);
                    for (AttributeFiller valueAttributeFiller : getAttributeFillers()) {
                        if (valueAttributeFiller.match(valueType)) {
                            mapValue = valueAttributeFiller.mockValue(valueType, mockInstance, null);
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

    @Override
    public Object mockValue(Class<?> clazz, Object instance, Object value) throws Throwable {
        return super.mockValue(clazz, instance, value);
    }
}
