package com.mysite.basejava.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Content> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(final String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(final String uuid, final String fullName) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "FullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getAllContacts() {
        return contacts;
    }

    public Map<SectionType, Content> getAllSections() {
        return sections;
    }

    public String getContact(final ContactType contact) {
        return contacts.get(contact);
    }

    public Content getSection(final SectionType section) {
        return sections.get(section);
    }

    public void setContact(final ContactType type, final String contact) {
        contacts.put(type, contact);
    }

    public void setSection(final SectionType type, final Content content) {
        sections.put(type, content);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + " Name: " + fullName;
    }

    @Override
    public int compareTo(final Resume resume) {
        int compare = fullName.compareTo(resume.getFullName());
        return compare != 0 ? compare : uuid.compareTo(resume.getUuid());
    }
}
