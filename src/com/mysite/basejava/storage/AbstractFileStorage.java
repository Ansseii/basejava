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

    @Override
    protected List<Resume> toList() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) throw new StorageException("Directory error", directory.getName());
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
            throw new StorageException("Can't write to file", file.getName(), e);
        }
    }

    @Override
    protected void toSave(final File file, final Resume resume) {
        try {
            if (!file.createNewFile()) {
                toUpdate(file, resume);
            }
            toWrite(resume, file);
        } catch (final IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void toDelete(final File file) {
        if (!file.delete()) {
            throw new StorageException("Can't delete file", file.getName());
        }
    }

    @Override
    protected Resume toGet(final File file) {
        try {
            return toRead(file);
        } catch (IOException e) {
            throw new StorageException("Can't read from file", file.getName(), e);
        }
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
        if (list == null) {
            throw new StorageException("Directory error", directory.getName());
        }
        return list.length;
    }

    protected abstract void toWrite(final Resume resume, final File file) throws IOException;

    protected abstract Resume toRead(final File file) throws IOException;
}
