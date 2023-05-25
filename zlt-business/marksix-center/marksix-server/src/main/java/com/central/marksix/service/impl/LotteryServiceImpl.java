package com.central.marksix.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.common.model.Lottery;
import com.central.common.model.PageResult;
import com.central.common.model.SiteLottery;
import com.central.common.model.SysUser;
import com.central.common.model.enums.StatusEnum;
import com.central.common.service.impl.SuperServiceImpl;
import com.central.marksix.entity.vo.SiteLotteryVO;
import com.central.marksix.mapper.LotteryMapper;
import com.central.marksix.service.ILotteryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 彩种表
 *
 * @author zlt
 * @date 2023-05-09 19:57:30
 */
@Slf4j
@Service
public class LotteryServiceImpl extends SuperServiceImpl<LotteryMapper, Lottery> implements ILotteryService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public List<SiteLotteryVO> findList(Map<String, Object> params){

        params.put("isDisplay", StatusEnum.ONE_TRUE.getStatus());
        return baseMapper.findList( params);
    }
    @Override
    public void updateLotteryStatus(Integer lotteryId, Integer stauts) {
        this.lambdaUpdate().eq(Lottery::getId, lotteryId)
                .setSql("`status` = "+ stauts)
                .update();
    }
}
