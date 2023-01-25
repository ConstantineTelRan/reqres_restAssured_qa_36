import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTests {
    private final String BASE_URI = "https://reqres.in";

    //Пример выполнения get запроса без проверок
    @Test
    public void getListUser_1() {
        RestAssured.given()
                .when()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all();
    }

//    Пример выполнения get запроса с проверкой полей ответа с помощью TestNG
    @Test
    public void getListUser_2() {
        Response response = RestAssured.given()
                .when()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "The actual status is not 200");
        Assert.assertEquals(response.body().jsonPath().getInt("data[0].id"), 7, "The id does not match expected id");
        Assert.assertEquals(response.body().jsonPath().getString("data[1].email"), "lindsay.ferguson@reqres.in");
    }

//     Пример выполнения get запроса с проверкой полей ответа с помощью Rest Assured
    @Test
    public void getListUser_3() {
        RestAssured.given()
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("data[0].id", Matchers.equalTo(7))
                .body("data[1].email", Matchers.equalTo("lindsay.ferguson@reqres.in"));
    }







}
