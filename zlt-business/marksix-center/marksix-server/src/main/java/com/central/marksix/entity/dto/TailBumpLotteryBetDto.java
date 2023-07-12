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
public class TailBumpLotteryBetDto {
    @ApiModelProperty(value = "开奖分类二类名称")
    private String quizTitle;
    @ApiModelProperty(value = "尾数一")
    private Integer tailOne;
    @ApiModelProperty(value = "尾数二")
    private Integer tailTwo;
    @ApiModelProperty(value = "尾数一赔率")
    private Double oddsOne;
    @ApiModelProperty(value = "尾数一赔率2(三中二之中三，中特之中二）")
    private Double oddsOne2;
    @ApiModelProperty(value = "尾数二赔率")
    private Double oddsTwo;
    @ApiModelProperty(value = "尾数二赔率2(三中二之中三，中特之中二）")
    private Double oddsTwo2;
}
