<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">
    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="${cacheClassName}"/>
    </#if>

    <#list table.fields as field>
        <#if field.keyFlag>
            <!-- 新增表${table.name}信息 -->
            <insert id="insert" parameterType="${package.Entity}.${entity}" useGeneratedKeys="true"
                    keyProperty="${entity?uncap_first}.id">
                insert ${table.name} (
                <#list table.fields as field>
                    <if test="${entity?uncap_first}.${field.propertyName} != null and ${entity?uncap_first}.${field.propertyName} != ''"><#if field_index gt 0>,</#if>${field.name}</if>
                </#list>
                ) values (
                <#list table.fields as field>
                    <if test="${entity?uncap_first}.${field.propertyName} != null and ${entity?uncap_first}.${field.propertyName} != ''"><#if field_index gt 0>,</#if>${r"#{"}${entity?uncap_first}.${field.propertyName}${r"}"}</if>
                </#list>
                )
            </insert>
        </#if>
    </#list>

    <#list table.fields as field>
        <#if field.keyFlag>
        <#--include是引用SQL代码,refid 是引用的sql的id名称，一定要唯一-->
            <!-- 根据主键${field.propertyName}更新表${table.name}信息 -->
            <update id="update" parameterType="${package.Entity}.${entity}" useGeneratedKeys="true"
                    keyProperty="${entity?uncap_first}.id">
                update ${table.name} set
                <#list table.fields as field>
                    <if test="${entity?uncap_first}.${field.propertyName} != null and ${entity?uncap_first}.${field.propertyName} != ''"><#if field_index gt 0>,</#if>${field.name}=${r"#{"}${entity?uncap_first}.${field.propertyName}${r"}"}</if>
                </#list>
                where
                <#list table.fields as field><#if field.keyFlag>${field.name}=${r"#{"}${entity?uncap_first}.${field.propertyName}${r"}"}</#if></#list>
            </update>
        </#if>
    </#list>

</mapper>
