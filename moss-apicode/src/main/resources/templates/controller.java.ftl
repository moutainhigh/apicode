package ${cfg.Controller};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ${cfg.ValidatorContract};
import ${cfg.result};
import ${cfg.baseResult};
import ${cfg.basePageResult};
import ${cfg.pageModel};
import ${cfg.entity}.${entity};
import ${cfg.model}.${entity}${cfg.modelSuffix};
import ${cfg.query}.${entity}${cfg.querySuffix};
import ${cfg.ServiceImpl}.${table.serviceImplName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * @Description ${table.comment!} 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */

@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Api(tags="${cfg.module}-${table.comment!}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceImplName},${entity},${entity}${cfg.querySuffix}> {
<#else>
public class ${table.controllerName} {
</#if>
	
	@ApiOperation(value="新增")
    @PostMapping
	public CommonResult<${entity}${cfg.modelSuffix}> insert(@Validated(ValidatorContract.OnCreate.class) @RequestBody ${entity}${cfg.modelSuffix} model) {
        return result(service.insert(model),model,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public CommonResult<${entity}${cfg.modelSuffix}> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) @RequestBody ${entity}${cfg.modelSuffix} model) {
        model.setId(id);
        return result(service.update(model),model,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public CommonResult<${entity}> getById(@PathVariable Long id) {
        return CommonResult.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public CommonResult<BasePageResult<${entity}>> selectPage(PageModel page, ${entity}${cfg.querySuffix} query) {
        return CommonResult.success(new BasePageResult(service.page(new Page(page.getPage(),page.getPageSize()),query)));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping
    public CommonResult<BaseResult<List<${entity}>>> selectList(${entity}${cfg.querySuffix} query) {
        return CommonResult.success(new BaseResult<List<${entity}>>(service.list(query)));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public CommonResult deleteById(@PathVariable Long id) {
        return result(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public CommonResult deleteBatch(String ids) {
        return result(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
</#if>
