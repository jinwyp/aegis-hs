package com.yimei.hs.boot.support;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Java8TimeModule extends SimpleModule {
    public Java8TimeModule() {
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        this.addSerializer(LocalDate.class, new LocalDateSerializer());

        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer());

    }

}
