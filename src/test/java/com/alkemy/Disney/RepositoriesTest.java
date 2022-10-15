package com.alkemy.Disney;

import com.alkemy.Disney.models.*;
import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.repositories.CharacterRepository;
import com.alkemy.Disney.repositories.ClientRepository;
import com.alkemy.Disney.repositories.GenderRepository;
import com.alkemy.Disney.repositories.MovieSerieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    GenderRepository genderRepository;
    @Autowired
    MovieSerieRepository movieSerieRepository;

    @Test
    public void existCharacters(){
        List<Character> characters = characterRepository.findAll();
        assertThat(characters,is(not(empty())));
    }

   @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
   }
    @Test
    public void clientName(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,hasItem(hasProperty("firstName",is("Melba"))));
        assertThat(clients,hasItem(hasProperty("firstName",is("admin"))));
    }
    @Test
    public void existGenders(){
        List<Gender> genders = genderRepository.findAll();
        assertThat(genders,is(not(empty())));
    }
    @Test
    public void genderName(){
        List<Gender> genders = genderRepository.findAll();
        assertThat(genders, hasItem(hasProperty("name",is("Action"))));
        assertThat(genders, hasItem(hasProperty("name",is("Comedy"))));
        assertThat(genders, hasItem(hasProperty("name",is("Drama"))));
    }
    @Test
    public void existMoviesSeries(){
        List<MovieSerie> movieSeries = movieSerieRepository.findAll();
        assertThat(movieSeries,is(not(empty())));
    }
    @Test
    public void moviesSeriesType(){
        List<MovieSerie> movieSeries = movieSerieRepository.findAll();
        assertThat(movieSeries,hasItem(hasProperty("typeMovieSerie",is(TypeMovieSerie.MOVIE))));
        assertThat(movieSeries,hasItem(hasProperty("typeMovieSerie",is(TypeMovieSerie.SERIE))));
    }

}
