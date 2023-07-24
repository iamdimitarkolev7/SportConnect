package com.connect.sport.authentication.payload.request;

import com.connect.sport.authentication.payload.request.base.UserAuthRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterRequest extends UserAuthRequest {

    private String firstName;
    private String lastName;
    private String confirmPassword;
}
