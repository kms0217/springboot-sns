package com.kms.mygram.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@Slf4j
public class HibernateInterceptor extends EmptyInterceptor {

    private ThreadLocal<Long> count = new ThreadLocal<>();

    public void startCount() {
        count.set(0L);
    }

    public Long getCount() {
        return count.get();
    }

    public void clearCount() {
        count.remove();
    }

    @Override
    public String onPrepareStatement(String sql) {
        Long c = count.get();
        if (c != null)
            count.set(c + 1);
        return super.onPrepareStatement(sql);
    }
}
