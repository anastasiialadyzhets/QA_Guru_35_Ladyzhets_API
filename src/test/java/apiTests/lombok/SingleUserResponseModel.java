package apiTests.lombok;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleUserResponseModel {
    // Основной класс, представляющий структуру JSON
    private Data data;
    private Support support;
    @lombok.Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Data {
        private int id;
        private String email;
        private String firstName;
        private String lastName;
        private String avatar;
    }
    @lombok.Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Support {
        private String url;
        private String text;
    }

}
