package felix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Felix Tee Yong Thye
 */
@Controller
public class SystemController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemController.class);

    public static final String VIEW_403 = "403";
    public static final String VIEW_SIGNIN = "signin";
    public static final String VIEW_TEST = "test";
    public static final String FORWARD_SECURE_HOME = "forward:/secure/home";

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(VIEW_403);
        return mav;
    }

    @RequestMapping(value = { "/", "/signin" }, method = RequestMethod.GET)
    public ModelAndView defaultPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            //The user is logged in
            mav.setViewName(FORWARD_SECURE_HOME);
            return mav;
        }
        mav.setViewName(VIEW_SIGNIN);
        return mav;
    }

    @RequestMapping(value = { "/test" }, method = RequestMethod.GET)
    public ModelAndView testPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(VIEW_TEST);
        return mav;
    }
}
