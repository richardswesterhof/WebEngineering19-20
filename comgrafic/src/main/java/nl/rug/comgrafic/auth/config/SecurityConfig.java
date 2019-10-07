package nl.rug.comgrafic.auth.config;

import nl.rug.comgrafic.auth.controller.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthSuccessHandler authSuccessHandler;
    private DataSource dataSource;

    @Autowired
    public SecurityConfig (BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthSuccessHandler authSuccessHandler,
                           DataSource dataSource
    ) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authSuccessHandler = authSuccessHandler;
        this.dataSource = dataSource;
    }


    @Value ("${spring.queries.users-query}")
    private String usersQuery;

    @Value ("${spring.queries.roles-query}")
    private String rolesQuery;

    //FIXME: this is not working for some reason
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/admin/**").hasAuthority ("ADMIN")
                    .antMatchers("/operator/**").hasAuthority ("OPERATOR")
                    .antMatchers("/upload/**").hasAuthority("PUBLISHER")
                    .anyRequest ().authenticated()
                .and().csrf().disable().formLogin()
                    .loginPage("/login").failureUrl("/login?error=true")
                    .successHandler (authSuccessHandler)
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher ("/logout"))
                    .logoutSuccessUrl("/").and().exceptionHandling();

    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
