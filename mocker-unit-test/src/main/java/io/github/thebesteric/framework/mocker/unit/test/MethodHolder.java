package io.github.thebesteric.framework.mocker.unit.test;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * MethodHolder
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-05 16:25
 * @since 1.0
 */
@Getter
@Setter
public class MethodHolder {

    private Method method;
    private Object[] args;

    public MethodHolder(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public String getSignature() {
        return getMethodSignature() + ":" + getMethodParametersSignature();
    }

    public String getMethodSignature() {
        return ReflectUtils.getMethodSignature(method);
    }

    public String getMethodParametersSignature() {
        return ReflectUtils.getMethodParametersSignature(method);
    }

}
