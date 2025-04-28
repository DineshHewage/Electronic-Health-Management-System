package com.sd.pms.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BloodGroup {

    AP("AP" , "A Positive"),
    AN("AN" , "A Negative"),
    BP("BP" , "B Positive"),
    BN("BN" , "B Negative"),
    ABP("ABP" , "AB Positive"),
    ABN("ABN" , "AB Negative"),
    OP("OP" , "O Positive"),
    ON("ON" , "O Negative");

    @Getter
    private final String displayText;

    @Getter
    private final String description;

}
