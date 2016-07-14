package felix;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import felix.enums.UserRoleType;

/**
 *
 * @author Felix Tee Yong Thye
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    protected SecurityProperties security;

    @Autowired
    protected DataSource dataSource;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // @formatter:off
        webSecurity.ignoring()
                .antMatchers("**/static/**")
                .antMatchers("**/favicon.ico")
                .antMatchers("**/css/**")
                .antMatchers("**/js/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/secure/home")
                .failureUrl("/signin?error").permitAll();
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID");
        http.authorizeRequests()
                .antMatchers("**/static/**").permitAll()
                .antMatchers("**/css/**").permitAll()
                .antMatchers("**/js/**").permitAll()
                .antMatchers("**/favicon.ico").permitAll()
                .antMatchers("/secure/**").authenticated()
                .antMatchers("/admin/**").hasAnyAuthority(UserRoleType.ROLE_ADMIN.getRole());
        http.exceptionHandling().accessDeniedPage("/access?error");
        http.csrf().disable();
        // @formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        BCryptPasswordEncoder encoder = passwordEncoder();
        auth.jdbcAuthentication().dataSource(this.dataSource)
                .usersByUsernameQuery(
                        "select id, password, enabled from trt_system_auth_user where lower(id)=lower(?)")
                .authoritiesByUsernameQuery(
                        "select id, user_role from trt_system_auth_user where lower(id)=lower(?)")
                .passwordEncoder(encoder);
        // @formatter:on
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
