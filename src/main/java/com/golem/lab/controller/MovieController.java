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
import java.util.Objects;

@Controller
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieService movieService;


    @RequestMapping("/home/filter")
    public String filterPerson(Model model, @RequestParam String field, @RequestParam String filter, @RequestParam int sort) {
        List<Movie> movies = movieService.findAllMovies();

        movies = movies.stream()
                .filter(movie -> !Objects.equals(field, "Name")                    || movie.getName().contains(filter))
                .filter(movie -> !Objects.equals(field, "Coord X")                 || String.valueOf(movie.getCoordinates().getX()).contains(filter))
                .filter(movie -> !Objects.equals(field, "Coord Y")                 || String.valueOf(movie.getCoordinates().getY()).contains(filter))
                .filter(movie -> !Objects.equals(field, "Creation Date")           || movie.getCreationDate().toString().contains(filter))
                .filter(movie -> !Objects.equals(field, "Oscar amount")            || String.valueOf(movie.getOscarsCount()).contains(filter))
                .filter(movie -> !Objects.equals(field, "Budget")                  || String.valueOf(movie.getBudget()).contains(filter))
                .filter(movie -> !Objects.equals(field, "MPAA Rating")             || movie.getMpaaRating().toString().contains(filter))
                .filter(movie -> !Objects.equals(field, "Total boxing office")     || String.valueOf(movie.getTotalBoxOffice()).contains(filter))
                .filter(movie -> !Objects.equals(field, "Length")                  || String.valueOf(movie.getLength()).contains(filter))
                .filter(movie -> !Objects.equals(field, "Golden palm amount")      || String.valueOf(movie.getGoldenPalmCount()).contains(filter))

                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Name"))                ? movie1.getName().compareTo(movie2.getName())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Coord X"))             ? Float.compare(movie1.getCoordinates().getX(), movie2.getCoordinates().getX())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Coord Y"))             ? Float.compare(movie1.getCoordinates().getY(), movie2.getCoordinates().getY())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Creation Date"))       ? movie1.getCreationDate().compareTo(movie2.getCreationDate())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Oscar amount"))        ? Integer.compare(movie1.getOscarsCount(), movie2.getOscarsCount())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Budget"))              ? Float.compare(movie1.getBudget(), movie2.getBudget())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "MPAA Rating"))         ? movie1.getMpaaRating().compareTo(movie2.getMpaaRating())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Total boxing office")) ? Float.compare(movie1.getTotalBoxOffice(), movie2.getTotalBoxOffice())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Length"))              ? Integer.compare(movie1.getLength(), movie2.getLength())*sort : 0)
                .sorted((movie1, movie2) -> (sort == 0 || Objects.equals(field, "Golden palm amount"))  ? Long.compare(movie1.getGoldenPalmCount(), movie2.getGoldenPalmCount())*sort : 0)
                .toList();


        model.addAttribute("field", field);
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("movies", movies);
        return "/home/movies";
    }
    

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
        movieRepo.save(newMovie);
        return "redirect:/home/movies";
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
