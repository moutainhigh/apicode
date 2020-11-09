package ${cfg.Controller};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import cn.hutool.core.convert.Convert;
import com.ycandyz.master.api.ReturnResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ${cfg.ValidatorContract};
import ${cfg.result};
import ${cfg.entity}.${entity};
import ${cfg.query}.${entity}${cfg.querySuffix};
import ${cfg.ServiceImpl}.${table.serviceImplName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * @Description ${table.comment!} 接口类
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
    @PostMapping(value = "insert")
	public ReturnResponse<${entity}> insert(@Validated(ValidatorContract.OnCreate.class) ${entity} entity) {
        return returnResponse(service.save(entity),entity,"保存失败!");
	}
	
	@ApiOperation(value = "通过ID更新")
    @PutMapping(value = "{id}")
	public ReturnResponse<${entity}> updateById(@PathVariable Long id,@Validated(ValidatorContract.OnUpdate.class) ${entity} entity) {
        entity.setId(id);
        return returnResponse(service.updateById(entity),entity,"更改失败!");
	}
	
	@ApiOperation(value = "查询根据ID")
    @GetMapping(value = "{id}")
	public ReturnResponse<${entity}> getById(@PathVariable Long id) {
        return ReturnResponse.success(service.getById(id));
    }
    
	@ApiOperation(value = "查询分页")
    @GetMapping(value = "page")
    @SuppressWarnings("unchecked")
    public ReturnResponse<Page<${entity}>> selectPage(Page page, ${entity}${cfg.querySuffix} query) {
        return ReturnResponse.success(service.page(page,query));
    }
    
    @ApiOperation(value = "查询全部")
    @GetMapping(value = "list")
    public ReturnResponse<${entity}> selectList(${entity}${cfg.querySuffix} query) {
        return ReturnResponse.success(service.list(query));
    }
    
    @ApiOperation(value = "通过ID删除")
    @DeleteMapping(value = "{id}")
	public ReturnResponse<${entity}> deleteById(@PathVariable Long id) {
        return returnResponse(service.removeById(id),null,"删除失败!");
	}

    @ApiImplicitParam(name="ids",value="ID集合(1,2,3)",required=true,allowMultiple=true,dataType="int")
   	@ApiOperation(value = "通过ids批量删除")
    @DeleteMapping(value = "delete")
	public ReturnResponse<${entity}> deleteBatch(String ids) {
        return returnResponse(service.deleteByIds(Convert.toLongArray(ids)),null,"删除失败!");
	}
    
}
</#if>
