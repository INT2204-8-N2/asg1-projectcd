package Dictionary;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Translate {
    public static String Translate(String t) throws MalformedURLException, IOException, ParseException {
        APIGoogleTranslate translator = new APIGoogleTranslate();
        translator.setSrcLang(APIGoogleTranslate.LANGUAGE.ENGLISH);
        translator.setDestLang(APIGoogleTranslate.LANGUAGE.VIETNAMESE);
        String data = translator.translate(t);
        return data;
    }
}
