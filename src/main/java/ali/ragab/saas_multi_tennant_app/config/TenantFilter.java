package ali.ragab.saas_multi_tennant_app.config;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter {

    private  static  final String TENANT_HEADER = "X-TenantID";
@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String tenantId = resolveHeader(httpRequest);
        if(tenantId == null || tenantId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Missing or empty X-TenantID header");
            return;
        }

        try {
TenantContext.setCurrentTenant(tenantId);
filterChain.doFilter(request, response);
        }
        finally {
            TenantContext.clearCurrentTenant();//this will remove the current tenant from the thread local storage after the request is processed,
            // preventing any potential memory leaks or incorrect tenant context in subsequent requests.  ودا بيضمن ان الثريد لما يعاد استخدامه تاني مش هيبقا شايل نفس ال
        }

}

private String resolveHeader(final HttpServletRequest request) {
    final String tenantId = request.getHeader(TENANT_HEADER);
    if (tenantId != null && !tenantId.isBlank()) {
        return tenantId.toLowerCase();
    }
    return null;
}

}
