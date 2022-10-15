package com.alkemy.Disney.repositories;

import com.alkemy.Disney.models.Character;
import com.alkemy.Disney.models.MoSeCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface CharacterRepository  extends JpaRepository<Character,Long> {
    public List<Character> findAllByName(String name);
    public List<Character> findAllByAge(int age);
    public List<Character> findAllByWeight(double weight);

}
