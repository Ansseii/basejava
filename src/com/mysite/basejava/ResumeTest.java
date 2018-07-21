package com.mysite.basejava;

import com.mysite.basejava.model.*;

import java.time.LocalDate;

public class ResumeTest {

    public static void main(String[] args) {

        Resume resume = new Resume("1", "John Smith");

        resume.addContact(ContactType.MOBILE, "+7 123 4567");
        resume.addContact(ContactType.SKYPE, "JohnSmith");
        resume.addContact(ContactType.MAIL, "JohnSmith@gmail.com");
        resume.addContact(ContactType.LINKED_IN, "https://linkedin.com");
        resume.addContact(ContactType.GITHUB, "https://githib.com");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com");
        resume.addContact(ContactType.HOMEPAGE, "https://JS.com");

        resume.addField(SectionType.OBJECTIVE, new TextContent("my position"));
        resume.addField(SectionType.PERSONAL, new TextContent("my personal information"));
        resume.addField(SectionType.ACHIEVEMENT,
                new ListContent("achievement1", "achievement2", "achievement3"));
        resume.addField(SectionType.QUALIFICATIONS,
                new ListContent("qualification1", "qualification2", "qualification3"));
        resume.addField(SectionType.EXPERIENCE,
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
        resume.addField(SectionType.EDUCATION,
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
