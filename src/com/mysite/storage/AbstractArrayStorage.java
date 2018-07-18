package com.mysite.storage;

import com.mysite.exception.StorageException;
import com.mysite.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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
    protected List<Resume> toList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void toUpdate(final Integer index, final Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void toSave(final Integer index, final Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, index);
        size++;
    }

    @Override
    protected void toDelete(final Integer index) {
        size--;
        fillElement(index);
        storage[size] = null;

    }

    @Override
    protected Resume toGet(final Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(final Integer index) {
        return index >= 0;
    }

    protected abstract void fillElement(final int index);

    protected abstract void insertElement(final Resume resume, final int index);

    protected abstract Integer getSearchElement(final String uuid);
}

