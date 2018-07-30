package com.mysite.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CompanyContent implements Content, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Company> companies;

    public CompanyContent(final Company... company) {
        companies = new LinkedList<>(Arrays.asList(company));
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
