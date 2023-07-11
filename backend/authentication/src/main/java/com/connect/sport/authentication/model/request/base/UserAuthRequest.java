package com.connect.sport.authentication.model.request.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthRequest {

    private String username;
    private String email;
    private String password;
}
