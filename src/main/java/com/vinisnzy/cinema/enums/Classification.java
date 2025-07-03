package com.vinisnzy.cinema.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Classification {
    LIVRE("Livre"),
    C10("10 anos"),
    C12("12 anos"),
    C14("14 anos"),
    C16("16 anos"),
    C18("18 anos");

    private final String value;
}
