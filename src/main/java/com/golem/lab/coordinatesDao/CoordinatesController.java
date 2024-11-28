package com.golem.lab.coordinatesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoordinatesController {

    @Autowired
    private CoordinatesService coordinatesService;

    @GetMapping("/coordinates")
    public String getCoordinates(Model model) {
        List<Coordinates> coordinates = coordinatesService.findAllCoordinates();
        model.addAttribute("coordinates", coordinates);
        return "coordinates";
    }



}
