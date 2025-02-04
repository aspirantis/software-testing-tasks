import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MainClassTest {

    MainClass check = new MainClass();

    @Test
    public void getLocalNumber() {
        if (check.getLocalNumber() == 14) {
            System.out.println("SUCCESS! getLocalNumber works correctly");
        }
        else {
            Assertions.fail("ERROR! The method getLocalNumber returns a different number, not 14");
        }
    }

    @Test
    public void getClassNumber() {
        if (check.getClassNumber() > 45) {
            System.out.println("SUCCESS! getClassNumber works correctly");
        }
        else {
            Assertions.fail("ERROR! The method getClassNumber returns a different number, lower than 45");
        }
    }

    @Test
    public void testGetClassString() {
        if (check.getClassString().contains("hello") || check.getClassString().contains("Hello")) {
            System.out.println("SUCCESS! getClassString works correctly");
        }
        else {
            Assertions.fail("ERROR! The method getClassString returns words other than hello or Hello");
        }
    }
}
