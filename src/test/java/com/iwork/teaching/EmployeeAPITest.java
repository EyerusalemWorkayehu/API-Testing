package com.iwork.teaching;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class EmployeeAPITest {

    // Gson boilerplate to json serialization and deserialization
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gson = builder.create();

    // Root URL for the API
    static final String ROOT_URL = "https://iwork-employee-api.up.railway.app/employees/";

    // Generated random user
    private static Employee temp = null;

    @BeforeTest
    public static void setUp() {
        // We're setting up the temporary user
        temp = Employee.generateFake();
    }

    @Test
    public void createEmployeeTest() {
        // Build the post request to create employee
        Response response = given()
                // set content type to json
                .header("Content-type", "application/json")
                .and()
                // pass the generated employee
                .body(gson.toJson(temp))
                // send the request and extract response data
                .when().post(ROOT_URL).then().extract().response();

        // Check the content created status code
        assertEquals(201, response.getStatusCode());

        // construct the employee in the response
        Employee received = new Employee(
                response.path("first_name"),
                response.path("last_name"),
                response.path("email"),
                Gender.fromString(response.path("gender"))
        );

        // Assert employee is equal to expected
        assertEmployee(temp, received);
        // Assert employee id is equal to expected
        temp.setId(response.path("id"));

        System.out.println("Created Employee: " + temp);
    }

    @Test(dependsOnMethods = { "createEmployeeTest" })
    public void getEmployeeTest() {
        // Build the post request to fetch an employee
        Response response = given().log().all()
                // send the request and extract response data
                .when().get(ROOT_URL+ temp.getId()).then().extract().response();

        // Check success status code
        assertEquals(200, response.getStatusCode());

        // construct the employee in the response
        Employee received = new Employee(
                response.path("first_name"),
                response.path("last_name"),
                response.path("email"),
                Gender.fromString(response.path("gender"))
        );

        assertEmployee(temp, received);
    }

    @Test(dependsOnMethods = { "getEmployeeTest" })
    public void editEmployeeTest() {
        Map<String, String> tempObject = new HashMap<>();
        tempObject.put("first_name", "" + temp.getId() + temp.getFirst_name());
        // Build the post request to create employee
        Response response = given()
                // set content type to json
                .header("Content-type", "application/json")
                .and()
                // changing attributes as an object
                .body(gson.toJson(tempObject))
                // send the request and extract response data
                .when().patch(ROOT_URL + temp.getId()).then().extract().response();

        // Check the content created status code
        assertEquals(200, response.getStatusCode());

        // construct the employee in the response
        Employee received = new Employee(
                response.path("first_name"),
                response.path("last_name"),
                response.path("email"),
                Gender.fromString(response.path("gender"))
        );

        // correct for what is changed
        temp.setFirst_name("" + temp.getId() + temp.getFirst_name() );

        // Assert employee is equal to expected
        assertEmployee(temp, received);
    }

    @Test(dependsOnMethods = { "editEmployeeTest" })
    public void deleteEmployeeTest() {
        // Build the post request to fetch an employee
        Response response = given()
                // send the request and extract response data
                .when().delete(ROOT_URL + temp.getId()).then().extract().response();

        // Check success status code
        assertEquals(204, response.getStatusCode());
    }

    private void assertEmployee(Employee expected, Employee given) {
        // Assert matching first_name
        assertEquals(expected.getFirst_name(), given.getFirst_name());
        // Assert matching last_name
        assertEquals(expected.getLast_name(), given.getLast_name());
        // Assert matching email
        assertEquals(expected.getEmail(), given.getEmail());
        // Assert matching gender
        assertEquals(expected.getGender(), given.getGender());
    }
}
