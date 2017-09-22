package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.entity.dto.ying.PageYingOrderDTO;
import com.yimei.hs.mapper.YingOrderConfigMapper;
import com.yimei.hs.mapper.YingOrderMapper;
import com.yimei.hs.mapper.YingOrderPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingOrderService {

    private static final Logger logger = LoggerFactory.getLogger(YingOrderService.class);

    @Autowired
    private YingOrderMapper yingOrderMapper;

    @Autowired
    private YingOrderPartyMapper yingOrderPartyMapper;

    @Autowired
    private YingOrderConfigMapper yingOrderConfigMapper;

    /**
     * 获取一页订单
     * @param pageYingOrderDTO
     * @return
     */
    public Page<YingOrder> getPage(PageYingOrderDTO pageYingOrderDTO) {
        return yingOrderMapper.getPage(pageYingOrderDTO);
    }

    /**
     * 获取指定订单
     * @param id
     * @return
     */
    public YingOrder findOne(long id) {
        return yingOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建订单
     * @param order
     * @return
     */
    public int create(YingOrder order) {
        return yingOrderMapper.insert(order);
    }

    /**
     *  更新订单
     * @param record
     * @return
     */
    public int update(YingOrder record) {
        return yingOrderMapper.updateByPrimaryKey(record);
    }

    @Autowired
    private YingLogService yingLogService;
}
