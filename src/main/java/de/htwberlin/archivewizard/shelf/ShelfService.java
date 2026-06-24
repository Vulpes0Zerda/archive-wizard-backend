package de.htwberlin.archivewizard.shelf;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.user.UserRepository;

@Service
public class ShelfService {
    private final UserRepository userRepository;

    public List<Shelf> getAllShelfs (Integer userId){
        Optional<User> user = Optional.ofNullable(userRepository.byById(userId));
    }
}
