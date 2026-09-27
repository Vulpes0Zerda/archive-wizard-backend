package de.htwberlin.archivewizard.category.value;

import java.util.ArrayList;
import java.util.List;
import javax.management.relation.RelationException;
import org.springframework.stereotype.Service;
import de.htwberlin.archivewizard.category.key.CategoryKey;
import de.htwberlin.archivewizard.category.key.CategoryKeyRepository;
import de.htwberlin.archivewizard.item.Item;
import de.htwberlin.archivewizard.item.ItemRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryValueService {
  private final CategoryValueRepository categoryValueRepository;
  private final CategoryKeyRepository categoryKeyRepository;
  private final ItemRepository itemRepository;

  public CategoryValueService(CategoryValueRepository categoryValueRepository,
      CategoryKeyRepository categoryKeyRepository, ItemRepository itemRepository) {
    this.categoryValueRepository = categoryValueRepository;
    this.categoryKeyRepository = categoryKeyRepository;
    this.itemRepository = itemRepository;
  }

  @Transactional
  public List<CategoryValue> updateCategoryValues(Number userId,
      List<UpdateCategoryValueRequest> updateCategoryValueData) throws Exception {

    List<CategoryValue> categoryValuesToSave = new ArrayList<CategoryValue>();

    for (UpdateCategoryValueRequest categoryValueRequest : updateCategoryValueData) {
      List<CategoryValue> categoryValues = categoryValueRepository.findByItemIdAndCategoryKeyId(
          categoryValueRequest.itemId(), categoryValueRequest.categoryKeyId());
      if (categoryValues.size() == 0) {
        Item item = itemRepository.getReferenceById(categoryValueRequest.itemId());
        CategoryKey categoryKey =
            categoryKeyRepository.getReferenceById(categoryValueRequest.categoryKeyId());
        if (item.getShelf().getCategoryGroup().equals(categoryKey.getCategoryGroup())) {

          categoryValuesToSave
              .add(new CategoryValue(categoryValueRequest.value(), categoryKey, item));
        } else {
          throw new RelationException(
              "Shelf does not have the same parent category group as Category Key.");
        }
      } else {
        for (CategoryValue categoryValue : categoryValues) {
          categoryValue.setValue(categoryValueRequest.value());
          if (categoryValue.getItem().getShelf().getCategoryGroup()
              .equals(categoryValue.getCategoryKey().getCategoryGroup())) {
            categoryValuesToSave.add(categoryValue);
          } else {
            throw new RelationException(
                "Shelf does not have the same parent category group as Category Key.");
          }
        }
      }
    }

    return categoryValueRepository.saveAll(categoryValuesToSave);
  }
}
