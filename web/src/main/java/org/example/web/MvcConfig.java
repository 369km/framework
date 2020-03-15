package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.Constant;
import org.example.data.service.ObjectMapperFactory;
import org.example.web.interceptor.LoggingInterceptor;
import org.example.web.interceptor.TokenInterceptor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor());
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns(Constant.API + "/**")
                .excludePathPatterns(Constant.API + "/user/login")
                .excludePathPatterns(Constant.API + "/user/captcha");
    }

    @Bean
    protected LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    protected TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = ObjectMapperFactory.OBJECTMAPPERAWALYS.getInstance();
        jacksonConverter.setObjectMapper(mapper);
        converters.add(jacksonConverter);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ObjectMapper objectMapper = getObjectMapper();
        if (objectMapper != null) {
            if (event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext) {
                for (HttpMessageConverter converter : getMessageConverters()) {
                    if (converter instanceof MappingJackson2HttpMessageConverter) {
                        ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
                    }
                }
            }
        }
    }

    /**
     * 若需要自定义objectMapper配置,请重写此方法
     *
     * @return
     */
    private ObjectMapper getObjectMapper() {
        return null;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setPageParameterName("index");
        resolver.setSizeParameterName("size");
        resolver.setMaxPageSize(Constant.PAGE_MAX_SIZE);
        PageRequest pageable = PageRequest.of(0, Constant.PAGE_DEFAULT_SIZE, new Sort(Sort.Direction.DESC, "modifiedTime"));
        resolver.setFallbackPageable(pageable);
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
