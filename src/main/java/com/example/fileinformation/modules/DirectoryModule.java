package com.example.fileinformation.modules;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryModule extends Module {
    @Override
    public boolean isSuitableExtension(File file) {
        return file.isDirectory();
    }
    
    @Override
    public String getDesc() {
        return "Выводит список файлов";
    }
    
    @Override
    public void execCommand(File file) {
        printFiles(file);
    }

    private void printFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}
