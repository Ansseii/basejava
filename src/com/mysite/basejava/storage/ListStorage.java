package com.mysite.basejava.storage;

import com.mysite.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected boolean isExist(final Integer element) {
        return element != null;
    }

    @Override
    protected void toUpdate(final Integer index, final Resume resume) {
        list.set(index, resume);
    }

    @Override
    protected void toSave(final Integer index, final Resume resume) {
        list.add(resume);
    }

    @Override
    protected void toDelete(final Integer index) {
        list.remove((index).intValue());
    }

    @Override
    protected Resume toGet(final Integer index) {
        return list.get(index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected List<Resume> toList() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}
