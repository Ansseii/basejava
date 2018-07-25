package com.mysite.basejava.storage;

import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(final File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    private void isNull(final Object[] objects) throws StorageException {
        if (objects == null) {
            throw new StorageException("Directory error", directory.getName());
        }
    }

    private void throwException(final String message, final String fileName,
                                final Exception exception) throws StorageException {
        throw new StorageException(message, fileName, exception);
    }

    @Override
    protected List<Resume> toList() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        isNull(files);
        for (File file : files) {
            resumes.add(toGet(file));
        }
        return resumes;
    }

    @Override
    protected File getSearchElement(final String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(final File file) {
        return file.exists();
    }

    @Override
    protected void toUpdate(final File file, final Resume resume) {
        try {
            toWrite(resume, file);
        } catch (final IOException e) {
            throwException("Can't write to file", file.getName(), e);
        }
    }

    @Override
    protected void toSave(final File file, final Resume resume) {
        try {
            file.createNewFile();
            toWrite(resume, file);
        } catch (final IOException e) {
            throwException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void toDelete(final File file) {
        if (!file.delete()) {
           throwException("Can't delete file", file.getName(), null);
        }
    }

    @Override
    protected Resume toGet(final File file) {
        try {
            return toRead(file);
        } catch (IOException e) {
            throwException("Can't read from file", file.getName(), e);
        }
        return null;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                toDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        isNull(list);
        return list.length;
    }

    protected abstract void toWrite(final Resume resume, final File file) throws IOException;

    protected abstract Resume toRead(final File file) throws IOException;
}
