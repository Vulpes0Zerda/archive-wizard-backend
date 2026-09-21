package de.htwberlin.archivewizard.category.group;

import java.util.List;
import de.htwberlin.archivewizard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
  List<CategoryGroup> findByUserOrUserIsNull(User user);
}
