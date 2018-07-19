package com.mysite.basejava.storage;

import com.mysite.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void fillElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected void insertElement(final Resume resume, final int index) {
        int current = -index - 1;
        System.arraycopy(storage, current, storage, current + 1, size - current);
        storage[current] = resume;
    }

    @Override
    protected Integer getSearchElement(final String uuid) {
        Resume searchKey = new Resume(uuid, "test");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
