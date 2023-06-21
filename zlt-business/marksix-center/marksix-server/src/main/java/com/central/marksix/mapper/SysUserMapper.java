package com.central.marksix.mapper;

import com.central.common.model.SysUser;
import com.central.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 Mapper 接口
 *
 * @author zlt
 * @data 2018-10-29
 */
@Mapper
public interface SysUserMapper extends SuperMapper<SysUser> {


    SysUser getMerchantAdministrator(@Param("siteCode")String siteCode);

    Integer[] getUserIdsByUserName(@Param("username")String username, @Param("userIds")Integer[] userIds);
}
