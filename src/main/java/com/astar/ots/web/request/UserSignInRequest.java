package com.astar.ots.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequest implements Serializable {

    @Schema(description = "Username", defaultValue = "admin")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Schema(description = "Password", defaultValue = "admin")
    @NotBlank(message = "Password can not be blank")
    private String password;
}
