package TestEncoder;

import eng.PasswordEncoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
 class TestHasher {
    @Test
    void testEncoder(){
        String password="abcd";
        String real="bcde";
        String hash= PasswordEncoder.hashPassword(password);
        assertEquals(real,hash);

    }
    @Test
    void testIgnoringNonAlpha(){
        String password="123";
        String hash=PasswordEncoder.hashPassword(password);
        assertEquals(password,hash);
    }
}
