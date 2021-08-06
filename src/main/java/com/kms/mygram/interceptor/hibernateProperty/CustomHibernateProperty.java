package com.kms.mygram.interceptor.hibernateProperty;

import com.kms.mygram.interceptor.HibernateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile("local")
public class CustomHibernateProperty implements HibernatePropertiesCustomizer {

    private final HibernateInterceptor hibernateInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.ejb.interceptor", hibernateInterceptor);
    }
}
