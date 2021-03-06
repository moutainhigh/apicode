package ${cfg.vo};

import ${cfg.entity}.${entity};
import com.baomidou.mybatisplus.annotation.TableName;
<#if entityLombokModel>
 import lombok.Data;
</#if>

/**
 * <p>
 * @Description ${table.comment!} VO
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */
<#if entityLombokModel>
@Data
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if superEntityClass??>
public class ${entity}${cfg.voSuffix} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity}${cfg.voSuffix} extends Model<${entity}> {
<#else>
public class ${entity}${cfg.voSuffix} extends ${entity} {
</#if>

}
