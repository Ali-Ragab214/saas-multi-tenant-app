package ali.ragab.saas_multi_tennant_app.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TenantHibernateFilter {
    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* ali.ragab.saas_multi_tennant_app.services.*.*(..))")
    public void activateFilter()
    {
        final String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null && !tenantId.isBlank()) {
            final Session session = entityManager.unwrap(Session.class);
            //activate the filter to enable multi-tenancy support
            session.enableFilter("tenantFilter")
                    .setParameter("tenantId", tenantId);


        }

    }
}
