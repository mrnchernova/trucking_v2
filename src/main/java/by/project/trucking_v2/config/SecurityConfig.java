package by.project.trucking_v2.config;

import by.project.trucking_v2.model.Permission;
import by.project.trucking_v2.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

private final UserDetailsService userDetailsService;
@Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/registration").permitAll()


//                .antMatchers(HttpMethod.GET, "/users").hasAnyRole(Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET, "/users/**").hasAuthority(Permission.USERS_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/users/**").hasAuthority(Permission.USERS_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority(Permission.USERS_WRITE.getPermission())

//                .antMatchers(HttpMethod.GET, "/orders").hasAnyRole(Role.CLIENT.name())
                .antMatchers(HttpMethod.GET, "/orders").hasAuthority(Permission.USERS_READ.getPermission())


                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/auth/success").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("a")
//                        .password(passwordEncoder().encode("a"))
////                        .roles(Role.ADMINISTRATOR.name())
//                        .authorities(Role.ADMINISTRATOR.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("c")
//                        .password(passwordEncoder().encode("c"))
////                        .roles(Role.CLIENT.name())
//                        .authorities(Role.CLIENT.getAuthorities())
//                        .build()
//        );
//    }

//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }


    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
//https://www.youtube.com/watch?v=7uxROJ1nduk&ab_channel=EugeneSuleimanov