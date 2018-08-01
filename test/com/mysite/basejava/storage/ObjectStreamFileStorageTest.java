package com.mysite.basejava.storage;

import com.mysite.basejava.serialization.ObjectStreamStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}