package com.alkemy.Disney.controllers;

import com.alkemy.Disney.DTO.CharacterDTO;
import com.alkemy.Disney.DTO.CharactersDTO;
import com.alkemy.Disney.Service.CharacterService;
import com.alkemy.Disney.Service.ClientService;
import com.alkemy.Disney.Service.MoSeCharacterService;
import com.alkemy.Disney.Service.MovieSerieService;
import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.Client;
import com.alkemy.Disney.models.MoSeCharacter;
import com.alkemy.Disney.models.MovieSerie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CharacterController {

    public final ClientService clientService;
    public final CharacterService characterService;
    public final MoSeCharacterService moSeCharacterService;
    public final MovieSerieService movieSerieService;

    @GetMapping("/characters")
    public Set<CharactersDTO> getCharacter(@RequestParam(required = false) String name,@RequestParam(required = false) Integer age,@RequestParam(required = false) Double weight,@RequestParam(required = false) Long movieSerieId) {

        if(name != null){
            return characterService.getByName(name).stream().map(character -> new CharactersDTO(character)).collect(Collectors.toSet());
        }
        if(age != null){
            return  characterService.getByAge(age).stream().map(character -> new CharactersDTO(character)).collect(Collectors.toSet());
        }
        if(weight != null){
            return  characterService.getByWeight(weight).stream().map(character -> new CharactersDTO(character)).collect(Collectors.toSet());
        }
        if(movieSerieId != null){
            return movieSerieService.getMovieSerieById(movieSerieId).getMoSeCharacters().stream().map(moSeCharacter -> new CharactersDTO(moSeCharacter.getCharacter())).collect(Collectors.toSet());
        }
        return characterService.getCharacter().stream().map(character -> new CharactersDTO(character)).collect(Collectors.toSet());

    }
    @GetMapping("/character/detail")
    public Set<CharacterDTO> getCharacterDetail(){
        return characterService.getCharacter().stream().map(character -> new CharacterDTO(character)).collect(Collectors.toSet());
    }

    @PostMapping("/character/created")
    public ResponseEntity<Object> created(Authentication authentication, @RequestBody CharacterDTO characterDTO,@RequestParam Long movieSerieId) {
        Client admin = clientService.findUserByEmail(authentication.getName());
        MovieSerie movieSerie = movieSerieService.getMovieSerieById(movieSerieId);

        if (admin == null) {
            return new ResponseEntity<>("Admin does not exist", HttpStatus.FORBIDDEN);
        }
        if (characterDTO.getAge() <= 0) {
            return new ResponseEntity<>("invalid age", HttpStatus.FORBIDDEN);
        }
        if (characterDTO.getWeight() <= 0) {
            return new ResponseEntity<>("invalid weight", HttpStatus.FORBIDDEN);
        }
        if (characterDTO.getName().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (characterDTO.getImage().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (characterDTO.getHistory().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Character newCharacter = new Character(characterDTO.getName(), characterDTO.getAge(), characterDTO.getWeight(), characterDTO.getHistory(), characterDTO.getImage());
        characterService.saveCharacter(newCharacter);
        MoSeCharacter moSeCharacter1 = new MoSeCharacter(newCharacter,movieSerie);
        moSeCharacterService.saveMoSeCharacter(moSeCharacter1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/character/edit")
    public ResponseEntity<Object> edit(Authentication authentication, @RequestParam Long characterId, @RequestParam String name, @RequestParam String history,
                                       @RequestParam String image) {
        Client client = clientService.findUserByEmail(authentication.getName());
        Character character = characterService.getCharacterId(characterId);

        if (client == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if (name.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (history.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        character.setName(name);
        character.setHistory(history);
        character.setImage(image);
        characterService.saveCharacter(character);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/character/delete")
    public ResponseEntity<Object> deleteCharacter(@RequestParam Long characterId, Authentication authentication){
        Client admin= clientService.findUserByEmail(authentication.getName());
        Character character = characterService.getCharacterId(characterId);
        
        if(admin == null){
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if(character == null){
            return new ResponseEntity<>("Character does not exist", HttpStatus.FORBIDDEN);
        }
        characterService.deleteCharacter(character);
        return new ResponseEntity<>("Character Deleted",HttpStatus.OK);
    }
}
