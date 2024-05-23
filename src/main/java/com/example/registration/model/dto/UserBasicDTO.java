package com.example.registration.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicDTO extends UserDTO {

    public UserBasicDTO(Long id, String name, String surname) {
        super(id, name, surname);
    }
}
