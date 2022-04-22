package at.htl.futbetdemo;

import at.htl.futbetdemo.database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FutBetdemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FutBetdemoApplication.class, args);
        //Database database = Database.getInstance();
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FutBetdemoApplication.class);
    }
}
