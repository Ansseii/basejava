package com.mysite.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Link link;
    private final List<Period> periods = new LinkedList<>();

    public Company(final String siteName, final String url, final Period... period) {
        periods.addAll(Arrays.asList(period));
        this.link = new Link(siteName, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(link, company.link) &&
                Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {

        return Objects.hash(link, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "link=" + link +
                ", periods=" + periods +
                '}';
    }

    public static class Period implements Serializable {

        private static final long serialVersionUID = 1L;

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Period(final LocalDate startDate, final LocalDate endDate,
                      final String title, final String description) {
            Objects.requireNonNull(startDate, "StartDate must not be null");
            Objects.requireNonNull(endDate, "EndDate must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(startDate, period.startDate) &&
                    Objects.equals(endDate, period.endDate) &&
                    Objects.equals(title, period.title) &&
                    Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
