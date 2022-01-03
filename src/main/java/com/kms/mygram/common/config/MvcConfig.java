package com.kms.mygram.common.config;

import com.kms.mygram.common.interceptor.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class MvcConfig{

    @Value("${upload.path}")
    private String rootPath;

    @Profile("local")
    @Configuration
    @RequiredArgsConstructor
    public class LocalMvcConfig implements WebMvcConfigurer {

        private final RequestInterceptor requestInterceptor;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/file/**")
                    .addResourceLocations("file:///" + rootPath)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
        }
    }

    @Profile("prod")
    @Configuration
    public class ProdMvcConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/file/**")
                    .addResourceLocations("file:/" + rootPath)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }
    }
}
