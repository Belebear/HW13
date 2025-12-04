package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleUserRequestBodyModel {
    private String name;
    private String job;
}
