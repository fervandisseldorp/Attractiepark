package B2a.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * Add pages that should be accessible without having to be logged in to antMatchers() as a String.
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers(
                            "/",
                            "/resources/**",
                            "/static/**",
                            "/webjars/**",

                            "/_layout",
                            "/attractions",
                            "/attraction/adminAttractionsList",
                            "/attraction/attractionAdmin",
                            "/attraction/attractionChooser",
                            "/attraction/attractionForm",
                            "/attraction/attractionsList",
                            "/attraction/rollercoasterForm",
                            "/attraction/info/**",

                            "/contact",
                            "/home",
                            "/park",
                            "/registration",
                            "/shop"
                        ).permitAll()
                    .antMatchers(
                            "/image/create/**",
                            "/image/delete/**",
                            "/image/edit/**",
                            "/image/index/**",

                            "/user/index/**",
                            "/user/role/**",

                            "/attraction/adminAttractionsList",
                            "/attraction/attractionChooser",

                            "/newsmessage/**"
                        ).access("hasRole('ADMIN')")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}