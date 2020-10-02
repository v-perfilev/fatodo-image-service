package com.persoff68.fatodo.config.aop.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListCacheEvictMethod {

    String cacheName();

    String keyCacheName();

    String key();

}