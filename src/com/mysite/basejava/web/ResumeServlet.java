package com.mysite.basejava.web;

import com.mysite.basejava.Config;
import com.mysite.basejava.model.Resume;
import com.mysite.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    //TomCat VM options -Droot="/Users/ansseii"
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("" +
                "<html>\n" +
                " <head>\n" +
                "  <title>" +
                "Таблица" +
                "  </title>\n" +
                " </head>\n" +
                "   <body>\n" +
                "       <table border=\"1\" align=\"\">\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" align=\"center\">All resumes</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>UUID</td>\n" +
                "        <td>Full Name</td>\n" +
                "    </tr>\n");
        fillBody(writer);
        writer.write("" +
                "       </table>\n" +
                "    </body>\n" +
                "</html>");
    }

    private void fillBody(final Writer writer) throws IOException {
        final List<Resume> resumes = Config.get().getStorage().getAllSorted();
        for (Resume resume : resumes) {
            writer.write("" +
                    "<tr>" +
                    " <td>" + resume.getUuid() + "</td>" +
                    " <td>" + resume.getFullName() + "</td>" +
                    "</tr>");
        }
    }
}
