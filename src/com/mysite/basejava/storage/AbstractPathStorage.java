package com.mysite.basejava.storage;

import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(final String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    private Stream<Path> getAllFiles() {
        try {
            return Files.list(directory);
        } catch (final IOException e) {
            throw new StorageException("Directory error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> toList() {
        return getAllFiles().map(this::toGet).collect(Collectors.toList());
    }

    @Override
    protected Path getSearchElement(final String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(final Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void toUpdate(final Path path, final Resume resume) {
        try {
            toWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (final IOException e) {
            throw new StorageException("Can't write to file", resume.getUuid(), e);
        }
    }

    @Override
    protected void toSave(final Path path, final Resume resume) {
        try {
            Files.createFile(path);
            toUpdate(path, resume);
        } catch (final IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void toDelete(final Path path) {
        try {
            Files.delete(path);
        } catch (final IOException e) {
            throw new StorageException("Can't delete file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume toGet(final Path path) {
        try {
            return toRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (final IOException e) {
            throw new StorageException("Can't read from file", path.getFileName().toString(), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::toDelete);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return (int) getAllFiles().count();
    }

    protected abstract void toWrite(final Resume resume, final OutputStream outputStream) throws IOException;

    protected abstract Resume toRead(final InputStream inputStream) throws IOException;
}
