package ru.gritandrey.gateway.config;

import org.springframework.beans.BeansException;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


public class ErrorHack implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext.containsBean("zuulHandlerMapping")) {
            applicationContext.getBean(ZuulHandlerMapping.class).setErrorController(null);
        }
    }
}
