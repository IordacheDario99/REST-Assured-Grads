package com.endava.petclinic.contollers;

import com.endava.petclinic.models.Owner;
import com.github.javafaker.Faker;

public class OwnerController {
    public static Owner getNewRandomOwner(){
        Faker faker = new Faker();
        Owner owner = new Owner(faker.name().firstName(),   //sau putem folosi constructorul default si apoi sa apelam setters and getters
                faker.name().lastName(),
                faker.address().streetAddress(),
                faker.address().city(),
                faker.number().digits(10));
        return owner;
    }
}
