package com.mysite.basejava.serialization;

import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage implements Strategy {

    @Override
    public void toWrite(final Resume resume, final OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    public Resume toRead(final InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (final ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
