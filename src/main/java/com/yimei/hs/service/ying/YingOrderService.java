package com.yimei.hs.service.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.Party;
import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.entity.YingOrderConfig;
import com.yimei.hs.entity.YingOrderParty;
import com.yimei.hs.entity.dto.ying.PageYingOrderDTO;
import com.yimei.hs.mapper.YingOrderConfigMapper;
import com.yimei.hs.mapper.YingOrderMapper;
import com.yimei.hs.mapper.YingOrderPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

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

        // 插入业务线
        int rtn = yingOrderMapper.insert(order);

        List<YingOrderConfig> configList = order.getOrderConfigList();

        List<YingOrderParty> partyList = order.getOrderPartyList();

        // 插入参与方
        if (partyList != null ) {
            partyList.forEach(new Consumer<YingOrderParty>() {
                @Override
                public void accept(YingOrderParty yingOrderParty) {
                    yingOrderParty.setOrderId(order.getId());
                    yingOrderPartyMapper.insert(yingOrderParty);
                }
            });
        }

        // 插入核算月配置
        if (partyList != null) {
            configList.forEach(new Consumer<YingOrderConfig>() {
                @Override
                public void accept(YingOrderConfig yingOrderConfig) {
                    yingOrderConfig.setOrderId(order.getId());
                    yingOrderConfigMapper.insert(yingOrderConfig);
                }
            });
        }

        return rtn;
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
