
import eng.PasswordEncoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestHasher {
    @Test
    void testEncoder(){
        String password="abcd";
        String real="bcde";
        String hash= PasswordEncoder.hashPassword(password);
        assertEquals(real,hash);

    }
    void testIgnoringNonAlpha(){
        String password="123";
        String hash=PasswordEncoder.hashPassword(password);
        assertEquals(password,hash);
    }
}
