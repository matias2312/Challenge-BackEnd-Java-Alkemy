package com.alkemy.Disney;

import com.alkemy.Disney.Service.*;
import com.alkemy.Disney.models.*;
import com.alkemy.Disney.models.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DisneyApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {

		SpringApplication.run(DisneyApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientService clientService, CharacterService characterService, MovieSerieService movieSerieService, GenderService genderService, MoSeCharacterService moSeCharacterService){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@alkemy.com", passwordEncoder.encode("123"));
			Client admin = new Client("admin","admin","admin@admindisney.com",passwordEncoder.encode("1234"));
			clientService.saveClient(client1);
			clientService.saveClient(admin);

			Character LukeSkywalker = new Character("Luke Skywalker",35,80,"Luke Skywalker fue un legendario héroe de guerra y Jedi que fundó la Nueva Orden Jedi.","https://static.wikia.nocookie.net/esstarwars/images/d/d9/Luke-rotjpromo.jpg/revision/latest/scale-to-width-down/350?cb=20071214134433");
			Character Yoda = new Character("Yoda",80,50,"La especie a la que pertenecía el legendario Gran Maestro Jedi Yoda era antigua y rodeada de misterio","https://static.wikia.nocookie.net/esstarwars/images/d/d5/MP-YodaSpecies.png/revision/latest/scale-to-width-down/350?cb=20180802191916");
			Character Chewbacca = new Character("Chewbacca",40,120,"fue un guerrero wookiee, un contrabandista y un luchador de resistencia que peleó en las Guerras Clon, la Guerra Civil Galáctica y el Guerra Fría.","https://static.wikia.nocookie.net/esstarwars/images/5/51/Chewbacca_TLJ.PNG/revision/latest?cb=20190419143715");
			Character MatthewFox = new Character("Matthew Fox",40,80,"Su primer papel protagonista fue el de Charlie Salinger en Party of Five en los años 1990, junto a Scott Wolf, Lacey Chabert y Neve Campbell; pero saltó a la fama por interpretar al doctor Jack Shephard en la serie de ABC Lost.","https://m.media-amazon.com/images/M/MV5BMTcyMjA4OTQwMF5BMl5BanBnXkFtZTcwNTQwMzgyMQ@@._V1_UY264_CR6,0,178,264_AL_.jpg");
			characterService.saveCharacter(LukeSkywalker);
			characterService.saveCharacter(Yoda);
			characterService.saveCharacter(Chewbacca);
			characterService.saveCharacter(MatthewFox);

			Gender action = new Gender("Action","https://i.blogs.es/14a784/forceawakens/1366_2000.jpg");
			Gender comedy = new Gender("Comedy","https://i.blogs.es/14a784/forceawakens/1366_2000.jpg");
			Gender drama = new Gender("Drama","https://i.blogs.es/14a784/forceawakens/1366_2000.jpg");
			genderService.saveGender(action);
			genderService.saveGender(comedy);
			genderService.saveGender(drama);

			MovieSerie MovieTheForceAwakens = new MovieSerie("https://i.blogs.es/14a784/forceawakens/1366_2000.jpg","The Force Awakens ", LocalDate.now().plusYears(-5), 5,action,TypeMovieSerie.MOVIE);
			MovieSerie SerieTheMandalorian = new MovieSerie("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR7GQ6ezssbwUU53xMM6irHjU_kCIPCVFoYnvLSKhaIKNLsvEqM","The Mandalorian", LocalDate.now().plusYears(-3), 4,action,TypeMovieSerie.SERIE);
			MovieSerie SerieLost = new MovieSerie("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSpr_z0Vis7qjeCLzoiYNkhMoCrLW_M54Z3Mu7mBxlCyxk0ZEhf","Lost", LocalDate.now().plusYears(-2), 3,action,TypeMovieSerie.SERIE);
			movieSerieService.saveMovieSerie(MovieTheForceAwakens);
			movieSerieService.saveMovieSerie(SerieTheMandalorian);
			movieSerieService.saveMovieSerie(SerieLost);



			MoSeCharacter moSeCharacter1 = new MoSeCharacter(LukeSkywalker,MovieTheForceAwakens);
			MoSeCharacter moSeCharacter2 = new MoSeCharacter(LukeSkywalker,SerieTheMandalorian);
			MoSeCharacter moSeCharacter3 = new MoSeCharacter(Yoda,MovieTheForceAwakens);
			MoSeCharacter moSeCharacter4 = new MoSeCharacter(Yoda,SerieTheMandalorian);
			moSeCharacterService.saveMoSeCharacter( moSeCharacter1);
			moSeCharacterService.saveMoSeCharacter( moSeCharacter2);
			moSeCharacterService.saveMoSeCharacter( moSeCharacter3);
			moSeCharacterService.saveMoSeCharacter( moSeCharacter4);
		};
	}
}
