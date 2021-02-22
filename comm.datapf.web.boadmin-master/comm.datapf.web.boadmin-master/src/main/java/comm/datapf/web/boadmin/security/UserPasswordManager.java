package comm.datapf.web.boadmin.security;

import comm.datapf.web.boadmin.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserPasswordManager implements PasswordEncoder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String encode(CharSequence rawPassword) {

        try {

            String pwd_enc = SecurityUtils.convertToHash(rawPassword.toString());
            //String pwd_enc = rawPassword.toString();

            return pwd_enc;

        } catch (Exception e) {
            logger.error("UserPasswordManager encodePassword Exception " + e);
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean checkPwd = false;

        try {
            checkPwd = encodedPassword.matches(SecurityUtils.convertToHash(rawPassword.toString()));
            //checkPwd = encodedPassword.matches(rawPassword.toString());
        } catch (Exception e) {
            logger.error("UserPasswordManager isPasswordValid Exception " + e);
        }
        return checkPwd;
    }
}
