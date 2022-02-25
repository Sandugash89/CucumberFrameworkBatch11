package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {
    //storing the base uri to use future
    String baseURI=RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDU3MzY2NjEsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY0NTc3OTg2MSwidXNlcklkIjoiMzYxMiJ9.o-l-jWQ_N_ayIcAoGzMldD17U7g5I13APUp3TRhN8DU";
    static String employee_id;
    @Test
    public void bGetCreatedEmployee(){


        //preparing the request to get an employee
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json")
                .header("Authorization",token)
                .queryParam("employee_id", employee_id);
       Response response= preparedRequest.when().get("/getOneEmployee.php");

        //System.out.println(response.asString());
        //prettyprint does the same as system.our.println();
        response.prettyPrint();
        String empID=response.jsonPath().getString("employee.employee_id");
        boolean comparingEmpID=empID.contentEquals(employee_id);
        Assert.assertTrue(comparingEmpID);
        response.then().assertThat().statusCode(200);
    }
    @Test
    public void aCreateEmployee(){
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json").
                header("Authorization", token).body("{\n" +
                        "  \"emp_firstname\": \"ALT\",\n" +
                        "  \"emp_lastname\": \"ISSA\",\n" +
                        "  \"emp_middle_name\": \"AMO\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1998-02-18\",\n" +
                        "  \"emp_status\": \"Employee\",\n" +
                        "  \"emp_job_title\": \"QA\"\n" +
                        "}");
        Response response= preparedRequest.when().post("/createEmployee.php");
        response.prettyPrint();
        //we use jsonPath() to get specific information from the json object
        employee_id=response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);
        //Assertion
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("Employee.emp_middle_name",equalTo("AMO"));
        response.then().assertThat().body("Message",equalTo("Employee Created"));
        response.then().assertThat().header("Server", equalTo ("Apache/2.4.39 (Win64) PHP/7.2.18"));
        response.then().assertThat().body("Employee.emp_job_title", equalTo("QA"));

    }
    @Test
    public  void cUpdateEmployee(){
        RequestSpecification preparedrequst = given().header("Content_Type","application/json")
                .header("Authorization",token).body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"Mar\",\n" +
                        "  \"emp_lastname\": \"SAN\",\n" +
                        "  \"emp_middle_name\": \"M\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2002-02-19\",\n" +
                        "  \"emp_status\": \"employee\",\n" +
                        "  \"emp_job_title\": \"QA\"\n" +
                        "}");
 Response response = preparedrequst.when().put("/updateEmployee.php");
 response.prettyPrint();
 response.then().assertThat().body("Message",equalTo("Employee record Updated"));
 response.then().assertThat().statusCode(200);
    }
    @Test
    public void dGetUpdatedEmployee(){
        RequestSpecification requestSpecification = given().header("Content_Type","application/json")
                .header("Authorization",token).queryParam("employee_id",employee_id);
        Response response=requestSpecification.when().get("/getOneEmployee.php");
        String middleName=response.jsonPath().getString("employee.emp_middle_name");
        Assert.assertTrue(middleName.contains("M"));
    }
}
