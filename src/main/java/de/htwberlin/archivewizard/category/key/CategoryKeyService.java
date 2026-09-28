package de.htwberlin.archivewizard.category.key;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CategoryKeyService {

  CategoryKeyRepository categoryKeyRepository;

  public CategoryKeyService(CategoryKeyRepository categoryKeyRepository) {
    this.categoryKeyRepository = categoryKeyRepository;
  }

  @Transactional
  public List<CategoryKey> getCategoryKeys(Number userId, Long categoryGroupId) throws Exception {
    List<CategoryKey> categoryKeys = categoryKeyRepository.findByCategoryGroupId(categoryGroupId);
    for (CategoryKey categoryKey : categoryKeys) {
      if (categoryKey.getCategoryGroup().getUser() == null) {
      } else if (categoryKey.getCategoryGroup().getUser().getId().equals(userId.intValue())) {
      } else {
        throw new NoSuchElementException("Did not find a CategoryGroup of id " + categoryGroupId);
      }
    }
    return categoryKeys;

  }

}
