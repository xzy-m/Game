package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;

<#if mapperAnnotationClass??>
    import ${mapperAnnotationClass.name};
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotationClass??>
    @${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
    @Mapper
    public interface ${table.mapperName} {

    @Select("select * from ${entity?uncap_first} where is_deleted = 0 and id = ${r'#{id}'}")
    ${entity} getById(@Param("id") BigInteger id);

    @Select("select * from ${entity?uncap_first} where id = ${r'#{id}'}")
    ${entity} extractById(@Param("id") BigInteger id);

    int insert(@Param("${entity?uncap_first}") ${entity} ${entity?uncap_first});

    int update(@Param("${entity?uncap_first}") ${entity} ${entity?uncap_first});

    @Delete("update ${entity?uncap_first} set is_deleted = 1,update_time = ${r'#{updateTime}'} where id = ${r'#{id}'}")
    int delete(@Param("updateTime") Integer updateTime,@Param("id") BigInteger id);

    }
</#if>
