package com.mysite.basejava;

import com.mysite.basejava.storage.SqlStorage;
import com.mysite.basejava.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final static File PROPERTIES = new File(System.getProperty("root"), "basejava/config/resumes.properties");

    private final static Config INSTANCE = new Config();

    private File storageDir;

    private Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {

        try (InputStream inputStream = new FileInputStream(PROPERTIES)) {
            Properties props = new Properties();
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"),
                    props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
