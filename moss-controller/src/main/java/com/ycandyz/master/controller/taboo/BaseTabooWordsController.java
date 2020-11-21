package com.ycandyz.master.controller.taboo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
import com.ycandyz.master.service.taboo.BaseTabooWordsService;
import com.ycandyz.master.service.taboo.impl.BaseTabooWordsServiceImpl;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @Description 敏感词管理 接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@Slf4j
@RestController
@RequestMapping("baseTabooWords")
@Api(tags="敏感词管理")
public class BaseTabooWordsController extends BaseController<BaseTabooWordsServiceImpl, BaseTabooWords, BaseTabooWordsQuery> {

    @Autowired
    private BaseTabooWordsService baseTabooWordsService;

    @ApiOperation(value="新增敏感字")
    @PostMapping(value = "/addBaseTabooWords")
    public ReturnResponse<Object> addBaseTabooWords(@Validated(ValidatorContract.OnCreate.class) @RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        if (baseTabooWordsVO == null){
            log.error("当前更新的入参数据为空");
            return ReturnResponse.failed("当前更新的入参数据为空");
        }
        log.info("新增敏感字请求入参:{}", JSON.toJSONString(baseTabooWordsVO));
        String phraseName = baseTabooWordsVO.getPhraseName();
        String[] tabooWords = baseTabooWordsVO.getTabooWords();
        ReturnResponse returnResponse = baseTabooWordsService.selTabooWords(phraseName,tabooWords);
        if (returnResponse != null && returnResponse.getCode() == 500){
            return returnResponse;
        }
        baseTabooWordsService.addBaseTabooWords(baseTabooWordsVO);
        log.info("新增敏感字请求响应:{}", ReturnResponse.success("保存成功!"));
        return ReturnResponse.success("保存成功!");
    }

    @ApiOperation(value = "查询根据ID")
    @GetMapping(value = "/select/{id}")
    public ReturnResponse<BaseTabooWordsRep> getById(@PathVariable Long id) {
        log.info("敏感词管理查询根据ID请求入参:{}", id);
        BaseTabooWordsRep baseTabooWords = baseTabooWordsService.selById(id);
        log.info("敏感词管理查询根据ID请求响应:{}", ReturnResponse.success(baseTabooWords));
        return ReturnResponse.success(baseTabooWords);
    }

    @ApiOperation(value="编辑敏感字")
    @PutMapping(value = "/updateBaseTabooWords")
    public ReturnResponse<Object> updateBaseTabooWords(@Validated(ValidatorContract.OnCreate.class) @RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        log.info("编辑敏感字请求入参:{}", JSON.toJSONString(baseTabooWordsVO));
        String[] tabooWords = baseTabooWordsVO.getTabooWords();
        ReturnResponse returnResponse = baseTabooWordsService.selTabooWord(tabooWords);
        if (returnResponse != null && returnResponse.getCode() == 500){
            return returnResponse;
        }
        ReturnResponse returnResponse2 = baseTabooWordsService.updateBaseTabooWords(baseTabooWordsVO);
        log.info("编辑敏感字请求响应:{}", JSON.toJSONString(returnResponse2));
        return returnResponse2;
    }

    @ApiOperation(value = "查询全部")
    @PostMapping(value = "/select/list")
    public ReturnResponse<Page<BaseTabooWordsRep>> selectList(@RequestBody RequestParams<BaseTabooWordsQuery> requestParams) {
        log.info("敏感词管理查询全部请求入参:{}", JSON.toJSONString(requestParams));
        Page<BaseTabooWordsRep> page = baseTabooWordsService.selectList(requestParams);
        log.info("敏感词管理查询全部请求响应:{}", JSON.toJSONString(ReturnResponse.success(page)));
        return ReturnResponse.success(page);
    }

    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "/delete/{id}")
    public ReturnResponse<Object> delById(@PathVariable Long id) {
        log.info("敏感词管理通过ID删除请求入参:{}", id);
        ReturnResponse returnResponse = baseTabooWordsService.delById(id);
        log.info("敏感词管理查询全部请求响应:{}", JSON.toJSONString(ReturnResponse.success(returnResponse)));
        return ReturnResponse.success(returnResponse);
    }

}
