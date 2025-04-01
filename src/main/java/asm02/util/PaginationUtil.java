package asm02.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PaginationUtil {
    public static String toPage(String absoluteURL,String targetPageNr){
        if (absoluteURL.contains("page=")) {
            return absoluteURL.replaceAll("page=\\d+", "page=" + targetPageNr);
        } else {
            return absoluteURL + (absoluteURL.contains("?") ? "&" : "?") + "page=" + targetPageNr;
        }
    }
    public static String getFullURL(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null)
            url += "?" + queryString;
        return url;
    }
}
