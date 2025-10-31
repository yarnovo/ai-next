package com.todolist.todoservice.dto;

import lombok.Data;

@Data
public class UserInfo {
    private Boolean valid;
    private String userId;
    private String email;
    private String username;
}
