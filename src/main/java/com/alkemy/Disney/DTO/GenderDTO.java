package com.alkemy.Disney.DTO;

import com.alkemy.Disney.models.Gender;
import com.alkemy.Disney.models.MovieSerie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GenderDTO {
    private long id;
    private String name;
    private String image;
   // private MovieSerie moviesSeries;
    public GenderDTO(Gender gender) {
        this.id = gender.getId();
        this.name = gender.getName();
        this.image = gender.getImage();
        //this.moviesSeries = gender.getMovieSerie();
    }
}
