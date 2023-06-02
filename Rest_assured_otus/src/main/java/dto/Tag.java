package dto;

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
public class Tag {
  @JsonProperty("id")
  public Integer id;
  @JsonProperty("name")
  public String name;
}
