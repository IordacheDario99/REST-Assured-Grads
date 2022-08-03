package com.endava.petclinic;

import com.endava.petclinic.utils.EnvReader;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TweeterBotTest {
    @Test
    public void postTweetOnWall() {

        given().baseUri("https://twitter.com").basePath("/j3r3my84")
                .auth().oauth(EnvReader.getApiKey(), EnvReader.getApiKeySecret(),
                        EnvReader.getAccessToken(), EnvReader.getAccessTokenSecret())
                .queryParam("status", "REEE")
                .post("https://api.twitter.com/1.1/statuses/update.json").prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    public void getAllTweetsFromWall() {

        given().baseUri("https://twitter.com").basePath("/j3r3my84")
                .auth().oauth(EnvReader.getApiKey(), EnvReader.getApiKeySecret(),
                        EnvReader.getAccessToken(), EnvReader.getAccessTokenSecret())
                .queryParam("status", EnvReader.getTwBanner())
                .post("https://api.twitter.com/1.1/statuses/update.json").prettyPeek()
                .then()
                .statusCode(200);

        given().baseUri("https://twitter.com").basePath("/j3r3my84")
                .auth().oauth(EnvReader.getApiKey(), EnvReader.getApiKeySecret(),
                        EnvReader.getAccessToken(), EnvReader.getAccessTokenSecret())
                .when()
                .get("https://api.twitter.com/1.1/statuses/user_timeline.json")
                .then().log().all()
                .statusCode(200);
    }
}
