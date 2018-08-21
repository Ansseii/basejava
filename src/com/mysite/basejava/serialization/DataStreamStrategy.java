package com.mysite.basejava.serialization;

import com.mysite.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamStrategy implements Strategy {

    private interface WriteElement<T> {
        void write(final T element) throws IOException;
    }

    private interface ReadElement<T> {
        T read() throws IOException;
    }

    private interface CollectionElement {
        void read() throws IOException;
    }

    private void writeDate(final DataOutputStream dataOutputStream, final LocalDate date) throws IOException {
        dataOutputStream.writeInt(date.getYear());
        dataOutputStream.writeInt(date.getMonthValue());
        dataOutputStream.writeInt(date.getDayOfMonth());
    }

    private LocalDate readDate(final DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt());
    }

    private <T> void writeCollection(final DataOutputStream dataOutputStream,
                                     final Collection<T> collection,
                                     final WriteElement<T> writeElement) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T element : collection) {
            writeElement.write(element);
        }
    }

    private void readCollection(final DataInputStream dataInputStream,
                                final CollectionElement collectionElement) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            collectionElement.read();
        }
    }

    private <T> List<T> readList(final DataInputStream dataInputStream, final ReadElement<T> element) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(element.read());
        }
        return list;
    }

    private Content readContent(final DataInputStream dataInputStream, final SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextContent(dataInputStream.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListContent(readList(dataInputStream, dataInputStream::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanyContent(readList(dataInputStream,
                        () -> new Company(new Link(dataInputStream.readUTF(), dataInputStream.readUTF()),
                                readList(dataInputStream,
                                        () -> new Company.Period(readDate(dataInputStream), readDate(dataInputStream),
                                                dataInputStream.readUTF(), dataInputStream.readUTF()
                                        ))
                        )));
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public void toWrite(final Resume resume, final OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            //Contacts
            Map<ContactType, String> contacts = resume.getAllContacts();
            writeCollection(dataOutputStream, contacts.entrySet(), pair -> {
                dataOutputStream.writeUTF(pair.getKey().name());
                dataOutputStream.writeUTF(pair.getValue());
            });
            //Sections
            Map<SectionType, Content> sections = resume.getAllSections();
            writeCollection(dataOutputStream, sections.entrySet(), pair -> {
                SectionType sectionType = pair.getKey();
                Content content = pair.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextContent) content).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dataOutputStream, ((ListContent) content).getContent(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dataOutputStream, ((CompanyContent) content).getCompanies(), company -> {
                            dataOutputStream.writeUTF(company.getLink().getName());
                            dataOutputStream.writeUTF(company.getLink().getUrl());
                            writeCollection(dataOutputStream, ((company.getPeriods())), period -> {
                                writeDate(dataOutputStream, period.getStartDate());
                                writeDate(dataOutputStream, period.getEndDate());
                                dataOutputStream.writeUTF(period.getTitle());
                                dataOutputStream.writeUTF(period.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume toRead(final InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            //Contacts
            readCollection(dataInputStream,
                    () -> resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            //Sections
            readCollection(dataInputStream,
                    () -> {
                        SectionType type = SectionType.valueOf(dataInputStream.readUTF());
                        resume.setSection(type, readContent(dataInputStream, type));
                    });
            return resume;
        }
    }
}
