package com.central.common.model.enums;

/**
 * M币账变记录
 */
public enum MoneyLogEnum {
    TRANSFER_STATUS_FAILED(1, "失败"),
    TRANSFER_STATUS_SUCCESS(1, "成功");
    private final Integer status;
    private final String remark;

    MoneyLogEnum(Integer status, String remark) {
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
