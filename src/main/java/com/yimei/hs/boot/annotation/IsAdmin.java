package com.yimei.hs.boot.annotation;


import java.lang.annotation.*;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsAdmin {
}

