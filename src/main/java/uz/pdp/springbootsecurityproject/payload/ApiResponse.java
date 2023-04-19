package uz.pdp.springbootsecurityproject.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<E> {

    private boolean success;

    private String message;

    private E data;

    private List<ErrorCode> errors;

    private ApiResponse(String message) {
        success=true;
        this.message = message;
    }

    public ApiResponse(E data) {
        success=true;
        this.data = data;
    }
    private ApiResponse(List<ErrorCode> errors) {
        success=false;
        this.errors = errors;
    }


    public static <T>ApiResponse <T>succesResponce(String message) {
        return new ApiResponse<>(message);
    }

    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<>(data);
    }

    public static ApiResponse<List<ErrorCode>> failResponce(String message, int value) {
        return new ApiResponse<>(List.of(new ErrorCode(message,value)));
    }
}
