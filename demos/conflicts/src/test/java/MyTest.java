import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTest {
    Logger logger = LoggerFactory.getLogger(MyTest.class);

    @Test
    public void shouldLogSomething() {
        logger.info("Hello, Berlin!");
    }
}
