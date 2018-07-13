package com.mysite.storage;

import com.mysite.model.Resume;

public class MapStorage extends AbstractStorage {


    @Override
    protected Object getSearchElement(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Object element) {
        return false;
    }

    @Override
    protected void updateForEachRealization(Resume resume, Object element) {

    }

    @Override
    protected void saveForEachRealization(Resume resume, Object element) {

    }

    @Override
    protected void deleteForEachRealization(Object element) {

    }

    @Override
    protected Resume getForEachRealization(Object element) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
