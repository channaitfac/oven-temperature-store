package com.astar.ots.web.rest;

import com.astar.ots.util.Constants;
import com.astar.ots.web.request.UserSignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Tag(name = "User Authentication Detail", description = "REST API for user authenticating related operations")
public interface UserController {

    /**
     * Get user associated JSON Web Token
     * @return JWT token
     *
     * Ex: POST http://127.0.0.1:44391/oven-temperature-store/users/signin
     */
    @Operation(summary = "Get JWT token", description = "Get user associated JSON Web Token (JWT)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return generated JSON Web Token (JWT)"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = Constants.ErrorMessages.INVALID_PARAMETER)
    })
    @RequestMapping(
            value = Constants.FORWARD_SLASH + Constants.RestUrls.SIGN_IN,
            method = RequestMethod.POST,
            consumes = {APPLICATION_JSON_VALUE},
            produces = {TEXT_PLAIN_VALUE}
    )
    ResponseEntity login(@RequestBody UserSignInRequest userSignInRequest);
}
