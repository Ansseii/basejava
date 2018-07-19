package com.mysite.basejava.storage;

import com.mysite.basejava.exception.ExistStorageException;
import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {

    public void update(final Resume resume) {
        T searchElement = getExistedElement(resume.getUuid());
        toUpdate(searchElement, resume);
    }

    public void save(final Resume resume) {
        T searchElement = getNotExistedElement(resume.getUuid());
        toSave(searchElement, resume);
    }

    public void delete(final String uuid) {
        T searchElement = getExistedElement(uuid);
        toDelete(searchElement);
    }

    public Resume get(final String uuid) {
        T searchElement = getExistedElement(uuid);
        return toGet(searchElement);
    }

    private T getExistedElement(final String uuid) {
        T searchElement = getSearchElement(uuid);
        if (!isExist(searchElement)) {
            throw new NotExistStorageException(uuid);
        }
        return searchElement;
    }

    private T getNotExistedElement(final String uuid) {
        T searchElement = getSearchElement(uuid);
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

    protected abstract T getSearchElement(final String uuid);

    protected abstract boolean isExist(final T element);

    protected abstract void toUpdate(final T element, final Resume resume);

    protected abstract void toSave(final T element, final Resume resume);

    protected abstract void toDelete(final T element);

    protected abstract Resume toGet(final T element);
}
