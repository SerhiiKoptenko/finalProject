package org.ua.project.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class IsDateAfterToday extends SimpleTagSupport {
    private LocalDate date;

    @Override
    public void doTag() throws IOException, JspException {
        getJspContext().getOut().println(isDateAfterToday(date));
    }

    private boolean isDateAfterToday(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.compareTo(now) > 0;
    }

    public void setDate(LocalDate localDate) {
        this.date = localDate;
    }
}
