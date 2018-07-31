package com.mysite.basejava.storage;

import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage extends AbstractFileStorage {

    protected ObjectStreamFileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void toWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    protected Resume toRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (final ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
