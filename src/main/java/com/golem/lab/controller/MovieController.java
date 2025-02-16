package com.golem.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.golem.lab.classes.*;
import com.golem.lab.repository.MovieRepo;
import com.golem.lab.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieService movieService;


    @PostMapping(value = "/home/demo-file-download")
    public ResponseEntity<byte[]> demo() { // (1) Return byte array response

        List<Movie> movies = movieService.findAllMovies();
        Movie movie = movies.get(0);

        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String demoContent = "";
        try {
            demoContent = ow.writeValueAsString(movie);
        }
        catch (Exception e) {
            demoContent = e.toString();
        }

//        String demoContent = "This is dynamically generated content in demo file"; // (2) Dynamic content




        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("demo-file.json").build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(demoContent.getBytes()); // (5) Return Response
    }

    @RequestMapping("/home/sop_action")
    public String specialOps(Model model, @RequestParam int sop, @RequestParam int gpp, @RequestParam String dgpp, @RequestParam int lpp) {

        model.addAttribute("sop", sop);
        model.addAttribute("gpp", gpp);
        model.addAttribute("dgpp", dgpp);
        model.addAttribute("lpp", lpp);


        List<Movie> movies = movieService.findAllMovies();
        switch (sop) {
            case 1:
                Movie movie_n = movies.stream()
                        .max(Comparator.comparing(Movie::getName)).get();
                model.addAttribute("sop_result", "Movie: id(" + movie_n.getId() + ") name(" + movie_n.getName()+").");

                break;
            case 2:
                long gp_count = movies.stream()
                        .filter(movie -> movie.getGoldenPalmCount() > gpp)
                        .count();
                model.addAttribute("sop_result", "There are "+ gp_count+" films, which have more than "+gpp+" golden palms .");

                break;
            case 3:
                List<Long> uniqueGoldenPalmCounts = movies.stream()
                        .map(Movie::getGoldenPalmCount)
                        .distinct()
                        .toList();

                model.addAttribute("sop_result", "Unique goldenpalms values: " + uniqueGoldenPalmCounts);

                break;
            case 4:
                int ctr = 0;
                List<Person> directors = movies.stream()
                        .filter(mov -> mov.getGenre().equals(MovieGenre.valueOf(dgpp)))
                        .map(Movie::getDirector)
                        .toList();
                for (Movie m : movies) {
                    for (Person p : directors) {
                        if (m.getDirector().equals(p)) {
                            m.setOscarsCount(0);
                            movieRepo.save(m);
                            ctr++;
                            break;
                        }
                    }
                }
                model.addAttribute("sop_result", "Oscars was nullified for "+ctr+" movies totally. Their directors was seen in genre :" + dgpp);

                break;
            case 5:
                int cntr = 0;
                for (Movie m : movies) {
                    if (m.getLength() > lpp) {
                        cntr++;
                        m.setOscarsCount(m.getOscarsCount()+1);
                        movieRepo.save(m);
                    }
                }

                model.addAttribute("sop_result", "For length "+lpp+" was updated "+cntr+" movies.");

                break;
        }

        model.addAttribute("movies", movies);

        return "/home/movies";
    }

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


        if (model.getAttribute("sop_result") == null) {
            model.addAttribute("sop_result", "No special operations was done recently.");
        }

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

    if (model.getAttribute("sop_result") == null) {
        model.addAttribute("sop_result", "No special operations was done recently.");
    }

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
