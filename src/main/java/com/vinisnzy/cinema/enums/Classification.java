package com.vinisnzy.cinema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Classification {
    LIVRE("Livre"),
    C10("10"),
    C12("12"),
    C14("14"),
    C16("16"),
    C18("18");

    private final String value;

    @JsonCreator
    public static Classification fromValue(String value) {
        if (value == null) {
            return LIVRE;
        }

        for (Classification classification : values()) {
            if (classification.value.equalsIgnoreCase(value.trim())) {
                return classification;
            }
        }
        return LIVRE;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
