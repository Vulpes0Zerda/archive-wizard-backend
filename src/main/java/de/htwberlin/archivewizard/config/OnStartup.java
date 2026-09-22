package de.htwberlin.archivewizard.config;

import de.htwberlin.archivewizard.category.group.CategoryGroupRepository;
import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.category.key.CategoryKeyRepository;
import de.htwberlin.archivewizard.category.key.CategoryKey;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnStartup implements ApplicationListener<ApplicationReadyEvent> {
  private CategoryGroupRepository categoryGroupRepository;
  private CategoryKeyRepository categoryKeyRepository;
  private Logger logger = LoggerFactory.getLogger(OnStartup.class);

  public OnStartup(CategoryGroupRepository categoryGroupRepository,
      CategoryKeyRepository categoryKeyRepository) {
    this.categoryGroupRepository = categoryGroupRepository;
    this.categoryKeyRepository = categoryKeyRepository;
  }

  @Override
  @Transactional
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    List<CategoryGroup> createdCategoryGroups = createCategoryGroups();
    logger.debug("Created Category Group Entities: " + createdCategoryGroups.toString());
    createCategoryKeys(createdCategoryGroups);
  }

  private List<CategoryGroup> createCategoryGroups() {
    List<CategoryGroup> categoryGroups = categoryGroupRepository.findAllByUserIsNull();
    List<CategoryGroup> templateCategoryGroups = new ArrayList<CategoryGroup>();
    templateCategoryGroups.add(new CategoryGroup("Books"));
    templateCategoryGroups.add(new CategoryGroup("Films"));
    templateCategoryGroups.add(new CategoryGroup("Games"));

    List<CategoryGroup> templateCategoryGroupsToSave = new ArrayList<CategoryGroup>();
    templateCategoryGroupsToSave.addAll(templateCategoryGroups);

    for (CategoryGroup categoryGroup : categoryGroups) {
      for (CategoryGroup templateCategoryGroup : templateCategoryGroups) {
        if (categoryGroup.getName().equals(templateCategoryGroup.getName())) {
          templateCategoryGroupsToSave.remove(templateCategoryGroup);
        }
      }
    }

    return categoryGroupRepository.saveAll(templateCategoryGroupsToSave);
  }

  private Map<CategoryGroup, List<CategoryKey>> createCategoryKeys(
      List<CategoryGroup> savedCategoryGroups) {
    Map<CategoryGroup, List<CategoryKey>> allSavedCategoryKeys = new HashMap<>();
    for (CategoryGroup categoryGroup : savedCategoryGroups) {
      List<CategoryKey> templateCategoryKeys = new ArrayList<>();
      switch (categoryGroup.getName()) {
        case "Books":
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Title", (short) 0));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Subtitle", (short) 1));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Author", (short) 2));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Personal Rating", (short) 3));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Public Rating", (short) 4));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Genre", (short) 5));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Contributors", (short) 6));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "ISBN", (short) 7));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Publisher", (short) 8));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Publication Date", (short) 9));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Language", (short) 10));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Description", (short) 11));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Completed", (short) 12));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Best Quote", (short) 13));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Notes", (short) 14));
          break;
        case "Films":
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Romanized Title", (short) 0));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Original Title", (short) 1));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Genre", (short) 2));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Personal Rating", (short) 3));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Public Rating", (short) 4));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Release Date", (short) 5));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Director", (short) 6));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Cast", (short) 7));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Writers", (short) 8));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Producer", (short) 9));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Runtime", (short) 10));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Language", (short) 11));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Description", (short) 12));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Completed", (short) 13));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Best Quote", (short) 14));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Notes", (short) 15));
          break;
        case "Games":
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Title", (short) 0));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Alternative Title", (short) 1));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Genre", (short) 2));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Personal Rating", (short) 3));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Public Rating", (short) 4));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Release Date", (short) 5));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Developer", (short) 6));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Publisher", (short) 7));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Voice Actors", (short) 8));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Platforms", (short) 9));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Game Engine", (short) 10));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Languages", (short) 11));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Age Rating", (short) 12));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Description", (short) 13));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Completed", (short) 14));
          templateCategoryKeys.add(new CategoryKey(categoryGroup, "Notes", (short) 15));
          break;
        default:
          break;
      }
      logger.debug("Saving Category Keys: " + templateCategoryKeys.toString());
      allSavedCategoryKeys.put(categoryGroup, categoryKeyRepository.saveAll(templateCategoryKeys));
    } ;
    return allSavedCategoryKeys;
  }
}
