package com.example.registration.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFullDTO extends UserDTO {

    @JsonProperty("personId")
    private String personId;
    @JsonProperty("uniqueId")
    private UUID uniqueId;

    public UserFullDTO(Long id, String name, String surname, String personId, UUID uniqueId) {
        super(id, name, surname);
    this.personId = personId;
    this.uniqueId = uniqueId;
    }

}
