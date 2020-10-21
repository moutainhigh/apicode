package ${cfg.Service};

import ${superServiceClassPackage};
import ${cfg.entity}.${entity};

/**
 * <p>
 * @Description ${table.comment!} 业务接口类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version 2.0
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}>{
	
}
</#if>
