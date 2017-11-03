package com.yimei.hs.boot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.util.Reflections;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.entity.YingFayun;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;

@Data
public class Logutil<T> {


    public static final <T> void create(ObjectMapper om, OrderMapper orderMapper, LogService logService, T data, EntityType entityType) {

        //输出date的方法 和参数
        Long orderId = (Long) Reflections.getFieldValue(data, "orderId");
        Long id = (Long) Reflections.getFieldValue(data, "id");

        Order order = orderMapper.selectByPrimaryKey(orderId);
        try {
            logService.create(
                    new Log(orderId,
                            (Long) Reflections.getFieldValue(data, "hsId"),
                            order.getOwnerId(),
                            id,
                            entityType,
                            om.writeValueAsString(data)));
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return;
    }


    public static final <T> void createforConfig(ObjectMapper om, OrderMapper orderMapper, LogService logService, T data, EntityType entityType) {

        //输出date的方法 和参数
        Long orderId = (Long) Reflections.getFieldValue(data, "orderId");
        Long id = (Long) Reflections.getFieldValue(data, "id");

        Order order = orderMapper.selectByPrimaryKey(orderId);
        try {
            logService.create(
                    new Log(orderId,
                            id,
                            order.getOwnerId(),
                            id,
                            entityType,
                            om.writeValueAsString(data)));
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return;
    }


    public static final <T> void createforOrder(ObjectMapper om, OrderMapper orderMapper, LogService logService, T data, EntityType entityType) {

        //输出date的方法 和参数

        Long id = (Long) Reflections.getFieldValue(data, "id");
        Long ownerId = (Long) Reflections.getFieldValue(data, "ownerId");


        try {
            logService.create(
                    new Log(id,
                            id,
                            ownerId,
                            id,
                            entityType,
                            om.writeValueAsString(data)));
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return;
    }}
