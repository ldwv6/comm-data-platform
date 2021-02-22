package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.domain.dao.UserDao;
import comm.datapf.web.boadmin.domain.dto.UserDto;
import comm.datapf.web.boadmin.security.UserPasswordManager;
import comm.datapf.web.boadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserPasswordManager userPasswordManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//    	 System.out.println(">>>>>>>>>>>>>>>>>>>  username :" + username  );

        UserDto user = userDao.readUser(username);

//         System.out.println(">>>>>>>>>>>>>>>>>>>  user.getUsername() :" + user.getUsername()  );

//         user.setAuthorities(userMapper.readAuthority(user.getUsername()));

        user.setAuthorities(getAuthorities(user.getUsername()));

//         System.out.println(">>>>>>>>>>>>>>>>>>>  user.getAuthorities() :" + user.getAuthorities());

        return user;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<String> string_authorities = userDao.readAuthority(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String authority : string_authorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }

    @Override
    public UserDto readUser(String username) {

        UserDto user = userDao.readUser(username);
//         user.setAuthorities(userMapper.readAuthority(username));
        return user;
    }


    @Override
    public PasswordEncoder passwordEncoder() {

        return userPasswordManager;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {


    }
}
