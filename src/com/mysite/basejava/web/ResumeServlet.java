package com.mysite.basejava.web;

import com.mysite.basejava.Config;
import com.mysite.basejava.model.*;
import com.mysite.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mysite.basejava.util.DateUtil.parse;

public class ResumeServlet extends HttpServlet {

    //TomCat VM options -Droot="/Users/ansseii"
    private Storage storage;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String uuid = request.getParameter("uuid");
        final String fullName = request.getParameter("fullName");
        final Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isEmpty(value)) {
                resume.getAllContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            final String value = request.getParameter(type.name());
            final String[] values = request.getParameterValues(type.name());
            if (isEmpty(value) && values.length < 2) {
                resume.getAllSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new TextContent(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(type, new ListContent(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> companies = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            final String name = values[i];
                            if (!isEmpty(name)) {
                                List<Company.Period> positions = new ArrayList<>();
                                final String counter = type.name() + i;
                                final String[] startDates = request.getParameterValues(counter + "startDate");
                                final String[] endDates = request.getParameterValues(counter + "endDate");
                                final String[] titles = request.getParameterValues(counter + "title");
                                final String[] descriptions = request.getParameterValues(counter + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!isEmpty(titles[j])) {
                                        positions.add(new Company.Period(parse(startDates[j]), parse(endDates[j]),
                                                titles[j], descriptions[j]));
                                    }
                                }
                                companies.add(new Company(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.setSection(type, new CompanyContent(companies));
                        break;
                }
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) throws ServletException, IOException {
        final String uuid = request.getParameter("uuid");
        final String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private static boolean isEmpty(final String str) {
        return str == null || str.trim().length() == 0;
    }

}
