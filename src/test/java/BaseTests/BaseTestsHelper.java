package BaseTests;

import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static io.restassured.RestAssured.given;


public class BaseTestsHelper {

    public String getIdFromMovie(String apiKey, String searchMovie, String searchMovieTitle) {
        RestAssured.baseURI = "http://www.omdbapi.com/";
        String id = null;
        try {
             Response response = getResponseFromEndPoint(apiKey,searchMovie);

            assert response != null;
            JsonPath path = response.jsonPath();
            response.getBody().prettyPrint();

            List<MovieCheckAndControl> arrList = path.getList("Search", MovieCheckAndControl.class);

            for (MovieCheckAndControl singleObject : arrList) {
                if (singleObject.getTitle().equals(searchMovieTitle)) {
                    id = singleObject.getImdbID();
                    System.out.println("\n For '"+ searchMovieTitle +"' Movie, ");
                    System.out.println("\n IMDB ID: " + id);
                    System.out.println("\n");
                    break;
                }
            }
            return id;
        }catch (Exception e){
            System.out.println("HATA! " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void searchByID(String apiKey, String id) {
        try {
            given()
                    .param("apikey", apiKey)
                    .param("i",id)
                    .when()
                       .get()
                    .then()
                       .log()
                       .all()
                       .statusCode(200)
                    .body("Title", not(emptyOrNullString()))
                    .body("Year", not(emptyOrNullString()))
                    .body("Released", not(emptyOrNullString()));
        }catch (Exception e){
            System.out.println("HATA! "+e.getMessage());
        }
    }

    private Response getResponseFromEndPoint(String apiKey, String searchMovie) {
        try {
            return given()
                    .param("apikey", apiKey)
                    .param("s", searchMovie)
                    .when()
                    .get()
                    .then()
                    .log()
                    .all()
                    .contentType(ContentType.JSON)
                    .statusCode(200)
                    .and()
                    .body("Search.Title",not(emptyOrNullString()))
                    .body("Search.Year",not(emptyOrNullString()))
                    .extract()
                    .response();
        }catch (Exception e){
            System.out.println("HATA! "+e.getMessage());
            return null;
        }
    }
}
