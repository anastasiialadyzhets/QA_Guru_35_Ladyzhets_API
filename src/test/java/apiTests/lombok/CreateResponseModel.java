package apiTests.lombok;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CreateResponseModel {
    String name;
    String job;
    String id;
    String createdAt;
}
