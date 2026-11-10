package ali.ragab.saas_multi_tennant_app.mappers;

import ali.ragab.saas_multi_tennant_app.config.TenantContext;
import ali.ragab.saas_multi_tennant_app.entities.Category;
import ali.ragab.saas_multi_tennant_app.requests.CategoryRequest;
import ali.ragab.saas_multi_tennant_app.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(final CategoryRequest request) {
       return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .tenantId(TenantContext.getCurrentTenant())
                .deleted(false)
                .build();
    }

  public CategoryResponse toResponse(final Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

}
