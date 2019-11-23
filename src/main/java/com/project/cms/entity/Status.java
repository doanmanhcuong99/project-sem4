package com.project.cms.entity;

import java.util.stream.Stream;

public enum  Status {
    OPEN("open"),
    CLOSED("closed");

    private String typeOfStatus;

    Status(String typeOfStatus){
        this.typeOfStatus = typeOfStatus;
    }

    public String getTypeOfStatus() {
        return typeOfStatus;
    }

    public static Stream<Status> stream() {
        return Stream.of(Status.values());
    }
}
