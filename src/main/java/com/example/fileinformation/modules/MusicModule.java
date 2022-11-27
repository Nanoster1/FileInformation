package com.example.fileinformation.modules;

import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

@Component
public class MusicModule extends Module {
    @Override
    public boolean isSuitableExtension(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        return extension != null && extension.equals("mp3");
    }
    
    @Override
    public String getDesc() {
        return "Выводит название качового трека. ЕЕЕЕ роооок. Я рокер";
    }
    
    @Override
    public void execCommand(File file) {
        printName(file);
    }

    private void printName(File file) {
        System.out.println(file.getName());
    }
}
