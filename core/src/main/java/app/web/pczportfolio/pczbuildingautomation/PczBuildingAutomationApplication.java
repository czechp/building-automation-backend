package app.web.pczportfolio.pczbuildingautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PczBuildingAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PczBuildingAutomationApplication.class, args);
    }

}
