package com.ycandyz.master.controller.taboo;

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
	    baseTabooWordsService.addBaseTabooWords(baseTabooWordsVO);
        return ReturnResponse.success("保存成功!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "/select/{id}")
	public ReturnResponse<BaseTabooWordsRep> getById(@PathVariable Long id) {
        BaseTabooWordsRep baseTabooWords = baseTabooWordsService.selById(id);
        return ReturnResponse.success(baseTabooWords);
    }

    @ApiOperation(value="编辑敏感字")
    @PutMapping(value = "/updateBaseTabooWords")
    public ReturnResponse<Object> updateBaseTabooWords(@Validated(ValidatorContract.OnCreate.class) @RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        ReturnResponse returnResponse = baseTabooWordsService.updateBaseTabooWords(baseTabooWordsVO);
        return returnResponse;
    }

    @ApiOperation(value = "查询全部")
    @PostMapping(value = "/select/list")
    public ReturnResponse<Page<BaseTabooWordsRep>> selectList(@RequestBody RequestParams<BaseTabooWordsQuery> requestParams) {
        Page<BaseTabooWordsRep> page = baseTabooWordsService.selectList(requestParams);
        return ReturnResponse.success(page);
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "/delete/{id}")
	public ReturnResponse<Object> delById(@PathVariable Long id) {
        baseTabooWordsService.delById(id);
        return ReturnResponse.success("删除成功");
	}

}
