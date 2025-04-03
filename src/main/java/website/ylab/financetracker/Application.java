package website.ylab.financetracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Application implements CommandLineRunner {
    Logger logger = Logger.getLogger(Application.class.getName());
    @Override
    public void run(String... args) throws Exception {
        logger.info("Application started");
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
