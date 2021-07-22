package com.overonix.signup.model;

import java.util.UUID;

public class BaseResponse {

    private boolean published = false;
    private UUID uuid = null;

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
