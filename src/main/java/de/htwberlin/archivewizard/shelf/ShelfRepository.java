package de.htwberlin.archivewizard.shelf;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {

  List<Shelf> findByUserId(Integer userId);

}
