package com.mysite.basejava.exception;

public class StorageException extends RuntimeException {

    private final String uuid;

    public StorageException(final String message, final String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(final String message, final String uuid, final Exception exception) {
        super(message, exception);
        this.uuid = uuid;
    }

    public StorageException(final Exception e) {
        this(e.getMessage(), null, e);
    }

    public String getUuid() {
        return uuid;
    }
}
