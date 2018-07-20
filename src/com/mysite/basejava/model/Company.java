package com.mysite.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {

    private final String url;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public Company(final String url, final LocalDate startDate, final LocalDate endDate, final String description) {
        Objects.requireNonNull(url, "Url must not be null");
        Objects.requireNonNull(startDate, "StartDate must not be null");
        Objects.requireNonNull(endDate, "EndDate must not be null");
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(url, company.url) &&
                Objects.equals(startDate, company.startDate) &&
                Objects.equals(endDate, company.endDate) &&
                Objects.equals(description, company.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, startDate, endDate, description);
    }

    @Override
    public String toString() {
        return "Company{" +
                "url='" + url + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
