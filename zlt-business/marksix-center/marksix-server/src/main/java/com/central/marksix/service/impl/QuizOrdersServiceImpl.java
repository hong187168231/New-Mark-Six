package com.central.marksix.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.common.constant.MarksixConstants;
import com.central.common.constant.RedisConstants;
import com.central.common.model.*;
import com.central.common.model.enums.*;
import com.central.common.redis.lock.RedissLockUtil;
import com.central.common.redis.template.RedisRepository;
import com.central.common.service.impl.SuperServiceImpl;
import com.central.common.utils.SnowflakeIdWorker;
import com.central.marksix.entity.dto.QuizOrdersDto;
import com.central.marksix.entity.dto.QuizSubordersDto;
import com.central.marksix.entity.vo.SiteLotteryVo;
import com.central.marksix.entity.vo.StatiQuizOrdersVo;
import com.central.marksix.mapper.QuizOrdersMapper;
import com.central.marksix.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class QuizOrdersServiceImpl extends SuperServiceImpl<QuizOrdersMapper, QuizOrders> implements IQuizOrdersService {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IMoneyLogService moneyLogService;
    @Autowired
    private ILotteryService lotteryService;
    @Autowired
    private IQuizSubordersService quizSubordersService;

    @Override
    public PageResult<QuizOrders> findList(Map<String, Object> params) {
        Page<QuizOrders> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        String redisKey = StrUtil.format(RedisConstants.SITE_MYQUIZORDERS_LIST_KEY,
                MapUtils.getInteger(params, "siteId"),
                MapUtils.getInteger(params, "memberId"),
                MapUtils.getInteger(params, "days"),
                MapUtils.getInteger(params, "status"),
                true == ObjectUtil.isEmpty(params.get("sortBy")) ? SortEnum.ASC.getCode() : MapUtils.getInteger(params, "sortBy"),
                MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<QuizOrders> list = (List<QuizOrders>) RedisRepository.get(redisKey);
        if (ObjectUtil.isEmpty(list)) {
            list = baseMapper.findList(page, params);
            RedisRepository.setExpire(redisKey, list, RedisConstants.EXPIRE_TIME_30_DAYS);
        }
        return PageResult.<QuizOrders>builder().data(list).count(page.getTotal()).build();
    }
    @Override
    public StatiQuizOrdersVo statiOrders(Map<String, Object> params) {
        String redisKey = StrUtil.format(RedisConstants.SITE_MYSTATIQUIZORDERS_LIST_KEY,
                MapUtils.getInteger(params, "siteId"),
                MapUtils.getInteger(params, "memberId"),
                MapUtils.getInteger(params, "days"),
                MapUtils.getInteger(params, "status"));
        StatiQuizOrdersVo statiQuizOrdersVo = (StatiQuizOrdersVo) RedisRepository.get(redisKey);
        if (ObjectUtil.isEmpty(statiQuizOrdersVo)) {
            statiQuizOrdersVo = baseMapper.statiOrders(params);
            RedisRepository.setExpire(redisKey, statiQuizOrdersVo, RedisConstants.EXPIRE_TIME_30_DAYS);
        }
        return statiQuizOrdersVo;
    }

    /**
     * 投注
     *
     * @param ordersDtoList
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Result bettingOrders(List<QuizOrdersDto> ordersDtoList, SysUser user) {
        String lockKey = StrUtil.format(MarksixConstants.Lock.USER_SITEID_SUBMIT_ORDERNO_LOCK, user.getSiteId(), user.getId());
        try {
            boolean lockedSuccess = RedissLockUtil.tryLock(lockKey, MarksixConstants.Lock.WAIT_TIME, MarksixConstants.Lock.LEASE_TIME);
            if (!lockedSuccess) {
                return Result.failed("加锁失败");
            }
            SysUser sysUser = userService.getSysUserById(user.getId());
            BigDecimal balance = sysUser.getMBalance();//用户余额
            BigDecimal totalPrice = BigDecimal.ZERO;//总订单金额
            List<QuizOrders> ordersList = new ArrayList<>();
            //子订单
            List<QuizSuborders> subordersList = new ArrayList<>();
            List<MoneyLog> moneyLogList = new ArrayList<>();
            BigDecimal currentBalance = sysUser.getMBalance();//用户当前余额
            for (QuizOrdersDto ordersDto : ordersDtoList) {
                List<Lottery> lotteryList = lotteryService.findListByLotteryId(ordersDto.getLotteryId());
                for (Lottery lottery : lotteryList) {
                    if (StatusEnum.ONE_TRUE.getStatus() == lottery.getStatus()) {
                        return Result.failed(lottery.getLotteryName() + "结算中，请稍后再试");
                    }
                }
                totalPrice = totalPrice.add(ordersDto.getTotalPrice());//汇总订单金额
                QuizOrders quizOrders = new QuizOrders();
                BeanUtil.copyProperties(ordersDto, quizOrders);
                String orderSn = SnowflakeIdWorker.createOrderSn();//订单号
                quizOrders.setOrderNo(orderSn);
                quizOrders.setSiteId(sysUser.getSiteId());//站点id
                quizOrders.setSiteCode(sysUser.getSiteCode());//站点编码
                quizOrders.setSiteName(sysUser.getSiteName());//站点名称
                quizOrders.setMemberId(sysUser.getId());//会员ID
                quizOrders.setUserName(sysUser.getUsername());//用户名
                quizOrders.setParentId(sysUser.getParentId());//上级id
                quizOrders.setParentName(sysUser.getParentName());//上级账号
                quizOrders.setCreateTime(new Date());
                quizOrders.setCreateBy(sysUser.getUsername());
                quizOrders.setUpdateTime(new Date());
                quizOrders.setUpdateBy(sysUser.getUsername());

                //子订单
                List<QuizSubordersDto> subordersDtoList = ordersDto.getSubordersList();
                if(null!=subordersDtoList && subordersDtoList.size()>0) {
                    for (QuizSubordersDto subordersDto : subordersDtoList) {
                        quizOrders.setIsSubOrders(StatusEnum.ONE_TRUE.getStatus());
                        QuizSuborders quizSuborders = new QuizSuborders();
                        BeanUtil.copyProperties(subordersDto, quizSuborders);
                        quizSuborders.setOrderNo(orderSn);//父订单号
                        quizSuborders.setSuborderNo(SnowflakeIdWorker.createOrderSn());//子订单号
                        quizSuborders.setCreateTime(new Date());
                        quizSuborders.setCreateBy(sysUser.getUsername());
                        quizSuborders.setUpdateTime(new Date());
                        quizSuborders.setUpdateBy(sysUser.getUsername());
                        subordersList.add(quizSuborders);
                    }
                }
                ordersList.add(quizOrders);

                MoneyLog moneyLog = new MoneyLog();
                moneyLog.setUserId(sysUser.getId());
                moneyLog.setUserName(sysUser.getUsername());
                moneyLog.setOrderNo(orderSn);
                moneyLog.setOrderType(MbChangeTypeEnum.BETTING.getType());//账变类型
                moneyLog.setOrderTypeName(MbChangeTypeEnum.BETTING.getName());//账变类型名称
                moneyLog.setBeforeMoney(currentBalance);//账变前金额
                moneyLog.setMoney(ordersDto.getTotalPrice().negate());//账变金额
                currentBalance = currentBalance.subtract(ordersDto.getTotalPrice());
                moneyLog.setAfterMoney(currentBalance);//账变后金额
                moneyLog.setCreateTime(new Date());
                moneyLog.setCreateBy(sysUser.getUsername());
                moneyLog.setUpdateTime(new Date());
                moneyLog.setUpdateBy(sysUser.getUsername());
                moneyLogList.add(moneyLog);
            }
            //用户余额小于总订单金额
            if (balance.compareTo(totalPrice) == -1) {
                return Result.failed(CodeEnum.MB_NOT_ENOUGH.getCode(), "余额不足", null);
            }
            //扣除M币
            userService.addRewardMb(sysUser, totalPrice.negate());
            //账变记录
            moneyLogService.saveBatch(moneyLogList);
            //保存注单
            this.saveBatch(ordersList);
            //保存子订单
            quizSubordersService.saveBatch(subordersList);
            //删除投注订单缓存
            String ordersRedisKey = StrUtil.format(RedisConstants.SITE_MYQUIZORDERS_LIST_KEY, user.getSiteId(), user.getId(),"*","*","*","*","*");
            RedisRepository.delete(ordersRedisKey);
            //删除统计投注订单缓存
            String statiOrdersRedisKey = StrUtil.format(RedisConstants.SITE_MYSTATIQUIZORDERS_LIST_KEY, user.getSiteId(), user.getId(),"*","*");
            RedisRepository.delete(statiOrdersRedisKey);
            return Result.succeed("投注完成");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.failed("投注失败");
        } finally {
            RedissLockUtil.unlock(lockKey);
        }
    }

    /**
     * 撤销投注
     *
     * @param ids
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Result cancelBetting(List<Long> ids, SysUser user) {
        String lockKey = StrUtil.format(MarksixConstants.Lock.USER_SITEID_SUBMIT_ORDERNO_LOCK, user.getSiteId(), user.getId());
        try {
            boolean lockedSuccess = RedissLockUtil.tryLock(lockKey, MarksixConstants.Lock.WAIT_TIME, MarksixConstants.Lock.LEASE_TIME);
            if (!lockedSuccess) {
                return Result.failed("加锁失败");
            }
            SysUser sysUser = userService.getSysUserById(user.getId());
            BigDecimal totalPrice = BigDecimal.ZERO;//总订单金额
            List<QuizOrders> ordersList = new ArrayList<>();
            List<MoneyLog> moneyLogList = new ArrayList<>();
            BigDecimal currentBalance = sysUser.getMBalance();//用户当前余额
            for (Long id : ids) {
                QuizOrders quizOrders = this.getById(id);
                if(null == quizOrders){
                    return Result.failed("投注记录不存在");
                }
                List<Lottery> lotteryList = lotteryService.findListByLotteryId(quizOrders.getLotteryId());
                for (Lottery lottery : lotteryList) {
                    if (StatusEnum.ONE_TRUE.getStatus() == lottery.getStatus()) {
                        return Result.failed(lottery.getLotteryName() + "结算中，请稍后再试");
                    }
                }
                if (OrderStatusEnum.ORDER_ONE.getStatus() != quizOrders.getStatus()) {
                    return Result.failed("状态不正确不允许撤销");
                }
                totalPrice = totalPrice.add(quizOrders.getTotalPrice());//汇总订单金额
                quizOrders.setUpdateTime(new Date());
                quizOrders.setUpdateBy(sysUser.getUsername());
                quizOrders.setStatus(OrderStatusEnum.ORDER_TWO.getStatus());
                ordersList.add(quizOrders);
                //0 没有子订单,1 有子订单
                if (StatusEnum.ONE_TRUE.getStatus() == quizOrders.getIsSubOrders()) {
                    Map<String, Object> subordersParams = new HashMap<>();
                    subordersParams.put("orderNo", quizOrders.getOrderNo());
                    List<QuizSuborders> quizSubordersList = quizSubordersService.findList(subordersParams);
                    List<QuizSuborders> newQuizSubordersList = new ArrayList<>();
                    for (QuizSuborders suborders : quizSubordersList) {
                        suborders.setUpdateTime(new Date());
                        suborders.setUpdateBy(sysUser.getUsername());
                        suborders.setStatus(OrderStatusEnum.ORDER_TWO.getStatus());
                        newQuizSubordersList.add(suborders);
                    }
                    quizSubordersService.updateBatchById(newQuizSubordersList);
                    //清空子订单缓存
                    RedisRepository.delete(StrUtil.format(RedisConstants.SITE_QUIZSUBORDERS_LIST_KEY, quizOrders.getOrderNo()));
                }

                MoneyLog moneyLog = new MoneyLog();
                moneyLog.setUserId(sysUser.getId());
                moneyLog.setUserName(sysUser.getUsername());
                moneyLog.setOrderNo(quizOrders.getOrderNo());
                moneyLog.setOrderType(MbChangeTypeEnum.CANCELBETTING.getType());//账变类型
                moneyLog.setOrderTypeName(MbChangeTypeEnum.CANCELBETTING.getName());//账变类型名称
                moneyLog.setBeforeMoney(currentBalance);//账变前金额
                moneyLog.setMoney(quizOrders.getTotalPrice());//账变金额
                currentBalance = currentBalance.add(quizOrders.getTotalPrice());
                moneyLog.setAfterMoney(currentBalance);//账变后金额
                moneyLog.setCreateTime(new Date());
                moneyLog.setCreateBy(sysUser.getUsername());
                moneyLog.setUpdateTime(new Date());
                moneyLog.setUpdateBy(sysUser.getUsername());
                moneyLogList.add(moneyLog);
            }
            //增加M币
            userService.addRewardMb(sysUser, totalPrice);
            //账变记录
            moneyLogService.saveBatch(moneyLogList);
            //更新投注
            this.saveOrUpdateBatch(ordersList);
            //删除投注订单缓存
            String ordersRedisKey = StrUtil.format(RedisConstants.SITE_MYQUIZORDERS_LIST_KEY, user.getSiteId(), user.getId(),"*","*","*","*","*");
            RedisRepository.delete(ordersRedisKey);
            //删除统计投注订单缓存
            String statiOrdersRedisKey = StrUtil.format(RedisConstants.SITE_MYSTATIQUIZORDERS_LIST_KEY, user.getSiteId(), user.getId(),"*","*");
            RedisRepository.delete(statiOrdersRedisKey);
            return Result.succeed("撤销投注");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.failed("撤销投注失败");
        } finally {
            RedissLockUtil.unlock(lockKey);
        }

    }
}