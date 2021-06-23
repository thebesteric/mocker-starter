package io.github.thebesteric.framework.mocker.core;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.configuration.MockerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.NonNull;

/**
 * MockerInitialization
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:01
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class MockerInitialization implements SmartLifecycle, ApplicationContextAware {

    protected boolean isRunning = false;

    protected GenericApplicationContext applicationContext;

    protected final MockerProperties properties;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * 启动
     *
     * @author Eric
     * @date 2021/5/28 23:55
     */
    @Override
    public abstract void start();

    @Override
    public int getPhase() {
        return 0;
    }

    public String getProjectPath() {
        return ReflectUtils.getProjectPath();
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (GenericApplicationContext) applicationContext;
    }

    protected <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
