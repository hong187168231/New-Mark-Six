package com.xxl.job.executor.service;

import com.central.common.model.PageResult;
import com.central.common.model.QuizOrders;
import com.central.common.model.Result;
import com.central.common.model.SysUser;
import com.central.common.service.ISuperService;

import java.util.List;
import java.util.Map;

public interface IQuizOrdersService extends ISuperService<QuizOrders> {


    /**
     * 获取会员订单列表
     * @return
     */
    public PageResult<QuizOrders> findList(Map<String, Object> params);

}
