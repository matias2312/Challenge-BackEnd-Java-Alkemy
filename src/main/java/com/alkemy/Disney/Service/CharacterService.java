package com.alkemy.Disney.Service;


import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.MoSeCharacter;

import java.util.List;
import java.util.Set;

public interface CharacterService {
   public void saveCharacter(Character character);
   public List<Character> getCharacter();
   public Character getCharacterId(Long Id);
   public void deleteCharacter(Character character);
   public List<Character> getByName(String name);
   public List<Character> getByAge(int age);
   public List<Character> getByWeight(double weight);

}
