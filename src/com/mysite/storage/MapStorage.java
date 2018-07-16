package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getSearchElement(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Object key) {
        return false;
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {

    }

    @Override
    protected void toSave(Resume resume, Object key) {

    }

    @Override
    protected void toDelete(Object key) {

    }

    @Override
    protected Resume toGet(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>();
    }

    @Override
    public int size() {
        return 0;
    }
}
