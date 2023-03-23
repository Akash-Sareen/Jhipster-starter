package com.sky.myapp.security;

import com.sky.myapp.security.exception.TenantNotFoundException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
        throws IOException, ServletException {
        final String tenant = request.getHeader(TENANT_ID_HEADER);

        // TODO: WHEN UI IS DONE, PASS X-Tenant IN THE HEADER
        //        if (StringUtils.hasText(tenant)) {
        //            TenantContext.setCompanyId(tenant);
        //            request.getSession().setAttribute("accountId", tenant);
        //        } else {
        //            throw new TenantNotFoundException("Tenant not found.");
        //        }

        TenantContext.setCompanyId("1");
        request.getSession().setAttribute("accountId", "1");
        filterChain.doFilter(request, response);
    }
}
