package com.golem.lab.controller;

import com.golem.lab.classes.*;
import com.golem.lab.repository.MovieRepo;
import com.golem.lab.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieService movieService;

    @RequestMapping("/home/updateMovie")
    public String updateMovie(Model model, @RequestParam int movieId) {
        Movie newMovie = movieService.findMovieById(movieId);
        model.addAttribute("newMovie", newMovie);

        return "/home/newMovie";
    }

    @RequestMapping("/home/deleteMovie")
    public String deleteMovie(Model model, @RequestParam int movieId) {
        movieService.deleteMovieById(movieId);
        return "redirect:/home/movies";
    }

    @GetMapping("/home/movies")
    public String getMovies(Model model) {
        List<Movie> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);

        return "/home/movies";
    }

    @GetMapping(value = "/home/newMovie")
    public String addMovie(Model model) {
        Movie newMovie = new Movie();
        Coordinates test_coord = new Coordinates();
        test_coord.setX(55);
        test_coord.setY(55);

        newMovie.setName("John");
        newMovie.setOscarsCount(1);
        newMovie.setBudget(250_000_000f);
        newMovie.setTotalBoxOffice(1_000_000_000f);
        newMovie.setGoldenPalmCount(2L);
        newMovie.setLength(3);
        newMovie.setDirector(getPerson());
        newMovie.setScreenwriter(getPerson());
        newMovie.setOperator(getPerson());
        newMovie.setGenre(MovieGenre.HORROR);
        newMovie.setMpaaRating(MpaaRating.R);
        newMovie.setCoordinates(test_coord);

        model.addAttribute("newMovie", newMovie);

        return "/home/newMovie";
    }

    @PostMapping(value = "/home/newMovie")
    public String addMovie(@ModelAttribute("newMovie") Movie newMovie, BindingResult bindingResult, Model model) {

//        if (movieRepo.existsById(newMovie.getId())) {
//            movieRepo.deleteById(newMovie.getId());
//            movieRepo.save(newMovie);
//            return "redirect:/home/movies";
//        }
        movieRepo.save(newMovie);

        return "redirect:/home";
    }

    private static Person getPerson() {
        Location test_loc = new Location();
        test_loc.setName("555");
        test_loc.setX(5f);
        test_loc.setY(5);
        test_loc.setZ(5);


        Person test = new Person();
        test.setName("555");
        test.setEyeColor(Color.BLUE);
        test.setHairColor(Color.BLUE);
        test.setHeight(555f);
        test.setLocation(test_loc);
        test.setNationality(Country.RUSSIA);
        return test;
    }
}
