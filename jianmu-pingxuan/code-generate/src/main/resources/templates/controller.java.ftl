package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@Api(tags = "${table.comment!}相关接口")
@RequestMapping("${controllerMappingHyphen?replace("-","_")}")
public class ${entity}Api {
private final ${table.serviceName} ${table.entityPath}Service;

@Autowired
public ${entity}Api(${table.serviceName} ${table.entityPath}Service) {
this.${table.entityPath}Service = ${table.entityPath}Service;
}

@PostMapping("/page")
@ResponseResult
@ApiOperation(value = "${table.comment!}列表分页查询")
public PageVO<${entity}> page(BasePageParam param){
return ${table.entityPath}Service.page(param);
}

@GetMapping("/get")
@ApiOperation(value = "${table.comment!}详情查询",notes = "get")
public ${entity} get(String id){
return ${table.entityPath}Service.getById(id);
}

@PostMapping("/save")
@ResponseResult
@ApiOperation(value = "${table.comment!}保存",notes = "save")
public Boolean save(${entity} ${table.entityPath}){
return ${table.entityPath}Service.save(${table.entityPath});
}

@DeleteMapping("/delete")
@ResponseResult
@ApiOperation(value = "${table.comment!}删除",notes = "delete")
public Boolean delete(List
<String> ids){
    return ${table.entityPath}Service.removeBatchByIds(ids);
    }
    }