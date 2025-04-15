package website.ylab.financetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import website.ylab.audit.annotation.EnableAudit;

@SpringBootApplication
@EnableAudit
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}