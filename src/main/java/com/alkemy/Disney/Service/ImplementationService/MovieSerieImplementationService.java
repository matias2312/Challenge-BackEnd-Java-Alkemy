package com.alkemy.Disney.Service.ImplementationService;

import com.alkemy.Disney.Service.MovieSerieService;
import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.Client;
import com.alkemy.Disney.models.Gender;
import com.alkemy.Disney.models.MoSeCharacter;
import com.alkemy.Disney.models.MovieSerie;
import com.alkemy.Disney.repositories.GenderRepository;
import com.alkemy.Disney.repositories.MovieSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieSerieImplementationService implements MovieSerieService {
    @Autowired
    private MovieSerieRepository movieSerieRepository;
    @Override
    public MovieSerie saveMovieSerie(MovieSerie movieSerie){
       return   movieSerieRepository.save(movieSerie);
    }
    @Override
    public List<MovieSerie> getMovieSerie() {
        return movieSerieRepository.findAll();
    }
    @Override
    public MovieSerie getMovieSerieById(Long Id){
        return movieSerieRepository.findById(Id).get();
    }
    @Override
    public void deleteMovieSerie(MovieSerie movieSerie){
         movieSerieRepository.delete(movieSerie);
    }
    @Override
    public List<MovieSerie> findAllByName(String title){

        return movieSerieRepository.findAllByTitle(title);
    }
}
