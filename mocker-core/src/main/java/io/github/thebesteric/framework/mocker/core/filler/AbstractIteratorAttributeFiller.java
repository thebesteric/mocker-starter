package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * AbstractIteratorAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 18:49
 * @since 1.0
 */
public abstract class AbstractIteratorAttributeFiller extends AbstractAttributeFiller {

    @Override
    protected Collection<AttributeFiller> getAttributeFillers() {
        Collection<AttributeFiller> attributeFillers = new ArrayList<>(2);
        attributeFillers.add(applicationContext.getBean(StringAttributeFiller.class));
        attributeFillers.add(applicationContext.getBean(WarpAttributeFiller.class));
        attributeFillers.add(applicationContext.getBean(ComplexAttributeFiller.class));
        return attributeFillers;
    }

    /**
     * 获取循环次数
     *
     * @param field 字段
     * @return int
     */
    protected int getRepeatLength(Field field) {
        int repeatlength = getProperties().getIteratorLength();
        if (field.isAnnotationPresent(MockProp.class)) {
            MockProp mockProp = field.getAnnotation(MockProp.class);
            repeatlength = mockProp.length() > 0 ? mockProp.length() : repeatlength;
        }
        return repeatlength;
    }

    public Object mockPropValue(Class<?> clazz, String value) throws Throwable {
        Object object = value;
        if (clazz == Byte.class || clazz == byte.class) {
            object = Byte.parseByte(value);
        } else if (clazz == Short.class || clazz == short.class) {
            object = Short.parseShort(value);
        } else if (clazz == Integer.class || clazz == int.class) {
            object = Integer.parseInt(value);
        } else if (clazz == Long.class || clazz == long.class) {
            object = Long.parseLong(value);
        } else if (clazz == Float.class || clazz == float.class) {
            object = Float.parseFloat(value);
        } else if (clazz == Double.class || clazz == double.class) {
            object = Double.parseDouble(value);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            object = Boolean.parseBoolean(value);
        }
        return object;
    }

    /**
     * 填充 MockProp 的值
     *
     * @param object   要填充的对象
     * @param sequence MockProp 值对应的当前的位置
     */
    public void populateMockPropValue(Object object, int sequence) throws Throwable {
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            if (!ReflectUtils.isFinal(declaredField) && declaredField.isAnnotationPresent(MockProp.class)) {
                MockProp mockProp = declaredField.getAnnotation(MockProp.class);
                String[] valueArr = mockProp.value();
                if (valueArr.length > 0) {
                    int index = Math.min(sequence, valueArr.length - 1);
                    Object propValue = mockPropValue(declaredField.getType(), valueArr[index]);
                    ReflectUtils.setFieldValue(object, declaredField, propValue);
                }
            }
        }
    }

    /**
     * 获取数组中实际的 Class 类型
     *
     * @param field field
     */
    protected Class<?> getActualType(Field field) throws ClassNotFoundException {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        Class<?> actualClassType = Object.class;
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            Type actualTypeArgument = actualTypeArguments[actualTypeArguments.length - 1];
            actualClassType = Class.forName(actualTypeArgument.getTypeName());
        }
        return actualClassType;
    }

}
