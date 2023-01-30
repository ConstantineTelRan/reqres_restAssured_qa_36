import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.request.UserDto;
import model.response.ListUsersRespDto;
import model.response.UserRespDto;
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

    @Test
    public void createUser_1() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        RestAssured.given()
                .header("Content-type","application/json")
                .body(body)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo("morpheus"));
    }

    @Test
    public void createUser_2() {
        String name = "morpheus";
        String job = "leader";
        UserDto userDto = new UserDto(name, job);

        RestAssured.given()
                .header("Content-type","application/json")
                .body(userDto)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo(name));
    }

    @Test
    public void createUser_3() {
        String name = "morpheus";
        String job = "leader";
        UserDto userDto = new UserDto(name, job);

        UserRespDto userRespDto =  RestAssured.given()
                .header("Content-type","application/json")
                .body(userDto)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .extract().as(UserRespDto.class);
        Assert.assertEquals(userRespDto.name, name);
        Assert.assertEquals(userRespDto.job, job);

    }

    @Test
    public void getListUsers() {
        ListUsersRespDto listUsersRespDto = RestAssured.given()
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .extract().as(ListUsersRespDto.class);

        Assert.assertEquals(listUsersRespDto.data.get(2).email, "tobias.funke@reqres.in");
        Assert.assertEquals(listUsersRespDto.data.get(5).first_name, "Rachel");
    }












}
