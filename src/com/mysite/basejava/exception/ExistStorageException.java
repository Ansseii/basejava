package com.mysite.basejava.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(final String uuid) {
        super("Resume with uuid = " + uuid + " already exist", uuid);
    }
}
