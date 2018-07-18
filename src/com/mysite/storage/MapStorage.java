package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapStorage extends AbstractStorage<String> {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getSearchElement(final String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(final String key) {
        return map.containsKey(key);
    }

    @Override
    protected void toUpdate(final String key, final Resume resume) {
        map.put(key, resume);
    }

    @Override
    protected void toSave(final String key, final Resume resume) {
        map.put(key, resume);
    }

    @Override
    protected void toDelete(final String key) {
        map.remove(key);
    }

    @Override
    protected Resume toGet(final String key) {
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
