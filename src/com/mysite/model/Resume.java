package com.mysite.model;

/**
 * com.urise.webapp.model.com.mysite.model.Resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
