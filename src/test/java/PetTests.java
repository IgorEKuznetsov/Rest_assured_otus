
import dto.Pet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetService;

public class PetTests {
  PetService petService = new PetService();
  int petId;

  /**
   * Для всех кейсов:
   * Предусловие: генерация питомца
   * Ожидаемый результат:
   * статус код 200
   * Content-Type: application/json
   * HTTP/1.1 200 OK
   * время запроса менее 5с
   * ответ прохоит валидацию по схеме
   * атрибуты в запросе соответствуют атрибутам в ответе
   * Постусловие: удаление питомца, проверка ответа
   */

  @Test
  @DisplayName("Создание питомца с минимальным набором атрибутов")
  public void createPetMinTest() {
    Pet pet = petService.minPetBuild();
    Pet petResponse = petService.createPet(pet).extract().as(Pet.class);
    petId = petResponse.getId();
    Assertions.assertEquals(pet, petResponse);

  }

  @Test
  @DisplayName("Создание питомца с максимальным набором атрибутов")
  public void createPetMaxTest() {
    Pet pet = petService.maxPetBuild();
    Pet petResponse = petService.createPet(pet).extract().as(Pet.class);
    petId = petResponse.getId();
    Assertions.assertEquals(pet, petResponse);

  }

  @Test
  @DisplayName("Получение питомца с максимальным набором атрибутов")
  public void getPetMaxTest() {
    Pet pet = petService.maxPetBuild();
    Pet petCreateResponse = petService.createPet(pet).extract().as(Pet.class);
    Assertions.assertEquals(pet, petCreateResponse);
    petId = petCreateResponse.getId();
    Pet petGetResponse = petService.getPet(petId).extract().as(Pet.class);
    Assertions.assertEquals(pet, petGetResponse);

  }

  @AfterEach
  public void killPet() {
    petService.deletePet(petId);
  }


}
