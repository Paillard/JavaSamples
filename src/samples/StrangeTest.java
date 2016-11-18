package samples;

/**
 * @author Treiber Julien
 */
public class StrangeTest {

    private StrangeTest() {
        System.out.println("Creation!");
    }

    { // this is called first! :o
        System.out.println("123456789");
    }

    public static void main(String... args) {
        new StrangeTest();
    }
}
