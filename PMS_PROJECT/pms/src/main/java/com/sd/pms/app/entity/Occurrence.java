package com.sd.pms.app.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Occurrence {
    DAILY("DAILY" , "DAILY"),
    WEEKLY("WEEKLY" , "WEEKLY"),
    MONTHLY("MONTHLY" , "MONTHLY"),
    YEARLY("YEARLY" , "YEARLY");

    @Getter
    private final String displayText;

    @Getter
    private final String description;
}
