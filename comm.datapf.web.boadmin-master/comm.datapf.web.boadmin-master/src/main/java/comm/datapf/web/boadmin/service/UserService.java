package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.domain.dto.UserDto;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService {

    public UserDto readUser(String username);
    public PasswordEncoder passwordEncoder();
    void configure(AuthenticationManagerBuilder auth);
}
