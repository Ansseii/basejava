package com.mysite.basejava.storage;

import com.mysite.basejava.serialization.ObjectStreamFileStorage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamFileStorage()));
    }
}