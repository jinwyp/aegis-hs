package com.yimei.hs.boot.ext.annotation;

import java.lang.annotation.*;

/**
 * Created by hary on 2017/9/26.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logined {
    boolean isAdmin() default false;
}
