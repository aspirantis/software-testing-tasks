import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MainClassTest {
    @Test
    public void getLocalNumber() {
        MainClass check = new MainClass();
        if (check.getLocalNumber() == 14) {
            System.out.println("SUCCESS! getLocalNumber works correctly");
        }
        else {
            Assertions.fail("ERROR! The method returns a different number, not 14");
        }
    }
}
