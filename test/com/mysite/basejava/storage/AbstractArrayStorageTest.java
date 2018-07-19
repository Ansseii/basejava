package com.mysite.basejava.storage;


import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(final Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void testOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.valueOf(i)));
            }
        } catch (Exception e) {
            Assert.fail("Storage Overflow");
        }
        storage.save(new Resume("John Smith"));
    }
}