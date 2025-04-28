package com.sd.pms.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleType {

    USER("USER"),
    DOCTOR("DOCTOR"),
    DATA("DATA");

    @Getter
    private final String displayText;

}
