package de.htwberlin.archivewizard.category.group;

import org.springframework.stereotype.Service;
import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.user.UserRepository;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;
    private final UserRepository userRepository;

    public CategoryGroupService(CategoryGroupRepository categoryGroupRepository,
            UserRepository userRepository) {
        this.categoryGroupRepository = categoryGroupRepository;
        this.userRepository = userRepository;
    }

    public CategoryGroup createCategoryGroup(CreateCategoryGroupRequest createCategoryGroupRecord,
            Number userId) {
        User user = userRepository.getReferenceById(userId.intValue());
        CategoryGroup categoryGroup = new CategoryGroup(createCategoryGroupRecord.name(), user);
        return categoryGroupRepository.save(categoryGroup);
    }

}
