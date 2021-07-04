package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.configuration.MockerProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 12:10
 * @since 1.0
 */
public abstract class AbstractAttributeFiller implements AttributeFiller, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    protected static final Map<Object, Map<Field, Integer>> RECURSION_INSTANCE_FIELD_MAP = new HashMap<>();

    protected int getInstanceFieldRecurseTimes(Object mockInstance, Field field) {
        Map<Field, Integer> fieldMap = RECURSION_INSTANCE_FIELD_MAP.getOrDefault(mockInstance, new HashMap<>(16));
        return fieldMap.getOrDefault(field, 0);
    }

    protected void addInstanceFieldRecurseTimes(Object mockInstance, Field field, int recurseTimes) {
        Map<Field, Integer> fieldMap = RECURSION_INSTANCE_FIELD_MAP.getOrDefault(mockInstance, new HashMap<>(16));
        fieldMap.put(field, recurseTimes);
        RECURSION_INSTANCE_FIELD_MAP.put(mockInstance, fieldMap);
    }


    /**
     * 是否是包装类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:43
     */
    public boolean isWrap(Class<?> clazz) {
        return ReflectUtils.isWrap(clazz);
    }

    /**
     * 是否是基本类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:44
     */
    public boolean isPrimitive(Class<?> clazz) {
        return ReflectUtils.isPrimitive(clazz);
    }

    /**
     * 是否是字符串类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/7/4 20:49
     */
    public boolean isString(Class<?> clazz) {
        return ReflectUtils.isString(clazz);
    }

    /**
     * 是否是 BigDecimal 类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/7/4 20:49
     */
    public boolean isBigDecimal(Class<?> clazz) {
        return ReflectUtils.isBigDecimal(clazz);
    }

    /**
     * 是否是复合类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:44
     */
    public boolean isComplex(Class<?> clazz) {
        return ReflectUtils.isComplex(clazz) && ComplexAttributeFiller.NonType.class != clazz;
    }

    /**
     * 是否是数组类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/6/9 18:00
     */
    public boolean isArray(Class<?> clazz) {
        return ReflectUtils.isArray(clazz);
    }

    /**
     * 是否是 List 类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/6/10 1:56
     */
    public boolean isList(Class<?> clazz) {
        return ReflectUtils.isList(clazz);
    }

    /**
     * 是否是 Map 类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/6/10 1:56
     */
    public boolean isMap(Class<?> clazz) {
        return ReflectUtils.isMap(clazz);
    }

    /**
     * 获取属性填充器
     *
     * @return Collection
     * @author Eric
     * @date 2021/6/10 12:28
     */
    protected Collection<AttributeFiller> getAttributeFillers() {
        Map<String, AttributeFiller> attributeFillerMap = this.applicationContext.getBeansOfType(AttributeFiller.class);
        return attributeFillerMap.values();
    }

    protected MockerProperties getProperties() {
        return this.applicationContext.getBean(MockerProperties.class);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
