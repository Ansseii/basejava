package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(final Resume resume) {
        Object searchElement = getExistedElement(resume.getUuid());
        toUpdate(resume, searchElement);
    }

    public void save(final Resume resume) {
        Object searchElement = getNotExistedElement(resume.getUuid());
        toSave(resume, searchElement);
    }

    public void delete(final String uuid) {
        Object searchElement = getExistedElement(uuid);
        toDelete(searchElement);
    }

    public Resume get(final String uuid) {
        Object searchElement = getExistedElement(uuid);
        return toGet(searchElement);
    }

    private Object getExistedElement(final String uuid) {
        Object searchElement = getSearchElement(uuid);
        if (!isExist(searchElement)) {
            throw new NotExistStorageException(uuid);
        }
        return searchElement;
    }

    private Object getNotExistedElement(final String uuid) {
        Object searchElement = getSearchElement(uuid);
        if (isExist(searchElement)) {
            throw new ExistStorageException(uuid);
        }
        return searchElement;
    }

    protected abstract Object getSearchElement(final String uuid);

    protected abstract boolean isExist(final Object element);

    protected abstract void toUpdate(final Resume resume, final Object element);

    protected abstract void toSave(final Resume resume, final Object element);

    protected abstract void toDelete(final Object element);

    protected abstract Resume toGet(final Object element);

}
