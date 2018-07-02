package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void insertElement( final Resume resume, final int index) {
        int idx = index;
        if (idx < 0) {
            idx = -idx - 1;
        }
        System.arraycopy(storage, idx, storage, idx + 1, size - idx);
        storage[idx] = resume;
    }

    @Override
    protected int getIndex(final String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}