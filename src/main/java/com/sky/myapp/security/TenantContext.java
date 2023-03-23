package com.sky.myapp.security;

public class TenantContext {

    protected static final ThreadLocal<String> threadLocal = new InheritableThreadLocal<String>() {
        @Override
        protected String initialValue() {
            try {
                return String.class.getDeclaredConstructor().newInstance();
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected String childValue(String parentValue) {
            return new String(parentValue);
        }
    };

    public static void setCompanyId(String companyId) {
        threadLocal.set(companyId);
    }

    public static String getCompanyId() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
