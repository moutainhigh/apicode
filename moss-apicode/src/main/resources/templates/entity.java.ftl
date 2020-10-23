package ${cfg.entity};

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

</#if>
<#if entityLombokModel>
import lombok.Getter;
import lombok.Setter;
</#if>

/**
 * <p>
 * @Description ${table.comment!} 数据表字段映射类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */
<#if entityLombokModel>
@Getter
@Setter
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if swagger2>
@ApiModel(description="${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${entity} extends Model {
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
 <#if field.keyFlag>
  <#assign keyPropertyName="${field.propertyName}"/>
 </#if>

 <#if field.comment!?length gt 0>
  <#if swagger2>
   @ApiModelProperty(value = "${field.comment}")
  <#else>
   /**
   * ${field.comment}
   */
  </#if>
 </#if>
 <#if field.keyFlag>
 <#-- 主键 -->
  <#if field.keyIdentityFlag>
   @TableId(value = "${field.name}", type = IdType.AUTO)
  <#elseif idType??>
   @TableId(value = "${field.name}", type = IdType.${idType})
  <#elseif field.convert>
   @TableId("${field.name}")
  </#if>
 <#-- 普通字段 -->
 <#elseif field.propertyName == "createDate">
  @TableField(value = "${field.name}", fill = FieldFill.INSERT)
 <#elseif field.propertyName == "createBy">
  @TableField(value = "${field.name}", fill = FieldFill.INSERT)
 <#elseif field.propertyName == "updateDate">
  @TableField(value = "${field.name}", fill = FieldFill.UPDATE)
 <#elseif field.propertyName == "updateBy">
  @TableField(value = "${field.name}", fill = FieldFill.UPDATE)
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
<#-- 乐观锁注解 -->
 <#if (versionFieldName!"") == field.name>
  @Version
 </#if>
<#-- 逻辑删除注解 -->
 <#if (logicDeleteFieldName!"") == field.name>
  @TableLogic
 </#if>
   private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
 <#list table.fields as field>
  <#if field.propertyType == "boolean">
   <#assign getprefix="is"/>
  <#else>
   <#assign getprefix="get"/>
  </#if>
  public ${field.propertyType} ${getprefix}${field.capitalName}() {
  return ${field.propertyName};
  }

  <#if entityBuilderModel>
   public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
  <#else>
   public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
  </#if>
  this.${field.propertyName} = ${field.propertyName};
  <#if entityBuilderModel>
   return this;
  </#if>
  }
 </#list>
</#if>

<#if entityColumnConstant>
 <#list table.fields as field>
  public static final String ${field.name?upper_case} = "${field.name}";

 </#list>
</#if>
<#if activeRecord>
 @Override
 protected Serializable pkVal() {
 <#if keyPropertyName??>
  return this.${keyPropertyName};
 <#else>
  return null;
 </#if>
 }

</#if>
<#if !entityLombokModel>
 @Override
 public String toString() {
 return "${entity}{" +
 <#list table.fields as field>
  <#if field_index==0>
   "${field.propertyName}=" + ${field.propertyName} +
  <#else>
   ", ${field.propertyName}=" + ${field.propertyName} +
  </#if>
 </#list>
 "}";
 }
</#if>
}