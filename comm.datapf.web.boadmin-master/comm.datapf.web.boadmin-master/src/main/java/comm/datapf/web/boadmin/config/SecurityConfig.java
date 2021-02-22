package comm.datapf.web.boadmin.config;

import comm.datapf.web.boadmin.security.LoginFailHandler;
import comm.datapf.web.boadmin.security.LoginSuccessHandler;
import comm.datapf.web.boadmin.security.UserPasswordManager;
import comm.datapf.web.boadmin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    private final UserPasswordManager userPasswordManager;

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/static/**");
        //web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);
        http.exceptionHandling().accessDeniedPage("/login");
        http.sessionManagement().maximumSessions(1).expiredUrl("/logout").maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
        http.sessionManagement().sessionFixation().newSession();

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/static/**").permitAll()
                //.antMatchers("/static/**", "/static/css/**", "/static/js/**", "/static/img/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/findPwd").permitAll()
                .antMatchers("/testUrl").permitAll() //2018-06-28 Damo처리 때문에 임시 제외
                .antMatchers("logout").permitAll()
                .antMatchers("/*").authenticated()
                //.antMatchers("/login").authenticated()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
        ;

        http.formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler()).usernameParameter("username").passwordParameter("password").failureHandler(failHandler())
        ;

        http.logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
        ;

        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return userPasswordManager;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }


    @Bean
    public AuthenticationFailureHandler failHandler() {
        return new LoginFailHandler();
    }

}