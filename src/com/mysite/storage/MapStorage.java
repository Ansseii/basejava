package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getSearchElement(final String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(final Object key) {
        return map.containsKey(key);
    }

    @Override
    protected void toUpdate(final Resume resume, final Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void toSave(final Resume resume, final Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void toDelete(final Object key) {
        map.remove(key);
    }

    @Override
    protected Resume toGet(final Object key) {
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
