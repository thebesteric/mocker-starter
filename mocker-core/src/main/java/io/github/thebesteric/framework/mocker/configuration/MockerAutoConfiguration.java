package io.github.thebesteric.framework.mocker.configuration;

import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;
import io.github.thebesteric.framework.mocker.commons.utils.HttpUtils;
import io.github.thebesteric.framework.mocker.core.MockerCoreInitialization;
import io.github.thebesteric.framework.mocker.core.enhancer.MockerAnnotationEnhancer;
import io.github.thebesteric.framework.mocker.core.filler.*;
import io.github.thebesteric.framework.mocker.core.processor.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

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

    @Bean(name = "mockerCacheUtils")
    public CacheUtils cacheUtils(MockerProperties properties) {
        CacheUtils.CacheConfiguration config = CacheUtils.CacheConfiguration.builder()
                .expireAfterWrite(properties.getCache().getExpireAfterWrite())
                .expireAfterAccess(properties.getCache().getExpireAfterAccess()).build();
        return new CacheUtils(config);
    }

    @Bean(name = "mockerHttpUtils")
    public HttpUtils httpUtils() {
        return HttpUtils.getInstance();
    }

    // for AttributeFiller

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
    public AttributeFiller arrayAttributeFiller() {
        return new ArrayAttributeFiller();
    }

    @Bean
    public AttributeFiller listAttributeFiller() {
        return new ListAttributeFiller();
    }

    @Bean
    public AttributeFiller mapAttributeFiller() {
        return new MapAttributeFiller();
    }

    @Bean
    public AttributeFiller complexAttributeFiller() {
        return new ComplexAttributeFiller();
    }

    // for InstanceProcessor

    @Bean
    public InstanceProcessor mockInstanceProcessor(@Qualifier("mockerCacheUtils") CacheUtils cacheUtils) {
        return new MockInstanceProcessor(cacheUtils);
    }

    @Bean
    public InstanceProcessor defaultInstanceProcessor(CacheUtils cacheUtils, List<AttributeFiller> attributeFillers) {
        return new DefaultInstanceProcessor(cacheUtils, attributeFillers);
    }

    @Bean
    public InstanceProcessor remoteTargetInstanceProcessor(CacheUtils cacheUtils, @Qualifier("mockerHttpUtils") HttpUtils httpUtils) {
        return new RemoteTargetInstanceProcessor(cacheUtils, httpUtils);
    }

    @Bean
    public InstanceProcessor localTargetInstanceProcessor(CacheUtils cacheUtils, MockerProperties properties) {
        return new LocalTargetInstanceProcessor(cacheUtils, properties);
    }

    @Bean
    public InstanceProcessor configInstanceProcessor(CacheUtils cacheUtils, List<AttributeFiller> attributeFillers) {
        return new ConfigInstanceProcessor(cacheUtils, attributeFillers);
    }

}
