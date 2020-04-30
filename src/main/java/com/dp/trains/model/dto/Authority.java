package com.dp.trains.model.dto;

public enum Authority {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String name;

    Authority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
