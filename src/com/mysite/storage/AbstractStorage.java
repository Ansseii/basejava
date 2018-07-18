package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public void update(final Resume resume) {
        Object searchElement = getExistedElement(resume.getUuid());
        toUpdate(searchElement, resume);
    }

    public void save(final Resume resume) {
        Object searchElement = getNotExistedElement(resume.getUuid());
        toSave(searchElement, resume);
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

    public List<Resume> getAllSorted() {
        List<Resume> resumes = toList();
        Collections.sort(resumes);
        return resumes;
    }

    protected abstract List<Resume> toList();

    protected abstract Object getSearchElement(final String uuid);

    protected abstract boolean isExist(final Object element);

    protected abstract void toUpdate(final Object element, final Resume resume);

    protected abstract void toSave(final Object element, final Resume resume);

    protected abstract void toDelete(final Object element);

    protected abstract Resume toGet(final Object element);
}
