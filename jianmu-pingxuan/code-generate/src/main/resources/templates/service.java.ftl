package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.jianmu.bean.base.BasePageParam;
import com.jianmu.bean.base.PageVO;
import com.baomidou.mybatisplus.extension.service.IService;
/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
    public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    PageVO<${entity}> page(BasePageParam param);
    }
</#if>
