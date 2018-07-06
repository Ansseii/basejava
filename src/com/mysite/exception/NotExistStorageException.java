package com.mysite.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(final String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
