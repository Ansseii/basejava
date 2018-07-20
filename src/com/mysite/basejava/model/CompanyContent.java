package com.mysite.basejava.model;

import java.util.List;
import java.util.Objects;

public class CompanyContent implements Content {

    private final List<Company> companies;

    public CompanyContent(final List<Company> companies) {
        Objects.requireNonNull(companies, "List of companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyContent that = (CompanyContent) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    @Override
    public String toString() {
        return companies.toString();
    }
}
