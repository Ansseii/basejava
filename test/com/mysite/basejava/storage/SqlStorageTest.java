package com.mysite.basejava.storage;

import com.mysite.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getDbUrl().toString(),
                Config.get().getDbUser().toString(),
                Config.get().getDbPassword().toString()));
    }
}