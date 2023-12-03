package com.apple.APIManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.testng.FileAssert.fail;

public class Base {


    public static int generateRandomNumber(int min, int max) {

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }


    public static void verifyAttributeValueExactMatchForMultipleObjects(Response response, int randomNumber, String key, String expected)
    {

        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        for (int i = 0; i < randomNumber; i++) {
            response.then()
                    .body("results[" + i + "]."+key, equalTo(expected));
        }

    }

    public static void verifyAttributeValueNotNull(Response response, int randomNumber, String key)
    {

        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        for (int i = 0; i < randomNumber; i++) {
            response.then()
                    .body("results[" + i + "]."+key, notNullValue());
        }

    }

    public static void verifyAttributeValuePartialMatchForMultipleObjects(Response response, int randomNumber, String key, String expected)
    {

        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        for (int i = 0; i < randomNumber; i++) {
            response.then()
                    .body("results[" + i + "]."+key, containsString(expected));  // Replace "expectedValue" with your expected kind value
        }

    }

    public static void verifyAttributeValuePartialMatchForExpectedArrayOfObjects(Response response, int randomNumber, String[] keys, String[] expected) {

        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));

        boolean found = false;
        String trackActualValue="";


        for (int i = 0; i < randomNumber; i++) {
            for (String s : expected) {
                for (String key : keys) {


                    String actualValue = response.jsonPath().getString("results[" + i + "]." + key);
                    System.out.println(key+"-"+actualValue);
                    trackActualValue=actualValue;



                    try {
                        if (actualValue.toLowerCase().contains(s.toLowerCase())) {
                            found = true;
                            break;
                        }
                    }
                    catch (NullPointerException np)
                    {
                        fail("actual value is null, either key may not existed in response or key value is null - " +key );
                    }

                }
            }


            if (!found) {
                fail("None of the expected values found in actual string at index " + i+" and actual value is "+trackActualValue+", expected values are "+ Arrays.toString(expected));
            }


            found = false;
        }
    }

    public static void verifyMatchedAttributeValueNotNull(Response response, int randomNumber,String matchAttribute) {
        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));

        boolean found = false;

        for (int i = 0; i < randomNumber; i++) {

            String[] keys = response.jsonPath().getMap("results[" + i + "]").keySet().toArray(new String[0]);

            for (String key : keys) {
                if (key.toLowerCase().contains(matchAttribute)) {
                    if(response.jsonPath().getString("results[" + i + "]." + key).isEmpty());
                    {
                        fail("key - "+key +" is empty or null");
                        break;
                    }

                }
                else if(matchAttribute.isEmpty())
                {
                    if(response.jsonPath().getString("results[" + i + "]." + key).isEmpty());
                    {
                        fail("key - "+key +" is empty or null");
                        break;
                    }
                }



            }




        }
    }


}
