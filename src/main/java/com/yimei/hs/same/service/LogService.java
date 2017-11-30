package com.yimei.hs.same.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.dto.PageLogDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.mapper.LogMapper;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.util.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/21.
 */
@Service
@Transactional(readOnly = true)
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogMapper logMapper;

    @Autowired
    OrderMapper orderMapper;


    @Autowired
    ObjectMapper om;


    public void log(Long orderId, Long hsId, String entityType, Long entityId, String memo) {
    }

    public Page<Log> getPage(PageLogDTO pageLogDTO) {
        return logMapper.getPage(pageLogDTO);
    }

//    @Transactional(readOnly = false)
//    public int create(Log log,EntityType entityType) {
//        this.create(log,entityType);
//        return logMapper.insertSelective(log);
//    }

    @Transactional(readOnly = false)
    public Log findOne(Long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public  int createLog(Object data, EntityType entityType) {

        //输出date的方法 和参数
        Long orderId = (Long) Reflections.getFieldValue(data, "orderId");
        Long id = (Long) Reflections.getFieldValue(data, "id");

        Order order = orderMapper.selectByPrimaryKey(orderId);
        try {
          int  rtn=  logMapper.insertSelective(
                    new Log(orderId,
                            (Long) Reflections.getFieldValue(data, "hsId"),
                            order.getOwnerId(),
                            id,
                            entityType,
                            om.writeValueAsString(data)));

            return rtn;
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return  0;
    }

    @Transactional(readOnly = false)
    public int  createforConfig(Object data, EntityType entityType) {
        int rtn = 0;
        //输出date的方法 和参数
        Long orderId = (Long) Reflections.getFieldValue(data, "orderId");
        Long id = (Long) Reflections.getFieldValue(data, "id");

        Order order = orderMapper.selectByPrimaryKey(orderId);
        try {
           rtn=  logMapper.insertSelective(
                    new Log(orderId,
                            id,
                            order.getOwnerId(),
                            id,
                            entityType,
                            om.writeValueAsString(data)));
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return rtn;
    }

    @Transactional(readOnly = false)
    public int  createforOrder(Object data, EntityType entityType) {

        int rtn = 0;

        Long id = (Long) Reflections.getFieldValue(data, "id");
        Long ownerId = (Long) Reflections.getFieldValue(data, "ownerId");


        try {
            rtn=  logMapper.insertSelective(
                    new Log(id,
                            id,
                            ownerId,
                            id,
                            entityType,
                            om.writeValueAsString(data)));
        } catch (Exception e) {

            System.out.printf("error" + e.getMessage());
        }
        return  rtn;
    }



}
