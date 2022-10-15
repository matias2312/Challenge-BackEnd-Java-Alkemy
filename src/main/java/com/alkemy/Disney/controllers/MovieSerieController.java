package com.alkemy.Disney.controllers;

import com.alkemy.Disney.DTO.MovieSerieDTO;
import com.alkemy.Disney.DTO.MoviesDTO;
import com.alkemy.Disney.Service.*;
import com.alkemy.Disney.configurations.WebAuthentication;
import com.alkemy.Disney.configurations.WebAuthorization;
import com.alkemy.Disney.models.*;
import com.alkemy.Disney.models.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieSerieController {

    public final ClientService clientService;
    public final MoSeCharacterService moSeCharacterService;
    public final MovieSerieService movieSerieService;
    public final GenderService genderService;
    public final CharacterService characterService;
    public  final WebAuthentication webAuthentication;
    public  final WebAuthorization webAuthorization;




    @GetMapping("/movies")
    public List<MoviesDTO> getMovies(@RequestParam (required = false) String title,@RequestParam (required = false) Long genderId, @RequestParam (required = false) String order){
        List<MovieSerie>  movies = movieSerieService.getMovieSerie().stream().filter(movieSerie -> movieSerie.getTypeMovieSerie().equals(TypeMovieSerie.MOVIE)).collect(Collectors.toList());

        if(title != null){
            return movieSerieService.findAllByName(title).stream().map(movieSerie -> new MoviesDTO(movieSerie)).collect(Collectors.toList());
        }
        if(genderId != null){
            return genderService.getGenderId(genderId).getMovieSerie().stream().map(movieSerie -> new MoviesDTO(movieSerie)).collect(Collectors.toList());
        }
        if(order != null){
            if(order.equals("ASC")) {
                List<MoviesDTO> movieSerieList = movieSerieService.getMovieSerie().stream().map(movieSerie -> new MoviesDTO(movieSerie)).collect(Collectors.toList());
                movieSerieList.sort(Comparator.comparing(MoviesDTO::getCreationDate));
                return movieSerieList;
            }
            if(order.equals("DESC")) {
                List<MoviesDTO> movieSerieList = movieSerieService.getMovieSerie().stream().map(movieSerie -> new MoviesDTO(movieSerie)).collect(Collectors.toList());
                movieSerieList.sort(Comparator.comparing(MoviesDTO::getCreationDate).reversed());
                return movieSerieList;
            }
        }
        return movies.stream().map(movieSerie -> new MoviesDTO(movieSerie)).collect(Collectors.toList());
    }
    @GetMapping("/movieSerie/detail")
    public Set<MovieSerieDTO> getMovieSerie(){
        return movieSerieService.getMovieSerie().stream().map(movieSerie -> new MovieSerieDTO(movieSerie)).collect(Collectors.toSet());
    }

    @PostMapping("/movieSerie/created")
    public ResponseEntity<Object> created(Authentication authentication, @RequestParam Long characterId, @RequestParam Long genderId, @RequestBody MovieSerieDTO movieSerieDTO){
        Client Admin = clientService.findUserByEmail(authentication.getName());
        Gender gender = genderService.getGenderId(genderId);
        Character character = characterService.getCharacterId(characterId);

        if (Admin == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if (gender == null) {
            return new ResponseEntity<>("Gender does not exist", HttpStatus.FORBIDDEN);
        }
        if (character == null) {
            return new ResponseEntity<>("Gender does not exist", HttpStatus.FORBIDDEN);
        }
        if(movieSerieDTO.getImage().isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(movieSerieDTO.getTypeMovieSerie() == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(movieSerieDTO.getCreationDate() == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(movieSerieDTO.getTitle().isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
       if(movieSerieDTO.getQualification() <= 0 || movieSerieDTO.getQualification() > 5){
           return new ResponseEntity<>("Invalid qualification", HttpStatus.FORBIDDEN);
       }
        if (movieSerieDTO.getQualification() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        MovieSerie movieSerie = new MovieSerie(movieSerieDTO.getImage(),movieSerieDTO.getTitle(),movieSerieDTO.getCreationDate(),movieSerieDTO.getQualification(),gender,movieSerieDTO.getTypeMovieSerie());
        movieSerieService.saveMovieSerie(movieSerie);
        MoSeCharacter newMoSeCharacter = new MoSeCharacter(character,movieSerie);
        moSeCharacterService.saveMoSeCharacter(newMoSeCharacter);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/movieSerie/edit")
    public ResponseEntity<Object> edit(Authentication authentication,@RequestBody MovieSerieDTO movieSerieDTO, @RequestParam Long movieSerieId) {
        Client admin = clientService.findUserByEmail(authentication.getName());
        MovieSerie movieSerie = movieSerieService.getMovieSerieById(movieSerieId);

        if (admin == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if (movieSerieDTO.getImage().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (movieSerieDTO.getTitle().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(movieSerieDTO.getQualification() <= 0 || movieSerieDTO.getQualification() > 5){
            return new ResponseEntity<>("Invalid qualification", HttpStatus.FORBIDDEN);
        }
        if (movieSerieDTO.getQualification() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (movieSerieDTO.getCreationDate() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        movieSerie.setImage(movieSerieDTO.getImage());
        movieSerie.setTitle(movieSerieDTO.getTitle());
        movieSerie.setCreationDate(movieSerieDTO.getCreationDate());
        movieSerie.setQualification(movieSerieDTO.getQualification());
        movieSerieService.saveMovieSerie(movieSerie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/movieSerie/delete")
    public ResponseEntity<Object> deleteMovieSerie(@RequestParam Long movieSerieId, Authentication authentication){
        Client admin= clientService.findUserByEmail(authentication.getName());
        MovieSerie movieSerie = movieSerieService.getMovieSerieById(movieSerieId);

        if(admin == null){
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if(movieSerie == null){
            return new ResponseEntity<>("Movie/Serie does not exist", HttpStatus.FORBIDDEN);
        }
        movieSerieService.deleteMovieSerie(movieSerie);
        return new ResponseEntity<>("Movie/Serie Deleted",HttpStatus.OK);
    }


}
