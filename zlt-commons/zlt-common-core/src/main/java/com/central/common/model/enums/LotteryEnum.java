package com.central.common.model.enums;

/**
 * 彩种
 */
public enum LotteryEnum {
    HONGKONG_MKS(1, "香港六合彩"),
    MACAO_MKS(1, "澳门六合彩"),
    TAIWAN_MKS(1, "台湾六合彩"),
    SINGAPORE_MKS(1, "新加坡六合彩");

    private final Integer status;
    private final String remark;

    LotteryEnum(Integer status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }
}
