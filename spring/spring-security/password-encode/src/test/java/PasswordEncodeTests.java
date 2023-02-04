import encode.PasswordEncodeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author Oliver
 * @date 2023年01月05日 2:20
 */
@SpringBootTest(classes = PasswordEncodeApplication.class)
public class PasswordEncodeTests {

    @Test
    void passwordEncoding() {

        //"1.BCryptPasswordEncoder
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
        //!"2.Pbkdf2PasswordEncoder
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        System.out.println(pbkdf2PasswordEncoder.encode("123"));
        //!"3.SCryptPasswordEncoder !"需要额外引⼊依赖
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        System.out.println(sCryptPasswordEncoder.encode("123"));
        //!"4.Argon2PasswordEncoder !"需要额外引⼊依赖
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
        System.out.println(argon2PasswordEncoder.encode("123"));
    }

}
