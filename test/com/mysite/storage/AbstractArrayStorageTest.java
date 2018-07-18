package com.mysite.storage;


import com.mysite.exception.StorageException;
import com.mysite.model.Resume;
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