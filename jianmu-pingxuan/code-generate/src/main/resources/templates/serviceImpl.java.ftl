package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
    public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public PageVO<${entity}> page(BasePageParam param) {
    LambdaQueryWrapper<${entity}> uw = Wrappers.lambdaQuery();
    Page<${entity}> page = page(new Page<>(param.getCurrent(), param.getSize()), uw);
    return new PageVO<${entity}>().setPages(page.getPages())
    .setCurrent(page.getCurrent())
    .setData(page.getRecords())
    .setSize(page.getSize())
    .setTotal(page.getTotal());
    }


    @Override
    public boolean removeBatchByIds(Collection<?> ids) {
    return this.baseMapper.enhanceBatchDelete(ids.parallelStream().map(String::valueOf).collect(Collectors.toList())) > 0;
    }
    }
</#if>
