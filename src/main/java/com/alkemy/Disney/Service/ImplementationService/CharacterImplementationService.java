package com.alkemy.Disney.Service.ImplementationService;

import com.alkemy.Disney.Service.CharacterService;
import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.Client;
import com.alkemy.Disney.models.MoSeCharacter;
import com.alkemy.Disney.models.MovieSerie;
import com.alkemy.Disney.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CharacterImplementationService implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public void saveCharacter(Character character){
        characterRepository.save(character);
    }

    @Override
    public List<Character> getCharacter() {
        return characterRepository.findAll();
    }
    @Override
    public Character getCharacterId(Long Id){
        return characterRepository.findById(Id).get();
    }
    @Override
    public List<Character> getByName(String name){
        return characterRepository.findAllByName(name);
    }
    @Override
    public List<Character> getByAge(int age){
        return characterRepository.findAllByAge(age);
    }
     @Override
     public List<Character> getByWeight(double weight){
        return characterRepository.findAllByWeight(weight);
     }
    @Override
    public void deleteCharacter(Character character){
         characterRepository.delete(character);
    }

}
