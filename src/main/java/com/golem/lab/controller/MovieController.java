package com.golem.lab.controller;

import com.golem.lab.classes.*;
import com.golem.lab.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @GetMapping(value = "/home/newMovie")
    public String addMovie(Model model) {

        Movie newMovie = new Movie();
        Coordinates test_coord = new Coordinates();
        test_coord.setX(1);
        test_coord.setY(1);

        newMovie.setBudget(999f);
        newMovie.setDirector(getPerson());
        newMovie.setScreenwriter(getPerson());
        newMovie.setOperator(getPerson());
        newMovie.setGenre(MovieGenre.ACTION);
        newMovie.setMpaaRating(MpaaRating.NC_17);
        newMovie.setCoordinates(test_coord);

        model.addAttribute("newMovie", newMovie);
        return "/home/newMovie";
    }



    @PostMapping(value = "/home/newMovie")
    public String addMovie(@ModelAttribute("newMovie") Movie newMovie, BindingResult bindingResult, Model model) {

        Coordinates test_coord = new Coordinates();
        test_coord.setX(1);
        test_coord.setY(1);

        newMovie.setDirector(getPerson());
        newMovie.setScreenwriter(getPerson());
        newMovie.setOperator(getPerson());
        newMovie.setGenre(MovieGenre.ACTION);
        newMovie.setMpaaRating(MpaaRating.NC_17);
        newMovie.setCoordinates(test_coord);
        movieRepo.save(newMovie);

        return "redirect:/home";
    }

    private static Person getPerson() {
        Location test_loc = new Location();
        test_loc.setName("name");
        test_loc.setX(1f);
        test_loc.setY(1);
        test_loc.setZ(1);


        Person test = new Person();
        test.setName("123");
        test.setEyeColor(Color.BLACK);
        test.setHairColor(Color.BLACK);
        test.setHeight(123.0f);
        test.setLocation(test_loc);
        test.setNationality(Country.GERMANY);
        return test;
    }
}
