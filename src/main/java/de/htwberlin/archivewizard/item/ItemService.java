package de.htwberlin.archivewizard.item;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.stereotype.Service;

import de.htwberlin.archivewizard.shelf.Shelf;
import de.htwberlin.archivewizard.shelf.ShelfRepository;

@Service 
public class ItemService {

  private ShelfRepository shelfRepository;

  public ItemService(ShelfRepository shelfRepository){
    this.shelfRepository = shelfRepository;
  }

  public List<Item> getAllItems(Number userId, Long shelfId) throws Exception{
    Shelf shelf = shelfRepository.getReferenceById(shelfId);

    if(shelf.getUser().getId().equals(userId.intValue())){
      return shelf.getItems();
    }
    else {
      throw new AccessDeniedException("This user has no access to this shelf.");
    }
  }
}
