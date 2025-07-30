package com.nicolas.qa;

public enum UserType {
    
    STANDARD_USER("standard_user","secret_sauce"),
    LOCKED_OUT_USER("locked_out_user","secret_sauce"),
    PROBLEM_USER("problem_user","secret_sauce"),
    PERFORMANCE_GLITCH_USER("performance_glitch_user","secret_sauce");

    private String username;
    private String password;

    UserType(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
