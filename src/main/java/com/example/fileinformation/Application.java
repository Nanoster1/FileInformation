package com.example.fileinformation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.fileinformation.modules.Module;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

@SpringBootApplication
public class Application {
    
    private final Collection<Module> modules;
    
    public Application(Collection<Module> modules) {
        this.modules = modules;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner Runner(ApplicationContext ctx) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите путь к файлу: ");
            String path = scanner.nextLine();
            File file = new File(path);
            for (Module module : modules) {
                if (module.isSuitableExtension(file)) {
                    applyModule(module, file);
                }
            }
        };
    }

    public void applyModule(Module module, File file) {
        System.out.print("Описание: ");
        System.out.println(module.getDesc());
        module.execCommand(file);
    }
}
