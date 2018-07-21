package com.mysite.basejava;

import com.mysite.basejava.model.*;

import java.time.LocalDate;

public class ResumeTest {

    public static void main(String[] args) {

        Resume resume = new Resume("1", "John Smith");

        resume.setContact(ContactType.MOBILE, "+7 123 4567");
        resume.setContact(ContactType.SKYPE, "JohnSmith");
        resume.setContact(ContactType.MAIL, "JohnSmith@gmail.com");
        resume.setContact(ContactType.LINKED_IN, "https://linkedin.com");
        resume.setContact(ContactType.GITHUB, "https://githib.com");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com");
        resume.setContact(ContactType.HOMEPAGE, "https://JS.com");

        resume.setField(SectionType.OBJECTIVE, new TextContent("my position"));
        resume.setField(SectionType.PERSONAL, new TextContent("my personal information"));
        resume.setField(SectionType.ACHIEVEMENT,
                new ListContent("achievement1", "achievement2", "achievement3"));
        resume.setField(SectionType.QUALIFICATIONS,
                new ListContent("qualification1", "qualification2", "qualification3"));
        resume.setField(SectionType.EXPERIENCE,
                new CompanyContent(
                        new Company(
                                "ООО Рога и копыта",
                                "https://thebestsiteever.com",
                                LocalDate.of(1992, 2, 14),
                                LocalDate.of(2001, 6, 25),
                                "title1",
                                "description1"),
                        new Company(
                                "APPLE",
                                "https://apple.com",
                                LocalDate.of(2001, 6, 26),
                                LocalDate.now(),
                                "title2",
                                "description2")));
        resume.setField(SectionType.EDUCATION,
                new CompanyContent(
                        new Company(
                                "ООО Рога и копыта",
                                "https://thebestsiteever.com",
                                LocalDate.of(1992, 2, 14),
                                LocalDate.of(2001, 6, 25),
                                "title1",
                                "description1"),
                        new Company(
                                "APPLE",
                                "https://apple.com",
                                LocalDate.of(2001, 6, 26),
                                LocalDate.now(),
                                "title2",
                                "description2")));


        System.out.println(resume);
    }
}
