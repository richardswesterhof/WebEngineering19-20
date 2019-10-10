package nl.rug.shamzam.Utils;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;

public class HeaderSetter {

    public static void setContentType(String accept, HttpServletResponse response) {
        accept = Unnullifier.unnullify(accept);

        //return csv if specifically requested, return json otherwise
        if(accept.toLowerCase().equals("text/csv")) {
            response.setHeader(HttpHeaders.CONTENT_TYPE, accept);
        }
        //else the content type will automatically be set to application/json


        System.out.println("Request should be answered in " + accept);
    }
}
