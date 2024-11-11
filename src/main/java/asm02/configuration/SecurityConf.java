package asm02.configuration;

import asm02.security.AuthService;
import asm02.security.PasswordEncoderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/profile").authenticated()
                .antMatchers("/user/**").hasRole("JOB_SEEKER")
                .antMatchers("/recruiter/**").hasRole("RECRUITER")
                .antMatchers("/dev/**").permitAll()
                .antMatchers("/static/**", "/public/**").permitAll()
                .antMatchers("/", "/login", "/logout", "/register").permitAll()
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    request.setAttribute("errorMessage", "Access Denied!");
                    request.setAttribute("errorCode", 403);
                    request.setAttribute("errorStatus", "Forbidden");
                    request.setAttribute("errorDetail", "You do not have permission to access this resource.");
                    request.getRequestDispatcher("/error").forward(request, response);
                })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/login-success")).permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new AuthService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderImpl();
    }
}

