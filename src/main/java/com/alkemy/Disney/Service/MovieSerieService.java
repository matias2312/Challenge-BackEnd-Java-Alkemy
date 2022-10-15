package com.alkemy.Disney.Service;

import com.alkemy.Disney.models.MovieSerie;

import java.util.List;

public interface MovieSerieService {
    public MovieSerie saveMovieSerie(MovieSerie movieSerie);
    public List<MovieSerie> getMovieSerie();
    public MovieSerie getMovieSerieById(Long Id);
    public void deleteMovieSerie(MovieSerie movieSerie);
    public List<MovieSerie> findAllByName(String title);
}
