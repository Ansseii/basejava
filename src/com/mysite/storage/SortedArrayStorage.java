package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void insertElement(final Resume resume, final int index) {
        int current = -index - 1;
        System.arraycopy(storage, current, storage, current + 1, size - current);
        storage[current] = resume;
    }

    @Override
    protected Integer getSearchElement(final String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
