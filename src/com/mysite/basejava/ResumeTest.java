package com.mysite.basejava;

import com.mysite.basejava.model.*;

import java.time.LocalDate;

public class ResumeTest {


    public static void main(String[] args) {

        Company company1 = new Company("ООО Рога и копыта", "https://thebestsiteever.com",
                new Company.Period(LocalDate.of(1992, 2, 14), LocalDate.of(2001, 6, 25),
                        "title1", "description1"),
                new Company.Period(LocalDate.of(2001, 6, 26), LocalDate.now(),
                        "title2", "description2")
        );
        Company company2 = new Company("APPLE", "https://apple.com",
                new Company.Period(LocalDate.of(2001, 6, 26), LocalDate.now(),
                        "title3", "description3")
        );

        TextContent objective = new TextContent("my position");
        TextContent personal = new TextContent("my personal information");
        ListContent achievements = new ListContent("achievement1", "achievement2", "achievement3");
        ListContent qualifications = new ListContent("qualification1", "qualification2", "qualification3");
        CompanyContent experience = new CompanyContent(company1, company2);
        CompanyContent education = new CompanyContent(company1, company2);

        Resume resume = new Resume("1", "John Smith");
        resume.setContact(ContactType.MOBILE, "+7 123 4567");
        resume.setContact(ContactType.SKYPE, "JohnSmith");
        resume.setContact(ContactType.MAIL, "JohnSmith@gmail.com");
        resume.setContact(ContactType.LINKED_IN, "https://linkedin.com");
        resume.setContact(ContactType.GITHUB, "https://githib.com");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com");
        resume.setContact(ContactType.HOMEPAGE, "https://JS.com");

        resume.setSection(SectionType.OBJECTIVE, objective);
        resume.setSection(SectionType.PERSONAL, personal);
        resume.setSection(SectionType.ACHIEVEMENT, achievements);
        resume.setSection(SectionType.QUALIFICATIONS, qualifications);
        resume.setSection(SectionType.EXPERIENCE, experience);
        resume.setSection(SectionType.EDUCATION, education);

        System.out.println(print(resume));
    }

    private static String print(final Resume resume) {
        StringBuilder builder = new StringBuilder();
        for (ContactType type : resume.getAllContacts().keySet()) {
            builder.append(type)
                    .append(" ")
                    .append(resume.getContact(type))
                    .append("\n");
        }
        for (SectionType type : resume.getAllSections().keySet()) {
            builder.append(type)
                    .append(" ")
                    .append(resume.getSection(type))
                    .append("\n");
        }
        return builder.toString();
    }
}
