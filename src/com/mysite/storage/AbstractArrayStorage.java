package com.mysite.storage;

import com.mysite.exception.StorageException;
import com.mysite.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateForAnyRealization(final Resume resume, final Object element) {
        storage[(Integer) element] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void saveForAnyRealization(final Resume resume, final Object element) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, (Integer) element);
        size++;
    }

    @Override
    protected void deleteForAnyRealization(final Object element) {
        fillElement((Integer) element);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume getForAnyRealization(final Object element) {
        return storage[(Integer) element];
    }

    @Override
    protected boolean isExist(final Object element) {
        return (Integer) element >= 0;
    }

    protected abstract void fillElement(final int index);

    protected abstract void insertElement(final Resume resume, final int index);

    protected abstract Integer getSearchElement(final String uuid);
}

