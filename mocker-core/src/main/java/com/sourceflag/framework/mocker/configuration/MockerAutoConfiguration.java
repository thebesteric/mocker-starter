package com.sourceflag.framework.mocker.configuration;

import com.sourceflag.framework.mocker.core.MockerCoreInitialization;
import com.sourceflag.framework.mocker.core.enhancer.MockerAnnotationEnhancer;
import com.sourceflag.framework.mocker.core.filler.AttributeFiller;
import com.sourceflag.framework.mocker.core.filler.PrimitiveAttributeFiller;
import com.sourceflag.framework.mocker.core.filler.StringAttributeFiller;
import com.sourceflag.framework.mocker.core.filler.WarpAttributeFiller;
import com.sourceflag.framework.mocker.core.processor.*;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Comparator;
import java.util.List;

/**
 * MockerAutoConfiguration
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 15:11
 * @since 1.0
 */
@Configuration
@EnableAsync
@Import(MockerCoreInitialization.class)
@ConditionalOnBean(MockerMarker.class)
@EnableConfigurationProperties(MockerProperties.class)
public class MockerAutoConfiguration {

    @Bean
    public MockerAnnotationEnhancer mockerAnnotationEnhancer(ConfigurableListableBeanFactory beanFactory,
                                                             MockerProperties properties, List<InstanceProcessor> instanceProcessors) {
        instanceProcessors.sort((o1, o2) -> {
            if (o1.order() != o2.order()) {
                return o1.order() > o2.order() ? 1 : -1;
            }
            return 0;
        });
        return new MockerAnnotationEnhancer(beanFactory, properties, instanceProcessors);
    }

    @Bean
    public AttributeFiller primitiveAttributeFiller() {
        return new PrimitiveAttributeFiller();
    }

    @Bean
    public AttributeFiller warpAttributeFiller() {
        return new WarpAttributeFiller();
    }

    @Bean
    public AttributeFiller stringAttributeFiller() {
        return new StringAttributeFiller();
    }

    @Bean
    public InstanceProcessor mockInstanceProcessor() {
        return new MockInstanceProcessor();
    }

    @Bean
    public InstanceProcessor constructorInstanceProcessor(List<AttributeFiller> attributeFillers) {
        return new ConstructorInstanceProcessor(attributeFillers);
    }

    @Bean
    public InstanceProcessor remoteTargetInstanceProcessor() {
        return new RemoteTargetInstanceProcessor();
    }

    @Bean
    public InstanceProcessor localTargetInstanceProcessor() {
        return new LocalTargetInstanceProcessor();
    }

}
