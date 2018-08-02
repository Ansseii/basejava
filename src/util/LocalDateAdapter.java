package util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(final String string) throws Exception {
        return LocalDate.parse(string);
    }

    @Override
    public String marshal(final LocalDate localDate) throws Exception {
        return localDate.toString();
    }
}
