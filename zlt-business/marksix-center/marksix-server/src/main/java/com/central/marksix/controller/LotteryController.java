package com.central.marksix.controller;

import cn.hutool.core.util.ObjectUtil;
import com.central.common.annotation.LoginUser;
import com.central.common.model.*;
import com.central.marksix.entity.vo.CategoryVO;
import com.central.marksix.entity.vo.SiteLotteryVO;
import com.central.marksix.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 彩种表
 *
 * @author zlt
 * @date 2023-05-09 19:57:30
 */
@Slf4j
@RestController
@RequestMapping("/lottery")
@Api(tags = "彩票相关API接口")
public class LotteryController {
    @Autowired
    private ILotteryService lotteryService;
    @Autowired
    private IWnDataService wnDataService;
    @Autowired
    private IQuizService quizService;
    @Autowired
    private IQuizChooseService quizChooseService;
    @Autowired
    private ISiteCategoryLotteryService categoryLotteryService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询彩种列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序(默认)、2倒叙", required = false, dataType = "Integer")
    })
    @GetMapping
    public Result<List<SiteLotteryVO>> list(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser SysUser user) {

        return Result.succeed(lotteryService.findList(params,user));
    }


    /**
     * 列表
     */
    @ApiOperation(value = "开奖号码列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序、2倒叙(默认)", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "lotteryId", value = "彩种id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = true, dataType = "Integer")
    })
    @GetMapping("/wndatalist")
    public Result<PageResult<WnData>>  wnDatalist(@RequestParam Map<String, Object> params) {
        if (ObjectUtil.isEmpty(params)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("lotteryId"))) {
            return Result.failed("彩种id不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("page"))) {
            return Result.failed("分页起始位置不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("limit"))) {
            return Result.failed("分页结束位置不能为空");
        }
        return Result.succeed(wnDataService.findList(params));
    }
    @ApiOperation(value = "根据彩种ID查询最近一期开奖数据")
    @GetMapping("/lastonewndata/{id}")
    public Result lastOneWnData(@PathVariable Integer id) {
        if (ObjectUtil.isEmpty(id)) {
            return Result.failed("ID不能为空");
        }
        return Result.succeed(wnDataService.lastOneWnData(id));
    }
    @ApiOperation(value = "查询站点下注分类（系统管理员权限）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "分类名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "siteLotteryId", value = "站点彩种ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序(默认)、2倒叙", required = false, dataType = "Integer")
    })
    @GetMapping("/listsitecategory")
    public Result<List<CategoryVO>> listSiteCategory(@RequestParam Map<String, Object> params) {
        if (ObjectUtil.isEmpty(params)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("siteLotteryId"))) {
            return Result.failed("站点彩种ID不能为空");
        }
        return Result.succeed(categoryLotteryService.findList(params));
    }
    @ApiOperation(value = "查询彩票规则主表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lotteryId", value = "彩种id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序(默认)、2倒叙", required = false, dataType = "Integer")
    })
    @GetMapping("/quizlist")
    public Result<List<Quiz>> quizList(@RequestParam Map<String, Object> params) {
        if (ObjectUtil.isEmpty(params)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("siteCategoryId"))) {
            return Result.failed("站点分类ID不能为空");
        }
        return Result.succeed(quizService.findList(params));
    }
    @ApiOperation(value = "查询彩票规则主表对应明细规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lotteryId", value = "彩种id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "quizId", value = "彩种规则主表id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序(默认)、2倒叙", required = false, dataType = "Integer")
    })
    @GetMapping("/quizchooselist")
    public Result<List<QuizChoose>> quizChooseList(@RequestParam Map<String, Object> params) {
        if (ObjectUtil.isEmpty(params)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("quizId"))) {
            return Result.failed("彩票规则主表id不能为空");
        }
        return Result.succeed(quizChooseService.findList(params));
    }
}
