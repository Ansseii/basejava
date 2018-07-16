package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getSearchElement(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object key) {
        return map.containsKey(key);
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void toSave(Resume resume, Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void toDelete(Object key) {
        map.remove(key);
    }

    @Override
    protected Resume toGet(Object key) {
        return map.get(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> toList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
