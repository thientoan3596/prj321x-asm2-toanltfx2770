package asm02.util;

import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public static String toPage(String absoluteURL,String targetPageNr){
        if (absoluteURL.contains("page=")) {
            return absoluteURL.replaceAll("page=\\d+", "page=" + targetPageNr);
        } else {
            return absoluteURL + (absoluteURL.contains("?") ? "&" : "?") + "page=" + targetPageNr;
        }
    }
}
