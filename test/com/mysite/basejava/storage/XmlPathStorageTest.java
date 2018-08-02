package com.mysite.basejava.storage;

import com.mysite.basejava.serialization.XmlStreamStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamStrategy()));
    }
}