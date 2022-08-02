package com.endava.petclinic;

import com.endava.petclinic.contollers.PetController;
import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.endava.petclinic.utils.EnvReader;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PetApiTest {
    @Test
    public void postPet() {
        //create an owner
        //create a petType
        //get an owner from the db by index
        //get a petType by index
        //save all te data in variables

        //GET owner by id
        Owner petOwner = new Owner();
        //TODO: POST a new owner and get its ID
        ValidatableResponse getOwner = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("ownerId", 6)
                // ca sa o mentin cat mai SDS trebuie
                // sa fac post la un un owner now si sa ii iau id-ul lui, pe care sa il pun aici

                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        petOwner.setId(getOwner.extract().jsonPath().getInt("id"));
        petOwner.setTelephone(getOwner.extract().jsonPath().getString("telephone"));
        petOwner.setFirstName(getOwner.extract().jsonPath().getString("firstName"));
        petOwner.setLastName(getOwner.extract().jsonPath().getString("lastName"));
        petOwner.setCity(getOwner.extract().jsonPath().getString("city"));
        petOwner.setAddress(getOwner.extract().jsonPath().getString("address"));

        //GET petType by id
        PetType petType = new PetType();
        ValidatableResponse getPetType = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath()).
                port(EnvReader.getPort())
                .pathParam("petTypeId", 2)
                .when()
                .get("/api/pettypes/{petTypeId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
        petType.setId(getPetType.extract().jsonPath().getInt("id"));
        petType.setName(getPetType.extract().jsonPath().getString("name"));

        //POST new pet
        Pet pet = new PetController().getNewPet(petOwner, petType); // in order to add a new pet we need to have an already
        //defined registered owner and pet type
        ValidatableResponse postPet = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath()).
                port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Pet validatePet = postPet.extract().as(Pet.class);
        assertThat(validatePet, is(pet));

    }

    @Test
    public void getPet() {
        Owner petOwner = new Owner();
        //TODO: POST a new owner and get its ID
        ValidatableResponse getOwner = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("ownerId", 6)
                // ca sa o mentin cat mai SDS trebuie
                // sa fac post la un un owner now si sa ii iau id-ul lui, pe care sa il pun aici

                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        petOwner.setId(getOwner.extract().jsonPath().getInt("id"));
        petOwner.setTelephone(getOwner.extract().jsonPath().getString("telephone"));
        petOwner.setFirstName(getOwner.extract().jsonPath().getString("firstName"));
        petOwner.setLastName(getOwner.extract().jsonPath().getString("lastName"));
        petOwner.setCity(getOwner.extract().jsonPath().getString("city"));
        petOwner.setAddress(getOwner.extract().jsonPath().getString("address"));

        //GET petType by id
        PetType petType = new PetType();
        ValidatableResponse getPetType = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath()).
                port(EnvReader.getPort())
                .pathParam("petTypeId", 2)
                .when()
                .get("/api/pettypes/{petTypeId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
        petType.setId(getPetType.extract().jsonPath().getInt("id"));
        petType.setName(getPetType.extract().jsonPath().getString("name"));

        //POST new pet
        Pet pet = new PetController().getNewPet(petOwner, petType); // in order to add a new pet we need to have an already
        //defined registered owner and pet type
        ValidatableResponse postPet = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath()).
                port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        //GET all pets
        given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath()).
                port(EnvReader.getPort())
                .when()
                .get("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);


    }
}
