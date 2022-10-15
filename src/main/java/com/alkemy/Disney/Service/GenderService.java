package com.alkemy.Disney.Service;

import com.alkemy.Disney.models.Gender;

import java.util.List;

public interface GenderService {
    public Gender saveGender(Gender gender);
    public List<Gender> getGenders();
    public Gender getGenderId(Long Id);
}
