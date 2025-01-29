import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MainClassTest {
    @Test
    public void getLocalNumber() {
        MainClass checkLocal = new MainClass();
        if (checkLocal.getLocalNumber() == 14) {
            System.out.println("SUCCESS! getLocalNumber works correctly");
        }
        else {
            Assertions.fail("ERROR! The method getLocalNumber returns a different number, not 14");
        }
    }

    @Test
    public void getClassNumber() {
        MainClass checkClassNumber = new MainClass();
        if (checkClassNumber.getClassNumber() > 45) {
            System.out.println("SUCCESS! checkClassNumber works correctly");
        }
        else {
            Assertions.fail("ERROR! The method checkClassNumber returns a different number, lower than 45");
        }

    }
}
