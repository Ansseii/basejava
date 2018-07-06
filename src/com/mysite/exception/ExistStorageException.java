package com.mysite.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(final String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
