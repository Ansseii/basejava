package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private final Resume[] storage = new Resume[10000];
    private int count = 0;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void save(final Resume resume) {
        if (count < storage.length) {
            if (getIndexOfUuid(resume.getUuid()) == -1) {
                storage[count++] = resume;
            } else {
                System.out.println("Resume is present");
            }
        } else {
            System.out.println("There is no space in the storage");
        }
    }

    public Resume get(final String uuid) {
        if (getIndexOfUuid(uuid) != -1) {
            return storage[getIndexOfUuid(uuid)];
        }
        System.out.println("Resume is not present");
        return null;
    }

    public void delete(final String uuid) {
        int index = getIndexOfUuid(uuid);
        if (index != -1) {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
        } else {
            System.out.println("Resume is not present");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    public void update(final Resume resume) {
        int index = getIndexOfUuid(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Resume is not present");
        }
    }

    private int getIndexOfUuid(final String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}

