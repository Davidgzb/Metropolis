import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ApiTesting {

    @Test
    public void checkJsonStructure(){

        RestAssured.baseURI = "https://api.github.com/repos/metrolab/SingleDateAndTimePicker";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);

        String responseBody = response.getBody().asString();
        System.out.println("Response body is => " + responseBody);

        String fileName = "ResponseTextFile.txt";
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){
                //System.out.println(line);
                if (line.contains("\":")){
                    System.out.println(line);
                    int startIndex = line.indexOf('\"');
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }

    }

}
