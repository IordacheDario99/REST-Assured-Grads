package com.endava.petclinic;

import com.endava.petclinic.contollers.OwnerController;
import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.User;
import com.endava.petclinic.utils.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PetClinicDay2Test {

    @Test
    public void postOwnerTest() {
        HashMap<String, String> owner = new HashMap<>();
        owner.put("id", null);
        owner.put("firstName", "Dario");
        owner.put("lastName", "Iordache");
        owner.put("address", " Strada Pietroasa");
        owner.put("city", "Bucuresti");
        owner.put("telephone", "1234567890");

        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .port(80)
                .body(owner)
                .when()
                .post("api/owners")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer id = response.extract().jsonPath().getInt("id");
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .port(80)
                .pathParam("ownerId", id)
                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(id)).log().all();


    }

    @Test
    public void postOwnerTestWithObject() {

        Owner owner = OwnerController.getNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(owner)
                .port(80)
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse = given().given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class);
        assertThat(ownerFromGetResponse, is(owner));
    }

    @Test
    public void putOwnerTest() {
        Owner owner = OwnerController.getNewRandomOwner();
        Faker faker = new Faker();
        ValidatableResponse response = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(owner)
                .when().log().all()
                .post("/api/owners")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));
        owner.setAddress(faker.address().streetAddress());
        owner.setCity(faker.address().city());
        owner.setTelephone(faker.number().digits(10));

        //PUT
        given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(owner)
                .when().log().all()
                .pathParam("ownerId", owner.getId())
                .put("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        //GET
        ValidatableResponse getResponse = given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("ownerId", owner.getId())
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner actualOwner = getResponse.extract().as(Owner.class);
        assertThat(actualOwner, is(owner));
    }

    @Test
    public void postOwnerTestWithObjectWithAuth() {

        Owner owner = OwnerController.getNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(owner).log().all()
                .port(EnvReader.getSecurePort())
                .auth().preemptive()
                .basic("admin","admin")// pentru user si parola, preemptive trimite direct credentialele
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse = given().given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .pathParam("ownerId", owner.getId())
                .port(EnvReader.getSecurePort())
                .auth().preemptive()
                .basic("admin", "admin")
                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class);
        assertThat(ownerFromGetResponse, is(owner));
    }

    @Test
    public void createOwnerSecuredTest(){

        Faker faker = new Faker();
        User user = new User(faker.name().username(),faker.internet().password(),"OWNER_ADMIN", "VET_ADMIN");

        given().baseUri(EnvReader.getBaseUri()).basePath(EnvReader.getBasePath())
                .port(EnvReader.getSecurePort())
                .auth().preemptive()
                .basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(user).log().all()
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED);



        Owner owner = OwnerController.getNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(owner).log().all()
                .port(EnvReader.getSecurePort())
                .auth().preemptive()
                .basic(user.getUsername(),user.getPassword())// pentru user si parola, preemptive trimite direct credentialele
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse = given().given().baseUri(EnvReader.getBaseUri())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .pathParam("ownerId", owner.getId())
                .port(EnvReader.getSecurePort())
                .auth().preemptive()
                .basic(user.getUsername(),user.getPassword())
                .when()
                .get("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class);
        assertThat(ownerFromGetResponse, is(owner));

    }

}
