package de.htwberlin.archivewizard.category.key;

import de.htwberlin.archivewizard.category.group.CategoryGroup;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryKeyRepository extends JpaRepository<CategoryKey, Long> {
  public List<CategoryKey> getByCategoryGroup(CategoryGroup categoryGroup);

}
