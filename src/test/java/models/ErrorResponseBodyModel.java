package models;

import lombok.Data;

@Data
public class ErrorResponseBodyModel {
    private String error;
    private String message;
}
