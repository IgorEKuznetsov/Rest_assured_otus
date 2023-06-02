package services;

import com.github.javafaker.Faker;
import dto.Category;
import dto.Pet;
import dto.Tag;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PetService {
  Faker faker = new Faker();
  List<String> photoUrls = new ArrayList<>();
  List<Tag> tags = new ArrayList<>();
  Pet pet = null;
  private static final String BASE_URI = System.getProperty("base.uri");
  private RequestSpecification requestSpecification;
  private ResponseSpecification responseSpecification;
  private static final String CREATE_PET_ENDPOINT = "/pet";
  private static final String GET_PET_ENDPOINT = "/pet/{petId}";
  private static final String DELETE_PET_ENDPOINT = "/pet/{petId}";


  public PetService() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    requestSpecification = new RequestSpecBuilder()
        .setBaseUri(BASE_URI)
        .setContentType(ContentType.JSON)
        .build();

    responseSpecification = new ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(200)
        .expectStatusLine("HTTP/1.1 200 OK")
        .expectContentType(ContentType.JSON)
        .expectResponseTime(Matchers.lessThan(5000L))
        .build();
  }


  public ValidatableResponse createPet(Pet pet) {
    return given(requestSpecification)
        .body(pet)
        .when()
        .post(CREATE_PET_ENDPOINT)
        .then()
        .spec(responseSpecification)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"));
  }

  public ValidatableResponse getPet(int id) {
    return given(requestSpecification)
        .when()
        .get(GET_PET_ENDPOINT, id)
        .then()
        .spec(responseSpecification)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"));
  }

  public ValidatableResponse getUnknownPet(int id) {
    return given(requestSpecification)
        .when()
        .get(GET_PET_ENDPOINT, id)
        .then()
        .log().all();
  }

  public ValidatableResponse deletePet(int id) {
    return given(requestSpecification)
        .when()
        .delete(DELETE_PET_ENDPOINT, id)
        .then()
        .spec(responseSpecification)
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body("message", equalTo(String.valueOf(id)));
  }

  public Pet minPetBuild() {
    return pet = Pet.builder()
        .id(faker.number().numberBetween(11111, 99999))
        .name("PET " + faker.name().username())
        .status("available")
        .photoUrls(photoUrls)
        .tags(tags)
        .build();
  }

  public Pet maxPetBuild() {
    Category category = Category.builder()
        .id(faker.number().numberBetween(11111, 99999))
        .name(faker.name().fullName())
        .build();

    photoUrls.add("http://petstore.photo.com");

    tags.add(Tag.builder()
        .id(faker.number().numberBetween(11111, 99999))
        .name(faker.name().name())
        .build());

    return pet = Pet.builder()
        .id(faker.number().numberBetween(11111, 99999))
        .category(category)
        .name("PET " + faker.name().username())
        .photoUrls(photoUrls)
        .tags(tags)
        .status("available")
        .build();
  }
}
