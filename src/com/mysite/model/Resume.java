package com.mysite.model;


import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    public Resume(final String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(final String uuid, final String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName);
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
