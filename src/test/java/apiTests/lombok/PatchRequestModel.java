package apiTests.lombok;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchRequestModel {
    String name;
    String job;
    String id;
    String id2;
    String id3;
    String id4;
    String id5;
}
