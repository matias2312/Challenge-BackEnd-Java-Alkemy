package com.alkemy.Disney.DTO;

import com.alkemy.Disney.models.Gender;
import com.alkemy.Disney.models.MovieSerie;
import com.alkemy.Disney.models.TypeMovieSerie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class MovieSerieDTO {
    private long id;
    private String image;
    private String title;
    private LocalDate creationDate;
    private Integer qualification;
    private List<String> characters;
    private TypeMovieSerie typeMovieSerie;
    public MovieSerieDTO(MovieSerie movieSerie) {
        this.id = movieSerie.getId();
        this.image = movieSerie.getImage();
        this.title = movieSerie.getTitle();
        this.creationDate = movieSerie.getCreationDate();
        this.qualification = movieSerie.getQualification();
        this.characters= movieSerie.getMoSeCharacters().stream().map(moSeCharacter -> moSeCharacter.getCharacter().getName()).collect(Collectors.toList());
        this.typeMovieSerie = movieSerie.getTypeMovieSerie();
    }
}
