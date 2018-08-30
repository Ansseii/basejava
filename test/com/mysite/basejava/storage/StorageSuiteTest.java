package com.mysite.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        SortedArrayStorageTest.class,
        ObjectStreamPathStorageTest.class,
        ObjectStreamFileStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class,
        SqlStorageTest.class
})

public class StorageSuiteTest {
}
