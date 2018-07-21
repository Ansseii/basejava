package com.mysite.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {

    private final Link link;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Company(final String siteName, final String url, final LocalDate startDate,
                   final LocalDate endDate, final String title, final String description) {
        Objects.requireNonNull(startDate, "StartDate must not be null");
        Objects.requireNonNull(endDate, "EndDate must not be null");
        this.link = new Link(siteName, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(link, company.link) &&
                Objects.equals(startDate, company.startDate) &&
                Objects.equals(endDate, company.endDate) &&
                Objects.equals(title, company.title) &&
                Objects.equals(description, company.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(link, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append(link)
                .append("             ")
                .append(title)
                .append("\n")
                .append(startDate)
                .append(" - ")
                .append(endDate)
                .append("             ")
                .append(description);

        return builder.toString();
    }
}
