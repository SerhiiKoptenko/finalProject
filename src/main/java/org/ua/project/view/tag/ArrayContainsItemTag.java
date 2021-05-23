package org.ua.project.view.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.stream.Stream;

public class ArrayContainsItemTag extends SimpleTagSupport {
    private String[] items;
    private String item;

    @Override
    public void doTag() throws IOException {
        if (Stream.of(items).anyMatch(i -> i.equals(item))) {
            getJspContext().getOut().print("true");
        } else {
            getJspContext().getOut().print("false");
        }
    }
}
