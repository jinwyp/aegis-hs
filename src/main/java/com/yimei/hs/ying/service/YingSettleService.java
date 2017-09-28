package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
public class YingSettleService {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleService.class);


    @Autowired
    YingFayunMapper yingFayunMapper;

    @Autowired
    YingSettleUpstreamMapper yingSettleUpstreamMapper;

    @Autowired
    YingSettleDownstreamMapper yingSettleDownstreamMapper;

    @Autowired
    YingSettleDownstreamMapMapper yingSettleDownstreamMapMapper;

    @Autowired
    YingSettleTrafficMapper yingSettleTrafficMapper;

    /**
     *  find settles by orderid
     * @param orderId
     * @return
     */
    public List<YingSettleUpstream> getAllUpStreamSettles(Long orderId) {
        return yingSettleUpstreamMapper.selectByOrderId(orderId);
    }

    @Autowired
    private YingLogService yingLogService;

    /**
     *
     * @param pageYingSettleTrafficDTO
     * @return
     */
    public Page<YingSettleTraffic> getPageTraffic(PageYingSettleTrafficDTO pageYingSettleTrafficDTO) {
        return yingSettleTrafficMapper.getPage(pageYingSettleTrafficDTO);
    }

    /**
     *
     * @param pageYingSettleUpstreamDTO
     * @return
     */
    public Page<YingSettleUpstream> getPageUpstream(PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {
        return yingSettleUpstreamMapper.getPage(pageYingSettleUpstreamDTO);
    }

    /**
     *
     * @param pageYingSettleDownstreamDTO
     * @return
     */
    public Page<YingSettleDownstream> getPageDownstream(PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO) {
        return yingSettleDownstreamMapper.getPage(pageYingSettleDownstreamDTO);
    }

    public YingSettleTraffic findTraffic(long id) {
        return yingSettleTrafficMapper.selectByPrimaryKey(id);
    }

    public int createTraffic(YingSettleTraffic yingSettleTraffic) {
        return yingSettleTrafficMapper.insert(yingSettleTraffic);
    }

    public int createUpstream(YingSettleUpstream yingSettleUpstream) {
        return yingSettleUpstreamMapper.insert(yingSettleUpstream);
    }

    /**
     * 下游结算
     * @param yingSettleDownstream
     * @return
     */
    public int createDownstream(YingSettleDownstream yingSettleDownstream) {
        int rtn = yingSettleDownstreamMapper.insert(yingSettleDownstream);
        if (rtn != 1) {
            return 0;
        }

        // 查询出当前订单的所有与付款的对应记录
        List<YingSettleDownstreamMap> huankuanMap = yingSettleDownstreamMapMapper.loadAll(yingSettleDownstream.getOrderId());

        // 当前订单的所有发运记录
        List<YingFayun> hukuanList = yingFayunMapper.getList(yingSettleDownstream.getOrderId());

        // 待添加的记录 todo
        List<YingSettleDownstreamMap> toAdd = new ArrayList<>();

        //
        for ( YingSettleDownstreamMap item: toAdd) {
            yingSettleDownstreamMapMapper.insert(item);
        }

        return rtn;
    }

    /**
     * 重建 下游结算-发运-映射
     * @param orderId
     * @return
     */
    public int createDownstreamMap(long orderId) {
        // todo
        return 1;
    }

    /**
     *
     * @param id
     * @return
     */
    public YingSettleDownstream findDownstream(long id) {
        return yingSettleDownstreamMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public YingSettleUpstream findUpstream(long id) {
        return yingSettleUpstreamMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @param yingSettleUpstream
     * @return
     */
    public int updateUpstream(YingSettleUpstream yingSettleUpstream) {
        return yingSettleUpstreamMapper.updateByPrimaryKeySelective(yingSettleUpstream);
    }

    /**
     *
     * @param yingSettleTraffic
     * @return
     */
    public int udpateTraffic(YingSettleTraffic yingSettleTraffic) {
        return yingSettleTrafficMapper.updateByPrimaryKeySelective(yingSettleTraffic);
    }

    /**
     *
     * @param yingSettleDownstream
     * @return
     */
    public int updateDownstream(YingSettleDownstream yingSettleDownstream) {
        return yingSettleDownstreamMapper.updateByPrimaryKeySelective(yingSettleDownstream);
    }

    /**
     * 重建所有对应关系
     * @param orderId
     * @return
     */

    /**
     *  逻辑删除
     * @param id
     * @return
     */
    public int deleteDownstream(long id) {
        // todo 如何删除对应关系
        return yingSettleDownstreamMapper.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int deleteTraffic(long id) {
        return yingSettleTrafficMapper.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int deleteUpstream(long id) {
        return yingSettleUpstreamMapper.delete(id);
    }
}
