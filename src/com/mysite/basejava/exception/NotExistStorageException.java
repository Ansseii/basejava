package com.mysite.basejava.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(final String uuid) {
        super("Resume with uuid =  " + uuid + " not exist", uuid);
    }
}
