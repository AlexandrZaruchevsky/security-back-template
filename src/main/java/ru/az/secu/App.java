package ru.az.secu;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(App.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
