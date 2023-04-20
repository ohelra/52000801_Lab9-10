package midterm.commerce.api.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, List<String>> errors;

    public CartResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CartResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public CartResponse(boolean success, String message, Map<String, List<String>> errors) {
        this.success = success;
        this.message = message;
        this.errors = errors;
    }
}