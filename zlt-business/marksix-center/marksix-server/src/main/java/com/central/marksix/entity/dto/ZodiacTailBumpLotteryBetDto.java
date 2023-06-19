package com.central.marksix.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 号码属性表
 *
 * @author zlt
 * @date 2023-05-08 15:05:53
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ZodiacTailBumpLotteryBetDto {
    @ApiModelProperty(value = "开奖分类二类名称")
    private String quizTitle;
    @ApiModelProperty(value = "生肖一")
    private Integer zodiacOne;
    @ApiModelProperty(value = "尾数二")
    private Integer tailTwo;
    @ApiModelProperty(value = "赔率一")
    private Double oddsOne;
    @ApiModelProperty(value = "赔率二")
    private Double oddsTwo;
}
