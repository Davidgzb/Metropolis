import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class ApiTesting {

    @Test
    public void checkJsonStructure(){

        // Here we make the GET call to the API and convert the response into a string
        RestAssured.baseURI = "https://api.github.com/repos/metrolab/SingleDateAndTimePicker";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);
        String responseBody = response.getBody().asString();

        // Here we read the .txt file line by line and compare it with the JSON to make sure every key is in the response
        String fileName = "ResponseTextFile.txt";
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){

                // Finding every key in every line and asserting that the response JSON contains every one of them.
                if (line.contains("\":")){
                    int startIndex = line.indexOf('\"');
                    int endIndex = line.indexOf(':', startIndex+1);

                    String keyValue = line.substring(startIndex,endIndex+1);

                    Assert.assertTrue(responseBody.contains(keyValue), "Test Failed: the response does not contain: " + keyValue);
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }

    }


    @Test
    public void checkLogInValue(){

        // Here we make the GET call and convert the Owner array of the response into a Map so that we can assert the value of login
        RestAssured.baseURI = "https://api.github.com/repos/metrolab/SingleDateAndTimePicker";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);

        Map<String,String> loginValue = response.jsonPath().getMap("owner");
        Assert.assertEquals(loginValue.get("login"),"metrolab");


    }

}
