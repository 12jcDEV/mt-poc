package com.mtpoc.app.tenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {


    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.set(null);
    }

}
