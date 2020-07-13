package com.example.common.utils;

import com.example.common.constant.Permission;

public class TenantUtil {

    public static boolean isAllowModifyOrDelete(String tenantId){
        if(null == tenantId){
            return true;
        }

        if(!EnvHolder.getHolder().getTenantIds().contains(tenantId) && !EnvHolder.getHolder().getTenantId().equals(Permission.PERMISSION_ALL) ){
            return false;
        }
        return true;
    }
}
