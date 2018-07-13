package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(final Resume resume) {
        Object searchElement = getExistedElement(resume.getUuid());
        updateForEachRealization(resume, searchElement);
    }

    public void save(final Resume resume) {
        Object element = getNotExistedElement(resume.getUuid());
        saveForEachRealization(resume, element);
    }

    public void delete(final String uuid) {
        Object element = getExistedElement(uuid);
        deleteForEachRealization(element);
    }

    public Resume get(final String uuid) {
        Object element = getExistedElement(uuid);
        return getForEachRealization(element);
    }

    private Object getExistedElement(final String uuid) {
        Object element = getSearchElement(uuid);
        if (!isExist(element)) {
            throw new NotExistStorageException(uuid);
        }
        return element;
    }

    private Object getNotExistedElement(final String uuid) {
        Object element = getSearchElement(uuid);
        if (isExist(element)) {
            throw new ExistStorageException(uuid);
        }
        return element;
    }

    protected abstract Object getSearchElement(final String uuid);

    protected abstract boolean isExist(final Object element);

    protected abstract void updateForEachRealization(final Resume resume, final Object element);

    protected abstract void saveForEachRealization(final Resume resume, final Object element);

    protected abstract void deleteForEachRealization(final Object element);

    protected abstract Resume getForEachRealization(final Object element);

}
