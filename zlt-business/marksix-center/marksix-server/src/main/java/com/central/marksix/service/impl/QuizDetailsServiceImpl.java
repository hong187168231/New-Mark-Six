package com.central.marksix.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.common.constant.RedisConstants;
import com.central.common.model.PageResult;
import com.central.common.model.QuizDetails;
import com.central.common.model.enums.SortEnum;
import com.central.common.model.enums.StatusEnum;
import com.central.common.redis.template.RedisRepository;
import com.central.common.service.impl.SuperServiceImpl;
import com.central.marksix.mapper.QuizDetailsMapper;
import com.central.marksix.service.IQuizDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 竞猜分类（三类）
 *
 * @author zlt
 * @date 2023-05-30 13:00:21
 */
@Slf4j
@Service
public class QuizDetailsServiceImpl extends SuperServiceImpl<QuizDetailsMapper, QuizDetails> implements IQuizDetailsService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public List<QuizDetails> findList(Map<String, Object> params){
        if(null == params){
            params = new HashMap<>();
        }
        params.put("status", StatusEnum.ONE_TRUE.getStatus());
        String redisKey = StrUtil.format(RedisConstants.SITE_QUIZDETAILS_LIST_KEY, MapUtils.getInteger(params,"quizId"),
                true== ObjectUtil.isEmpty(params.get("sortBy"))? SortEnum.ASC.getCode():MapUtils.getInteger(params,"sortBy"),
                StatusEnum.ONE_TRUE.getStatus());
        List<QuizDetails> list = (List<QuizDetails>)RedisRepository.get(redisKey);
        if (ObjectUtil.isNotEmpty(list)) {
            list = baseMapper.findList( params);
            RedisRepository.setExpire(redisKey, list, RedisConstants.EXPIRE_TIME_30_DAYS);
        }
        return list;
    }
}
