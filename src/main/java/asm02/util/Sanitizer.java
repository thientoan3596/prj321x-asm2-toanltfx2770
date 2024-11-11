package asm02.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class Sanitizer {
    public static String sanitizeContent(String content){
        return Jsoup.clean(content,whiteList());
    }
    public static Safelist whiteList() {
        return new Safelist()
                .addTags("strong", "i", "u", "s", "code", "sub", "sup", "p", "a", "ul", "ol", "li")
                .addAttributes("a", "href")
                .addProtocols("a", "href", "http", "https");
    }
    public static String sanitizeQuery(String original){
        return original.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
