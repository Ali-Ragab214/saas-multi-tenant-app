package ali.ragab.saas_multi_tennant_app.services.impl;

import ali.ragab.saas_multi_tennant_app.entities.Category;
import ali.ragab.saas_multi_tennant_app.exceptions.DuplicateResourceException;
import ali.ragab.saas_multi_tennant_app.exceptions.ResourceNotFoundException;
import ali.ragab.saas_multi_tennant_app.mappers.CategoryMapper;
import ali.ragab.saas_multi_tennant_app.repositories.CategoryRepository;
import ali.ragab.saas_multi_tennant_app.requests.CategoryRequest;
import ali.ragab.saas_multi_tennant_app.responses.CategoryResponse;
import ali.ragab.saas_multi_tennant_app.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
   public void create(CategoryRequest request) {
       checkIfCategoryExistByname(request.getName());
       categoryRepository.save(categoryMapper.toEntity(request));
    }


    @Override
    public void update(String id, CategoryRequest request) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        if(!category.getName().equalsIgnoreCase(request.getName())){
            checkIfCategoryExistByname(request.getName());
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        final Category updatedCategory = categoryRepository.save(category);

    }


    @Override
    public CategoryResponse findById(String id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        return categoryMapper.toResponse(category);
    }


    @Override
    public void delete(String id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        categoryRepository.delete(category);
    }


    private  void checkIfCategoryExistByname(String name){
      final Optional<Category>  category = categoryRepository.findByNameIgnoreCase(name);
      if(category.isPresent()){
            log.debug("Category with name {} already exists", name);
            throw new DuplicateResourceException("Category with name " + name + " already exists");
            //I can customize the exception to be more specific and meaningful but i focus on multi tenant in this projct
        }
    }

}
