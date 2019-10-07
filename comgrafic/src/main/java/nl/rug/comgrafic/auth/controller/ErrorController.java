package nl.rug.comgrafic.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping (value = "error", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        System.out.println ("renderErrorPage");

        ModelAndView errorPage = new ModelAndView();

        Object status = httpRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int httpErrorCode = Integer.valueOf(status.toString());
            switch (httpErrorCode) {
                case 400: { // Http Error Code: 400. Bad Request
                    errorPage.setViewName ("errors/400");
                    return errorPage;
                }
                case 403: { // Http Error Code: 403. Forbidden
                    errorPage.setViewName ("errors/403");
                    return errorPage;
                }
                case 404: { // Http Error Code: 404. Resource not found
                    errorPage.setViewName ("errors/404");
                    return errorPage;
                }
                case 500: { // Http Error Code: 500. Internal Server Error
                    errorPage.setViewName ("errors/500");
                    return errorPage;
                }
            }
        }

        // Any other error
        errorPage.setViewName ("errors/error");
        String errorMsg;
        if (status == null)
            errorMsg = "Unknown error";
        else
            errorMsg = status.toString ();
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    @Override
    public String getErrorPath () {
        return "/error";
    }
}