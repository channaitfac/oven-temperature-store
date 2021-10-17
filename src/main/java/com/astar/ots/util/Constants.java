package com.astar.ots.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public interface Constants {

    String AUTH = "auth";
    String BEARER = "Bearer ";
    String FORWARD_SLASH = "/";
    String USER_AGENT= "User-Agent";
    String AUTHORIZATION = "Authorization";
    String SG_TIME_ZONE = "Asia/Singapore";
    String FILE_NAME = "ovens_temperatures.json";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    ObjectMapper MAPPER = new ObjectMapper();
    SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

    interface UserDetail {
        String USERNAME = "admin";
        String PASSWORD = "admin";
        String EMAIL = "admin@email.com";
    }

    interface RestUrls {
        String USERS = "users";
        String OVENS = "ovens";
        String SIGN_IN = "signin";
        String TEMPERATURE = "temperature";
        String APP_NAME = "oven-temperature-store";
        String BASE_URL = "http://127.0.0.1:44391";
        String OVEN_TEMPERATURES = "oven-temperatures";
    }

    interface ErrorMessages {
        String USER_NOT_EXISTS = "User not exists";
        String INVALID_PARAMETER = "Invalid parameter(s)";
        String EXPIRED_JWT_TOKEN = "Expired or invalid JWT token";
        String USERNAME_ALREADY_USE = "Username is already in use";
        String CANNOT_FOUND_OVEN = "Can not found any Oven record(s)";
        String INVALID_CREDENTIALS = "Invalid username/password supplied";
        String CANNOT_FOUND_OVEN_FOR_ID = "Can not found any Oven for given Id";
        String GET_JWT_TOKEN = "Exception occurred while trying to get JWT token";
    }
}
