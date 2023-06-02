package dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
  @JsonProperty("id")
  public Integer id;
  @JsonProperty("category")
  public Category category;
  @JsonProperty("name")
  public String name;
  @JsonProperty("photoUrls")
  public List<String> photoUrls;
  @JsonProperty("tags")
  public List<Tag> tags;
  @JsonProperty("status")
  public String status;

}
