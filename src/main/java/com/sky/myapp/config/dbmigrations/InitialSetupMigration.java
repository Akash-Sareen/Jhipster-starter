package com.sky.myapp.config.dbmigrations;

import com.sky.myapp.config.Constants;
import com.sky.myapp.domain.Authority;
import com.sky.myapp.domain.Company;
import com.sky.myapp.domain.Privilege;
import com.sky.myapp.domain.User;
import com.sky.myapp.security.AuthoritiesConstants;
import com.sky.myapp.security.PrivilegesConstants;
import com.sky.myapp.security.TenantContext;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup.
 */
@ChangeUnit(id = "users-initialization", order = "001")
public class InitialSetupMigration {

    private final MongoTemplate template;

    public InitialSetupMigration(MongoTemplate template) {
        this.template = template;
    }

    @Execution
    public void changeSet() {
        List<String> privileges = PrivilegesConstants.getAllPrivileges();
        Map<String, Privilege> privilegeMap = new HashMap<>();
        privileges
            .stream()
            .forEach(privilege -> {
                Privilege pri = new Privilege(privilege);
                pri = template.save(pri);
                privilegeMap.put(privilege, pri);
            });

        Authority userAuthority = createUserAuthority(privilegeMap);
        userAuthority = template.save(userAuthority);
        Authority adminAuthority = createAdminAuthority(privilegeMap);
        adminAuthority = template.save(adminAuthority);
        addUsers(userAuthority, adminAuthority);
    }

    @RollbackExecution
    public void rollback() {}

    private Authority createAuthority(String authority, Set<Privilege> privileges) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(authority);
        adminAuthority.setPrivileges(privileges);
        return adminAuthority;
    }

    private Authority createAdminAuthority(Map<String, Privilege> privilegeMap) {
        Set<Privilege> privileges = privilegeMap.values().stream().collect(Collectors.toSet());
        Authority adminAuthority = createAuthority(AuthoritiesConstants.ADMIN, privileges);
        return adminAuthority;
    }

    private Authority createUserAuthority(Map<String, Privilege> privilegeMap) {
        Set<Privilege> privileges = Set.of(privilegeMap.get(PrivilegesConstants.PRIVILEGE_READ));
        Authority userAuthority = createAuthority(AuthoritiesConstants.USER, privileges);
        return userAuthority;
    }

    private void addUsers(Authority userAuthority, Authority adminAuthority) {
        Company company = new Company();
        company.setName("CUSTOMER1");
        company.setId("1");
        template.save(company);

        company = new Company();
        company.setName("CUSTOMER2");
        company.setId("2");
        template.save(company);

        User user = createUser(userAuthority);
        user.setCompany(company);
        template.save(user);

        company = new Company();
        company.setName("ADMIN_CONSOLE");
        company.setId("0");
        template.save(company);

        User admin = createAdmin(adminAuthority, userAuthority);
        template.save(admin);
    }

    private User createUser(Authority userAuthority) {
        User userUser = new User();
        userUser.setId("user-2");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(Constants.SYSTEM);
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        return userUser;
    }

    private User createAdmin(Authority adminAuthority, Authority userAuthority) {
        User adminUser = new User();
        adminUser.setId("user-1");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(Constants.SYSTEM);
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        return adminUser;
    }
}
