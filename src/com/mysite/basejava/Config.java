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
    private File dbUrl;
    private File dbUser;
    private File dbPassword;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {

        try (InputStream inputStream = new FileInputStream(PROPERTIES)) {
            Properties props = new Properties();
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = new File(props.getProperty("db.url"));
            dbUser = new File(props.getProperty("db.user"));
            dbPassword = new File(props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public File getDbUrl() {
        return dbUrl;
    }

    public File getDbUser() {
        return dbUser;
    }

    public File getDbPassword() {
        return dbPassword;
    }
}
