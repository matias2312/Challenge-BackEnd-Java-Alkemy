package com.alkemy.Disney.DTO;

import com.alkemy.Disney.models.MovieSerie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class MoviesDTO {
    private String image;
    private String title;
    private LocalDate creationDate;

    public MoviesDTO(MovieSerie movieSerie) {
        this.image = movieSerie.getImage();
        this.title = movieSerie.getTitle();
        this.creationDate = movieSerie.getCreationDate();
    }
}
