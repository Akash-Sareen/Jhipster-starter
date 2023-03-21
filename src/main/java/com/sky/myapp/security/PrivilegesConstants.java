package com.sky.myapp.security;

import java.util.List;

public final class PrivilegesConstants {

    public static final String PRIVILEGE_READ = "PRIVILEGE_READ";

    public static final String PRIVILEGE_WRITE = "PRIVILEGE_WRITE";

    public static final String PRIVILEGE_DELETE = "PRIVILEGE_DELETE";

    public static final String PRIVILEGE_ADMIN = "PRIVILEGE_ADMIN";

    public static final String PRIVILEGE_USER = "PRIVILEGE_USER";

    public static final String PRIVILEGE_ANONYMOUS = "PRIVILEGE_ANONYMOUS";

    public static List<String> getAllPrivileges() {
        return List.of(PRIVILEGE_READ, PRIVILEGE_WRITE, PRIVILEGE_DELETE, PRIVILEGE_ADMIN, PRIVILEGE_USER, PRIVILEGE_ANONYMOUS);
    }
}
