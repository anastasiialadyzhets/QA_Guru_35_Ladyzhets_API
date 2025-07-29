package apiTests.lombok;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CreateRequestModel {
    String name;
    String job;
    String id;
    String id2;
    String id3;
    String id4;
    String id5;

}
