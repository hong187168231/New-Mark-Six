package com.central.marksix.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto  extends  ProxyAdminDto{

    @ApiModelProperty(value = "代理用户名")
    String proxyUsername;

}
