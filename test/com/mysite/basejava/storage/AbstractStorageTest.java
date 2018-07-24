package com.mysite.basejava.storage;

import com.mysite.basejava.exception.ExistStorageException;
import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private static final Resume resume1 = new Resume(UUID_1, "Ivanov Ivan Ivanovich");
    private static final Resume resume2 = new Resume(UUID_2, "Petrov Petr Petrovich");
    private static final Resume resume3 = new Resume(UUID_3, "Ivanov Ivan Ivanovich");
    private static final Resume resume4 = new Resume(UUID_4, "Sidorov Andrei Andreevich");
    private static final Resume resume5 = new Resume(UUID_5, "Sidorov Andrei Andreevich");

    static {
        Company company1 = new Company("ООО Рога и копыта", "https://thebestsiteever.com",
                new Company.Period(LocalDate.of(1992, 2, 14),
                        LocalDate.of(2001, 6, 25), "title1", "description1"),
                new Company.Period(LocalDate.of(2001, 6, 26),
                        LocalDate.now(), "title2", "description2")
        );
        Company company2 = new Company("APPLE", "https://apple.com",
                new Company.Period(LocalDate.of(2001, 6, 26),
                        LocalDate.now(), "title3", "description3")
        );

        TextContent objective = new TextContent("my position");
        TextContent personal = new TextContent("my personal information");
        ListContent achievements = new ListContent("achievement1", "achievement2", "achievement3");
        ListContent qualifications = new ListContent("qualification1", "qualification2", "qualification3");
        CompanyContent experience = new CompanyContent(company1, company2);
        CompanyContent education = new CompanyContent(company1, company2);

        resume1.setContact(ContactType.MOBILE, "+7 123 4567");
        resume1.setContact(ContactType.SKYPE, "JohnSmith");
        resume1.setContact(ContactType.MAIL, "JohnSmith@gmail.com");
        resume1.setContact(ContactType.LINKED_IN, "https://linkedin.com");
        resume1.setContact(ContactType.GITHUB, "https://githib.com");
        resume1.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com");
        resume1.setContact(ContactType.HOMEPAGE, "https://JS.com");

        resume1.setSection(SectionType.OBJECTIVE, objective);
        resume1.setSection(SectionType.PERSONAL, personal);
        resume1.setSection(SectionType.ACHIEVEMENT, achievements);
        resume1.setSection(SectionType.QUALIFICATIONS, qualifications);
        resume1.setSection(SectionType.EXPERIENCE, experience);
        resume1.setSection(SectionType.EDUCATION, education);
    }

    public AbstractStorageTest(final Storage storage) {
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
    public void updateIfResumeNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(resume1, resume2, resume3, resume4);
        Collections.sort(expectedResumes);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void save() {
        storage.save(resume5);
        sizeForTests(5);
        Assert.assertEquals(resume5, storage.get(UUID_5));
    }

    @Test(expected = ExistStorageException.class)
    public void saveIfResumeAlreadyExist() {
        storage.save(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        sizeForTests(3);
        storage.get(UUID_2);
    }

    @Test
    public void get() {
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getIfResumeNotExist() {
        storage.get(UUID_5);
    }

    private void sizeForTests(final int size) {
        Assert.assertEquals(size, storage.size());
    }
}
