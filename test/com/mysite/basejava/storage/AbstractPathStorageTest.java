package com.mysite.basejava.storage;

import static org.junit.Assert.*;

public class AbstractPathStorageTest extends AbstractStorageTest {

    public AbstractPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.getName()));
    }
}