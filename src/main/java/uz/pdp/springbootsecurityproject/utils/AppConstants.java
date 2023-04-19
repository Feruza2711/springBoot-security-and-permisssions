package uz.pdp.springbootsecurityproject.utils;

import uz.pdp.springbootsecurityproject.controller.AuthController;

public interface AppConstants {
    String BASE_PATH="/api";
    String AUTH_HEADER="Authorization";
    String AUTH_TYPE_BASIC="Basic ";
    String AUTH_TYPE_BEARER="Bearer ";
    String[] OPEN_PAGES = {
            AuthController.BASE_PATH + "/**",
    };

}
