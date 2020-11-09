package com.ycandyz.master.controller.taboo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.base.BaseController;
import com.ycandyz.master.domain.query.taboo.BaseTabooWordsQuery;
import com.ycandyz.master.domain.response.risk.BaseTabooWordsRep;
import com.ycandyz.master.entities.taboo.BaseTabooWords;
import com.ycandyz.master.model.taboo.BaseTabooWordsVO;
import com.ycandyz.master.service.taboo.IBaseTabooWordsService;
import com.ycandyz.master.service.taboo.impl.BaseTabooWordsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * @Description 基础商品表 接口类
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
    private IBaseTabooWordsService baseTabooWordsServicel;

	@ApiOperation(value="新增敏感字")
    @PostMapping(value = "/addBaseTabooWords")
	public ReturnResponse<Object> addBaseTabooWords(@RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        baseTabooWordsServicel.addBaseTabooWords(baseTabooWordsVO);
        return ReturnResponse.success("保存成功!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "/select/{id}")
	public ReturnResponse<BaseTabooWordsRep> getById(@PathVariable Long id) {
        BaseTabooWordsRep baseTabooWords = baseTabooWordsServicel.selById(id);
        return ReturnResponse.success(baseTabooWords);
    }


    @ApiOperation(value="编辑敏感字")
    @PutMapping(value = "/updateBaseTabooWords")
    public ReturnResponse<Object> updateBaseTabooWords(@RequestBody BaseTabooWordsVO baseTabooWordsVO) {
        int i = baseTabooWordsServicel.updateBaseTabooWords(baseTabooWordsVO);
        if (i > 0){
            return ReturnResponse.success("修改成功!");
        }
        return ReturnResponse.success("修改失败!");
    }



    @ApiOperation(value = "查询全部")
    @PostMapping(value = "/select/list")
    public ReturnResponse<Page<BaseTabooWordsRep>> selectList(RequestParams<BaseTabooWordsQuery> query) {
        Page<BaseTabooWordsRep> page = baseTabooWordsServicel.selectList(query);
        return ReturnResponse.success(page);
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "/delete/{id}")
	public ReturnResponse<Object> delById(@PathVariable Long id) {
        int i = baseTabooWordsServicel.delById(id);
        if (i > 0){
            return ReturnResponse.success("删除成功!");
        }
        return ReturnResponse.failed("该id对应的数据不存在");
	}


}
