package com.jianmu.constant;

/**
 * @author lime
 */
public interface AuthConstant {

    /**
     * 管理端路径
     */
    String MANAGER_URL_PATTERN = "/manager/**";
    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";


    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

}
