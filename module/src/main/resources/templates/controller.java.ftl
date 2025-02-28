package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
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
<#if restControllerStyle>
    @RestController
<#else>
    @RestController
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
    </#if>
    @Autowired
    private ${table.serviceName} ${(table.serviceName)?uncap_first};

    @RequestMapping("/info")
    public ${entity} getById(@RequestParam("id") BigInteger id){
    return ${(table.serviceName)?uncap_first}.getById(id);
    }

    @RequestMapping("/extractInfo")
    public ${entity} extractById(@RequestParam("id") BigInteger id){
    return ${(table.serviceName)?uncap_first}.extractById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestParam("${entity}") ${entity} ${entity?uncap_first}){
    return ${(table.serviceName)?uncap_first}.insert(${entity?uncap_first}) != 0 ? "成功" : "失败";
    }

    @RequestMapping("/update")
    public String update(@RequestParam("${entity}") ${entity} ${entity?uncap_first}){
    return ${(table.serviceName)?uncap_first}.update(${entity?uncap_first}) != 0 ? "成功" : "失败";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id")BigInteger id){
    return ${(table.serviceName)?uncap_first}.delete(id) != 0 ? "成功" : "失败";
    }

    }
</#if>
