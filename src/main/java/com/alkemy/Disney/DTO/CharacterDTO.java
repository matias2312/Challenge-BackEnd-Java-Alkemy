package com.alkemy.Disney.DTO;

import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.MoSeCharacter;
import com.alkemy.Disney.models.MovieSerie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CharacterDTO {
    private String name;
    private int age;
    private double weight;
    private String history;
    private String image;
    private List<String> movieSeries;

    public CharacterDTO(Character character) {
        this.name = character.getName();
        this.age = character.getAge();
        this.weight = character.getWeight();
        this.history = character.getHistory();
        this.image = character.getImage();
        this.movieSeries = character.getMoSeCharacters().stream().map(moSeCharacter -> moSeCharacter.getMovieSerie()).map(movieSerie -> movieSerie.getTitle()).collect(Collectors.toList());
    }
}
