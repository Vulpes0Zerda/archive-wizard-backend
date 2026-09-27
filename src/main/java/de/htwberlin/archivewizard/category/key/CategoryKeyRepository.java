package de.htwberlin.archivewizard.category.key;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryKeyRepository extends JpaRepository<CategoryKey, Long> {
  public List<CategoryKey> findByCategoryGroupId(Long categoryGroupId);

}
