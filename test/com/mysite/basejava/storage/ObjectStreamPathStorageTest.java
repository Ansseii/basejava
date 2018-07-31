package com.mysite.basejava.storage;


import com.mysite.basejava.serialization.ObjectStreamPathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamPathStorage()));
    }
}