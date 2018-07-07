package com.mysite.storage;

import com.mysite.exception.NotExistStorageException;
import com.mysite.exception.StorageException;
import com.mysite.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME4 = new Resume(UUID_4);
    private static final String UUID_5 = "uuid5";
    private static final Resume RESUME5 = new Resume(UUID_5);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
        storage.save(RESUME4);
    }

    @Test
    public void size() throws Exception {
        sizeForTests(4);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        sizeForTests(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(RESUME1, resumes[0]);
        Assert.assertEquals(RESUME2, resumes[1]);
        Assert.assertEquals(RESUME3, resumes[2]);
        Assert.assertEquals(RESUME4, resumes[3]);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME5);
        sizeForTests(5);
        Assert.assertEquals(RESUME5, storage.get(RESUME5.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(RESUME2.getUuid());
        sizeForTests(3);
        storage.get(RESUME2.getUuid());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME1, storage.get(RESUME1.getUuid()));
        Assert.assertEquals(RESUME2, storage.get(RESUME2.getUuid()));
        Assert.assertEquals(RESUME3, storage.get(RESUME3.getUuid()));
    }

    @Test(expected = StorageException.class)
    public void testOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void sizeForTests(final int size) {
        Assert.assertEquals(size, storage.size());
    }
}