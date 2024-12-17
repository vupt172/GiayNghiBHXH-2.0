package com.vupt.application;

import com.vupt.application.views.GiayNghiBHXHController;
import com.vupt.application.views.exception.ErrorDialog;
import javafx.application.Application;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication(scanBasePackages = "com.vupt.application")
public class MyApplication extends Application {
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        this.applicationContext = SpringApplication.run(MyApplication.class);
        System.setProperty("java.awt.headless", "false");

    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(ErrorDialog::showError);
        GiayNghiBHXHController.loadView(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
}
