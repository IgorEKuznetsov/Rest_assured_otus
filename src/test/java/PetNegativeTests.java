import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import io.qameta.allure.Feature;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetService;

@Feature("Negative tests")
public class PetNegativeTests {
  PetService petService = new PetService();

  @Test
  @DisplayName("Получение питомца с несуществующим id")
  public void getNotExistingPetTest() {
    ValidatableResponse res = petService.getUnknownPet(6896895)
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .time(lessThan(5000l))
        .body("code", equalTo(1))
        .body("type", equalTo("error"))
        .body("message", equalTo("Pet not found"));

  }
}
