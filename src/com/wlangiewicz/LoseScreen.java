package com.wlangiewicz;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {
    int score = 0;
    public LoseScreen(int pts)
    {
        score = pts;
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You lost :( only " + score + " points", 10);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}