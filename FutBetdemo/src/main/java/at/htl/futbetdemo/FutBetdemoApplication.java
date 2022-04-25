package at.htl.futbetdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;

@SpringBootApplication
public class FutBetdemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(FutBetdemoApplication.class, args);




    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FutBetdemoApplication.class);
    }
}
