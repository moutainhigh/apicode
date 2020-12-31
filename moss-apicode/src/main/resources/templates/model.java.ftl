package ${cfg.model};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
 import lombok.Data;
</#if>
import ${cfg.jsonNaming};
import ${cfg.propertyNamingStrategy};

import java.io.Serializable;

/**
 * <p>
 * @Description ${table.comment!} Model
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */
<#if entityLombokModel>
@Data
</#if>
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
<#if swagger2>
@ApiModel(description="${table.comment!}-Model")
</#if>
<#if superEntityClass??>
public class ${entity}${cfg.modelSuffix} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity}${cfg.modelSuffix} extends Model<${entity}> {
<#else>
public class ${entity}${cfg.modelSuffix} implements Serializable {
</#if>

   private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
 <#if field.keyFlag>
  <#assign keyPropertyName="${field.propertyName}"/>
 </#if>

 <#if field.comment!?length gt 0>
  <#if swagger2>
   @ApiModelProperty(name = "${field.name}", value = "${field.comment}")
  <#else>
   /**
   * ${field.comment}
   */
  </#if>
 </#if>
 <#if field.keyFlag>
 <#-- 主键 -->
  <#if field.keyIdentityFlag>
  <#elseif idType??>
  <#elseif field.convert>
  </#if>
 <#elseif field.fill??>
 <#-- -----   存在字段填充设置   ----->
  <#if field.convert>
   @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
  <#else>
   @TableField(fill = FieldFill.${field.fill})
  </#if>
 <#elseif field.convert>
   @TableField(value = "${field.name}")
 </#if>
   private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if entityColumnConstant>
 <#list table.fields as field>
  public static final String ${field.name?upper_case} = "${field.name}";

 </#list>
</#if>

}

