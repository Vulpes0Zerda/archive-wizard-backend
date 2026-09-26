package de.htwberlin.archivewizard.item;

import java.nio.file.AccessDeniedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htwberlin.archivewizard.shelf.Shelf;
import de.htwberlin.archivewizard.shelf.ShelfRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ItemService {

  private ShelfRepository shelfRepository;
  private ItemRepository itemRepository;
  @Autowired
  private EntityManager entityManager;

  public ItemService(ShelfRepository shelfRepository, ItemRepository itemRepository,
      EntityManager entityManager) {
    this.shelfRepository = shelfRepository;
    this.itemRepository = itemRepository;
    this.entityManager = entityManager;
  }

  @Transactional
  public List<Item> getAllItems(Number userId, Long shelfId) throws Exception {
    Shelf shelf = shelfRepository.getReferenceById(shelfId);

    if (shelf.getUser().getId().equals(userId.intValue())) {
      return shelf.getItems();
    } else {
      throw new AccessDeniedException("This user has no access to this shelf.");
    }
  }

  @Transactional
  public Item createItem(Number userId, CreateItemRequest newItem) throws Exception {
    Shelf shelf = shelfRepository.getReferenceById(newItem.shelfId());
    if (shelf.getUser().getId().equals(userId.intValue())) {
      Item item = itemRepository.saveAndFlush(new Item(shelf, newItem.name(), new byte[0]));
      entityManager.refresh(item);
      return item;
    } else {
      throw new AccessDeniedException("This user has no permission to create this item.");
    }
  }

  @Transactional
  public Long deleteItem(Number userId, Long itemId) throws Exception {
    Item item = itemRepository.getReferenceById(itemId);

    if (item.getShelf().getUser().getId().equals(userId.intValue())) {
      itemRepository.delete(item);
      return item.getId();
    } else {
      throw new AccessDeniedException("This user has no permission to delete this item.");
    }
  }
}
