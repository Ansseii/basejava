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
        if (this.count < storage.length) {
            if (!resumeIsPresent(resume.getUuid())) {
                storage[count++] = resume;
            } else {
                System.out.println("Resume is present");
            }
        } else {
            System.out.println("There is no space in the storage");
        }
    }

    public Resume get(final String uuid) {
        if (resumeIsPresent(uuid)) {
            for (int i = 0; i < count; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return storage[i];
                }
            }
        }
        System.out.println("Resume is not present");
        return null;
    }

    public void delete(final String uuid) {
        if (resumeIsPresent(uuid)) {
            for (int i = 0; i < count; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    System.arraycopy(storage, i + 1, storage, i, count - i - 1);
                    count--;
                }
            }
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
        if (resumeIsPresent(resume.getUuid())) {
            for (int i = 0; i < count; i++) {
                if (resumeIsPresent(storage[i].getUuid())) {
                    storage[i] = resume;
                }
            }
        } else {
            System.out.println("Resume is not present");
        }
    }

    private boolean resumeIsPresent(final String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return true;
            }
        }
        return false;
    }

}

