package de.htwberlin.archivewizard.shelf;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/shelf-manager")
@CrossOrigin(origins = "http://localhost:4200")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    // todo: needs check if user is logged in
    @GetMapping("/get-overview/{userId}")
    public ResponseEntity<List<Shelf>> getAllShelfs(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(this.shelfService.getAllShelfs(userId));
    }

    @PostMapping("/create-shelf")
    public ResponseEntity<List<Shelf>> createShelf(@RequestBody CreateShelfRecord shelf) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shelfService.createShelf(shelf));
    }

}
