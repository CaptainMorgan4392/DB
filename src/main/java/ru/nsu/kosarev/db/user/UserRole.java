package ru.nsu.kosarev.db.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRole {

    @JsonProperty("admin")
    ADMIN,

    @JsonProperty("privileged")
    PRIVILEGED,

    @JsonProperty("common")
    COMMON

}
