package de.htwberlin.archivewizard.category.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroupService (CategoryGroupRepository categoryGroupRepository){
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public List<CategoryGroup> createCategoryGroup(CreateCategoryGroupRecord createCategoryGroupRecord){
        CategoryGroup categoryGroup = new CategoryGroup(createCategoryGroupRecord.name());
        categoryGroupRepository.save(categoryGroup);
        ArrayList<CategoryGroup> categoryGroups = new ArrayList<CategoryGroup>();
        categoryGroups.add(categoryGroup);
        return categoryGroups;
    }
    
}
