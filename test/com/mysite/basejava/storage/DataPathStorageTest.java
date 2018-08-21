package com.mysite.basejava.storage;

import com.mysite.basejava.serialization.DataStreamStrategy;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new FileStorage(STORAGE_DIR, new DataStreamStrategy()));
    }
}
