package com.overonix.signup.model;

import java.util.UUID;

public class BaseRequest {
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
