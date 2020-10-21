package ${cfg.query};

import ${cfg.condition};
import ${cfg.conditionEnum};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>

/**
 * <p>
 * @Description ${table.comment!} 检索参数类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
</#if>
<#if swagger2>
@ApiModel(description="${table.comment!}-检索参数")
</#if>
<#if superEntityClass??>
public class ${entity}${cfg.querySuffix} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity}${cfg.querySuffix} extends Model<${entity}> {
<#else>
public class ${entity}${cfg.querySuffix} implements Serializable {
</#if>

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    <#if swagger2>
    	<#if field.propertyType != "Date">
    @ApiModelProperty(value = "${field.comment}")
    	</#if>
    <#else>
    /**
     * ${field.comment}
     */
    </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @Condition(field = "${field.name}", condition = ConditionEnum.EQ)
        <#elseif idType??>
    @Condition(field = "${field.name}", condition = ConditionEnum.EQ)
        <#elseif field.convert>
    @Condition(field = "${field.name}", condition = ConditionEnum.EQ)
        </#if>
    <#-- 普通字段 -->
    <#elseif field.propertyType == "Date">
    @ApiModelProperty(value = "${field.comment}起")
    @Condition(field = "${field.name}", condition = ConditionEnum.GE)
    private ${field.propertyType} ${field.propertyName}S;
    
    @ApiModelProperty(value = "${field.comment}止")
    @Condition(field = "${field.name}", condition = ConditionEnum.LE)
    private ${field.propertyType} ${field.propertyName}E;
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @Condition(field = "${field.name}", condition = ConditionEnum.EQ)
        <#else>
    @Condition(condition = ConditionEnum.EQ)
        </#if>
    <#elseif field.convert>
    @Condition(condition = ConditionEnum.EQ)
    </#if>
    <#if field.propertyType != "Date">
    private ${field.propertyType} ${field.propertyName};
    </#if>
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

}
