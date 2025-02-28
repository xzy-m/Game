package ${package.Service};

import ${package.Entity}.${entity};
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

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
    @Service
    public class ${table.serviceName}{

    @Resource
    private ${table.mapperName} ${(table.mapperName)?uncap_first};

    public ${entity} getById(BigInteger id){
    return ${table.mapperName?uncap_first}.getById(id);
    }

    public ${entity} extractById(BigInteger id){
    return ${table.mapperName?uncap_first}.extractById(id);
    }

    public int insert(${entity} ${entity?uncap_first}){
    return ${table.mapperName?uncap_first}.insert(${entity?uncap_first});
    }

    public int update(${entity} ${entity?uncap_first}){
    return ${table.mapperName?uncap_first}.update(${entity?uncap_first});
    }

    public int delete(BigInteger id){
    int time = (int) (System.currentTimeMillis() / 1000);
    return ${table.mapperName?uncap_first}.delete(time,id);
    }
    }
</#if>
