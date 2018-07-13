package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchElement(final String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(final Object element) {
        return element != null;
    }

    @Override
    protected void updateForAnyRealization(final Resume resume, final Object element) {
        list.set((Integer) element, resume);
    }

    @Override
    protected void saveForAnyRealization(final Resume resume, final Object element) {
        list.add(resume);
    }

    @Override
    protected void deleteForAnyRealization(final Object element) {
        list.remove(((Integer) element).intValue());
    }

    @Override
    protected Resume getForAnyRealization(final Object element) {
        return list.get((Integer) element);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
