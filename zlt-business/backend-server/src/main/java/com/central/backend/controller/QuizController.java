package com.central.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import com.central.backend.service.IQuizService;
import com.central.common.annotation.LoginUser;
import com.central.common.model.Quiz;
import com.central.common.model.SysUser;
import com.central.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.central.common.model.PageResult;
import com.central.common.model.Result;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 竞猜分类
 *
 * @author zlt
 * @date 2023-05-09 18:41:24
 */
@Slf4j
@RestController
@RequestMapping("/quiz")
@Api(tags = "竞猜分类")
public class QuizController {
    @Autowired
    private IQuizService quizService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteCategoryId", value = "站点分类ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "sortBy", value = "排序方式：1正序(默认)、2倒叙", required = false, dataType = "Integer")
    })
    @GetMapping
    public Result<List<Quiz>> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (ObjectUtil.isEmpty(params)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(params.get("siteCategoryId"))) {
            return Result.failed("站点分类ID");
        }
        return Result.succeed(quizService.findList(params));
    }


    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping
    public Result save(@RequestBody Quiz quiz, @ApiIgnore @LoginUser SysUser user) {
        if (ObjectUtil.isEmpty(quiz)) {
            return Result.failed("请求参数不能为空");
        }
        if (ObjectUtil.isEmpty(quiz.getTitle())) {
            return Result.failed("标题不能为空");
        }
        if (ObjectUtil.isEmpty(quiz.getSiteCategoryId())) {
            return Result.failed("站点分类ID不能为空");
        }
        if (ObjectUtil.isEmpty(quiz.getSort())) {
            return Result.failed("顺序不能为空");
        }
        if(StringUtils.isNotNull(quiz.getId())){
            quiz.setUpdateBy(user.getUsername());
            quiz.setUpdateTime(new Date());
        }else {
            quiz.setCreateBy(user.getUsername());
            quiz.setCreateTime(new Date());
            quiz.setUpdateBy(user.getUsername());
            quiz.setUpdateTime(new Date());
        }
        quizService.saveOrUpdate(quiz);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result deleteQuiz(@PathVariable Long id) {
        if (ObjectUtil.isEmpty(id)) {
            return Result.failed("ID不能为空");
        }
        quizService.deleteQuiz(id);
        return Result.succeed("删除成功");
    }
}
