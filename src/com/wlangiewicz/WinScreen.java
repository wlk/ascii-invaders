package com.wlangiewicz;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {
    int score = 0;
    public WinScreen(int pts)
    {
        score = pts;
    }
    public void displayOutput(AsciiPanel terminal) {

        int n = 6;
        List<String> introLines;
        try {
            introLines = readFile();
            for (String line: introLines){
                terminal.write(line, 5, n++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        terminal.writeCenter("You won. Score: " + score, 18);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }

    private List<String> readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/com/wlangiewicz/intro.txt")));
        String line;
        ArrayList<String> result = new ArrayList<String>();
        while((line = reader.readLine()) != null){
            result.add(line);
        }
        return result;
    }
}