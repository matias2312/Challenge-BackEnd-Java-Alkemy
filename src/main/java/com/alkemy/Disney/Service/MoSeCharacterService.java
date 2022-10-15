package com.alkemy.Disney.Service;

import com.alkemy.Disney.models.MoSeCharacter;

import java.util.List;

public interface MoSeCharacterService {
    public MoSeCharacter saveMoSeCharacter(MoSeCharacter moSeCharacter);

    public List<MoSeCharacter> getMoSeCharacter();
    public void delete(MoSeCharacter moSeCharacter);
    public MoSeCharacter getMoSeCharacterId(Long Id);

}
