package de.htwberlin.archivewizard.category.value;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryValueRepository extends JpaRepository<CategoryValue, Long> {
  public List<CategoryValue> findByItemIdAndCategoryKeyId(Long itemId, Long categoryKeyId);
}
