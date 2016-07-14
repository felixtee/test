package felix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 *
 * @author Felix Tee Yong Thye
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServletInitializer extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(ServletInitializer.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    @Bean
    public FilterRegistrationBean characterEncodingFilterRegistration() {
        LOG.debug("Spring-Bean : characterEncodingFilterRegistration");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addInitParameter("encoding", "UTF-8");
        registration.addUrlPatterns("/*");
        registration.setFilter(filter);
        return registration;
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        LOG.debug("Spring-Bean : dispatcherServlet");
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        LOG.debug("Spring-Bean : dispatcherServletRegistration");
        DispatcherServlet servlet = dispatcherServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setName(AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME);
        registration.addInitParameter("contextConfigLocation", "");
        registration.addUrlMappings("/*");
        registration.setLoadOnStartup(50);
        registration.setServlet(servlet);
        return registration;
    }

}
