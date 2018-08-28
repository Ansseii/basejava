package com.mysite.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final static File PROPERTIES = new File("../basejava/config/resumes.properties");

    private final static Config INSTANCE = new Config();

    private File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {

        try (InputStream inputStream = new FileInputStream(PROPERTIES)) {
            Properties props = new Properties();
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
