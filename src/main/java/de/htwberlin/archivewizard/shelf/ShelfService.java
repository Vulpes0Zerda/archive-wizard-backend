package de.htwberlin.archivewizard.shelf;

import java.util.List;
import org.springframework.stereotype.Service;

import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.user.UserRepository;
import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.category.group.CategoryGroupRepository;

@Service
public class ShelfService {
  private final UserRepository userRepository;
  private final ShelfRepository shelfRepository;
  private final CategoryGroupRepository categoryGroupRepository;

  public ShelfService(UserRepository userRepository, ShelfRepository shelfRepository,
      CategoryGroupRepository categoryGroupRepository) {
    this.userRepository = userRepository;
    this.shelfRepository = shelfRepository;
    this.categoryGroupRepository = categoryGroupRepository;
  }

  public List<Shelf> getAllShelfs(Number userId) {
    return shelfRepository.findByUserId(userId.intValue());
  }

  public Shelf createShelf(CreateShelfRequest shelfInformation, Number userId) {
    User user = userRepository.getReferenceById(userId.intValue());
    CategoryGroup categoryGroup =
        categoryGroupRepository.getReferenceById(shelfInformation.categoryGroupId().longValue());
    Shelf shelf =
        new Shelf(user, categoryGroup, shelfInformation.name(), shelfInformation.position());
    return shelfRepository.save(shelf);
  }
}
