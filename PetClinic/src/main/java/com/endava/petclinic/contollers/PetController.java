package com.endava.petclinic.contollers;

import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class PetController {
    public Pet getNewPet(Owner owner, PetType petType) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        Faker faker = new Faker();

        //String birthDate = dateFormatter.format((TemporalAccessor) faker.date().birthday());

        Pet pet = new Pet(faker.funnyName().name(),
                dateFormatter.format(faker.date().birthday()),
                owner,
                petType
        );
        return pet;
    }

    public static  Pet getNewPetStatic(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        Faker faker = new Faker();
        Owner owner = new Owner();
        PetType petType = new PetType();

        // dupa care trebuie sa cream un obiect de tip owner si petType
        //String birthDate = dateFormatter.format((TemporalAccessor) faker.date().birthday());

        Pet pet = new Pet(faker.funnyName().name(),
                dateFormatter.format(faker.date().birthday()),
                owner,
                petType
        );
        return pet;

    }

}
