package com.xxl.job.executor.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.common.model.WnData;
import com.central.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 开奖数据
 * 
 * @author zlt
 * @date 2023-05-09 18:39:54
 */
@Mapper
public interface WnDataMapper extends SuperMapper<WnData> {
    WnData lastOneWnData(Integer lotteryId);
}
