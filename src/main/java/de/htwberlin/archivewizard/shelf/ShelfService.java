package de.htwberlin.archivewizard.shelf;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.user.UserRepository;

@Service
public class ShelfService {
    private final UserRepository userRepository;
    private final ShelfRepository shelfRepository;

    public ShelfService(UserRepository userRepository, ShelfRepository shelfRepository) {
        this.userRepository = userRepository;
        this.shelfRepository = shelfRepository;
    }

    public List<Shelf> getAllShelfs(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return shelfRepository.findByUserId(userId);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public List<Shelf> createShelf(CreateShelfRecord shelfInformation) {
        Shelf shelf = new Shelf(shelfInformation.user(), shelfInformation.categoryGroup(),
                shelfInformation.name(), shelfInformation.position());
        shelfRepository.save(shelf);
        return shelfRepository.findByUserId(shelfInformation.user().getId());
    }
}
