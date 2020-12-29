package BaseTests;

import org.junit.Before;
import org.junit.Test;


public class BaseTests {

    public static String apiKey = "eed779f8" ;
    public static String searchMovie = "Harry Potter";
    public static String searchMovieTitle = "Harry Potter and the Sorcerer's Stone";

    BaseTestsHelper baseTestsHelper = new BaseTestsHelper();

    @Test
    public void searchHarryPotterMovie(){
        String id = baseTestsHelper.getIdFromMovie(apiKey,searchMovie,searchMovieTitle);
        baseTestsHelper.searchByID(apiKey,id);
    }
}
