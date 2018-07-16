package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAllStorageTest {

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume resume1 = new Resume(UUID_1, "Ivanov Ivan Ivanovich");
    private static final String UUID_2 = "uuid2";
    private static final Resume resume2 = new Resume(UUID_2, "Petrov Petr Petrovich");
    private static final String UUID_3 = "uuid3";
    private static final Resume resume3 = new Resume(UUID_3, "Ivanov Ivan Ivanovich");
    private static final String UUID_4 = "uuid4";
    private static final Resume resume4 = new Resume(UUID_4, "Sidorov Andrei Andreevich");

    private static final String UUID_5 = "uuid5";
    private static final Resume resume5 = new Resume(UUID_5, "Sidorov Andrei Andreevich");

    public AbstractAllStorageTest(final Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void size() {
        sizeForTests(4);
    }

    @Test
    public void clear() {
        storage.clear();
        sizeForTests(0);
    }

    @Test
    public void update() {
        storage.update(resume1);
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void attemptUpdateIfResumeNotExist() {
        storage.get("test");
    }

    @Test
    public void getAllSorted() {
        List<Resume> current = new ArrayList<>();
        current.add(resume1);
        current.add(resume2);
        current.add(resume3);
        current.add(resume4);
        Assert.assertArrayEquals(current.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    public void save() {
        storage.save(resume5);
        sizeForTests(5);
        Assert.assertEquals(resume5, storage.get(resume5.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void attemptSaveIfResumeAlreadyExist() {
        storage.save(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(resume2.getUuid());
        sizeForTests(3);
        storage.get(resume2.getUuid());
    }

    @Test
    public void get() {
        Assert.assertEquals(resume3, storage.get(resume3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    private void sizeForTests(final int size) {
        Assert.assertEquals(size, storage.size());
    }

}
