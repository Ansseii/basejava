package com.mysite.basejava.model;


import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Content> fields = new EnumMap<>(SectionType.class);

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

    public String getContacts(final ContactType type) {
        return contacts.get(type);
    }

    public Content getFields(final SectionType type) {
        return fields.get(type);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(fields, resume.fields);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName, contacts, fields);
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
