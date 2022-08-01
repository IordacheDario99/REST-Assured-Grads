package com.endava.petclinic;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetClinic {
    @Test
    public void getOwnerById() { //testele de JUNIT nu primesc parametrii in mod normal
        //DSL - domain specific language
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId", 1)
                .when() // nu este obligatoriu (Given - When - Then) .log().all()
                .get("/api/owners/{ownerId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(1))
                .body("firstName", containsString("org"))
                .body("lastName", startsWith("Fr"))
                .body("city", equalToIgnoringCase("MadisOn"))
                .body("telephone", hasLength(10));//trebuie sa specificam locatia, ownerId este path parameters
    }

    @Test
    public void postOwnersTest() {
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"address\": \"Tineretului, nr1\",\n" +
                        "  \"city\": \"Constanta\",\n" +
                        "  \"firstName\": \"Dario\",\n" +
                        "  \"id\": null,\n" +
                        "  \"lastName\": \"Iordache\",\n" +
                        "  \"telephone\": \"1234567890\"\n" +
                        "}")
                .when().log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer ownerId = response.extract().jsonPath().getInt("id");
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId", ownerId)
                .when()
                .get("/api/owners/{ownerId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(ownerId));
    }

    @Test
    public void getPetsTest() {
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("petclinic")
                .port(80)
                .when()
                .get("/api/pets")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);


    }

    @Test
    public void getPetsByIdTest() {
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("petclinic")
                .port(80)
                .pathParam("petId", 52)
                .when()
                .get("/api/pets/{petId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void putPets() {

        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"birthDate\": \"2022/08/01\",\n" +
                        "  \"id\": null,\n" +
                        "  \"name\": \"Doge\",\n" +
                        "  \"owner\": {\n" +
                        "    \"address\": \"Doge street\",\n" +
                        "    \"city\": \"Doge City\",\n" +
                        "    \"firstName\": \"The Doge Owner\",\n" +
                        "    \"id\": 69,\n" +
                        "    \"lastName\": \"Yes\",\n" +
                        "    \"pets\": [\n" +
                        "      null\n" +
                        "    ],\n" +
                        "    \"telephone\": \"string\"\n" +
                        "  },\n" +
                        "  \"type\": {\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"Hamster\"\n" +
                        "  },\n" +
                        "  \"visits\": [\n" +
                        "    {\n" +
                        "      \"date\": \"yyyy/MM/dd\",\n" +
                        "      \"description\": \"string\",\n" +
                        "      \"id\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .when()
                .post("/api/pets").prettyPeek()
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void postVisitTest() {
        given().baseUri("http://api.petclinic.mywire.org/").basePath("petclinic").port(80)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"id\": null,\n" +
                        "    \"date\": \"2022/08/01\",\n" +
                        "    \"description\": \"Crypto check\",\n" +
                        "    \"pet\": {\n" +
                        "      \"id\": 52,\n" +
                        "  \"name\": \"Doge\",\n" +
                        "  \"birthDate\": \"2022/08/01\",\n" +
                        "  \"type\": {\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"hamster\"\n" +
                        "  },\n" +
                        "  \"owner\": {\n" +
                        "    \"id\": 69,\n" +
                        "    \"firstName\": \"Dario\",\n" +
                        "    \"lastName\": \"Iordache\",\n" +
                        "    \"address\": \"Tineretului, nr1\",\n" +
                        "    \"city\": \"Constanta\",\n" +
                        "    \"telephone\": \"1234567890\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }")
                .when().post("/api/visits").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

}
