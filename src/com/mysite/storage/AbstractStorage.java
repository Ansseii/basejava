package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(final Resume resume) {
        Object searchElement = getExistedElement(resume.getUuid());
        updateForAnyRealization(resume, searchElement);
    }

    public void save(final Resume resume) {
        Object element = getNotExistedElement(resume.getUuid());
        saveForAnyRealization(resume, element);
    }

    public void delete(final String uuid) {
        Object element = getExistedElement(uuid);
        deleteForAnyRealization(element);
    }

    public Resume get(final String uuid) {
        Object element = getExistedElement(uuid);
        return getForAnyRealization(element);
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

    protected abstract void updateForAnyRealization(final Resume resume, final Object element);

    protected abstract void saveForAnyRealization(final Resume resume, final Object element);

    protected abstract void deleteForAnyRealization(final Object element);

    protected abstract Resume getForAnyRealization(final Object element);

}
