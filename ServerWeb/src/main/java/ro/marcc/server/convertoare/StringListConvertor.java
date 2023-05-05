package ro.marcc.server.convertoare;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class StringListConvertor implements AttributeConverter<List<String>, String> {
    private final String SEPARATOR = ";";
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if(strings!=null) {
            if (strings.size() > 1) {
                String autorizari = "";
                autorizari += String.join(SEPARATOR, strings.toArray(new String[0]));
                return autorizari;

            } else {
                return strings.get(0);
            }
        }else{
            return "";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        List<String> tmpLst = new ArrayList();
        if(string!=null && string.contains(SEPARATOR)) {
            for (String tmpStr:
                 string.split(SEPARATOR)) {
                tmpLst.add(tmpStr);
            }
        }else{
            tmpLst.add(string);
        }
        return tmpLst;
    }
}
