package de.htwberlin.archivewizard.shelf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/shelf-manager")
public class ShelfController {
    @GetMapping("./get-overview/{userId}")
    public String getAllShelfs(@PathVariable Integer userId) {
        return new String();
    }
    
}
