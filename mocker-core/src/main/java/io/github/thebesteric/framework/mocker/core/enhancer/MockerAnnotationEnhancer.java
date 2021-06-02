package io.github.thebesteric.framework.mocker.core.enhancer;

import io.github.thebesteric.framework.mocker.annotation.Mocker;
import io.github.thebesteric.framework.mocker.configuration.MockerProperties;
import io.github.thebesteric.framework.mocker.core.processor.InstanceProcessor;
import io.github.thebesteric.framework.mocker.utils.ReflectUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.List;

/**
 * MockerAnnotationEnhancer
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 18:10
 * @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class MockerAnnotationEnhancer implements BeanPostProcessor {

    private static final String SPRING_CGLIB_SEPARATOR = "$$";

    private final ConfigurableListableBeanFactory beanFactory;
    private final MockerProperties properties;
    private final List<InstanceProcessor> instanceProcessors;

    private final Enhancer enhancer = new Enhancer();

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (properties.isEnable()) {
            Class<?> beanClass = bean.getClass();
            String originClassName = getOriginClassName(bean.getClass());
            Class<?> originClass = Class.forName(originClassName);
            Mocker mocker = originClass.getAnnotation(Mocker.class);
            if (isMocker(mocker) && checkClassLegal(beanClass)) {
                return enhancer(beanName, bean, originClass, new MockerAnnotationInterceptor(instanceProcessors));
            }
        }
        return bean;
    }

    /**
     * 是否是符合条件的 Mocker
     *
     * @param mocker mocker
     * @return boolean
     * @author Eric
     * @date 2021/5/26 17:52
     */
    private boolean isMocker(Mocker mocker) {
        return mocker != null && mocker.enable();
    }

    /**
     * 检测 Class 类型的正确性
     * <p>
     * The class type must be public and not static and final
     *
     * @param clazz clazz
     * @return boolean
     * @author Eric
     * @date 2021/1/14 23:30
     */
    private boolean checkClassLegal(Class<?> clazz) {
        return ReflectUtils.isPublic(clazz) && !ReflectUtils.isStatic(clazz) && !ReflectUtils.isFinal(clazz);
    }

    /**
     * 获取 bean 的原始 class
     *
     * @param beanClass beanClass
     * @return java.lang.String
     * @author Eric
     * @date 2021/5/14 0:42
     */
    private String getOriginClassName(Class<?> beanClass) {
        String beanClassName = beanClass.getName();
        String originClassName = beanClassName;
        // Check if used Spring CGLIB. eg: Aspect
        if (beanClassName.contains(SPRING_CGLIB_SEPARATOR)) {
            originClassName = beanClassName.substring(0, beanClassName.indexOf(SPRING_CGLIB_SEPARATOR));
        }
        return originClassName;
    }

    /**
     * 创建代理
     *
     * @param beanName    beanName
     * @param bean        bean
     * @param originClass 原始类
     * @param callback    回调函数
     * @return java.lang.Object
     * @author Eric
     * @date 2021/5/26 17:58
     */
    private Object enhancer(String beanName, Object bean, Class<?> originClass, Callback callback) {
        enhancer.setSuperclass(originClass);
        enhancer.setCallback(callback);
        Object target = enhancer.create();
        beanFactory.registerSingleton(beanName, copyProperties(originClass, bean, target));
        return target;
    }

    /**
     * 属性拷贝
     *
     * @param originClass 原始类
     * @param source      原始对象
     * @param target      代理对象
     * @return java.lang.Object
     * @author Eric
     * @date 2021/5/26 17:57
     */
    private synchronized Object copyProperties(Class<?> originClass, Object source, Object target) {
        Class<?> currentClass = originClass;
        do {
            for (Field sourceField : currentClass.getDeclaredFields()) {
                sourceField.setAccessible(true);
                try {
                    sourceField.set(target, sourceField.get(source));
                } catch (Exception ex) {
                    log.debug(ex.getMessage());
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null && currentClass != Object.class);
        return target;
    }

}
