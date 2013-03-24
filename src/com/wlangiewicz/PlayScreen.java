package com.wlangiewicz;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private ListWrapper lists = null;
    private CreatureFactory creatureFactory;
    private Points points = new Points();
    private static Thread thread = null;

    public static final int NUM_ALIENS = 40;

    public PlayScreen(){
        if(thread != null){
            thread.stop();
            thread = null;
        }
        lists = new ListWrapper(points);
        screenWidth = 80;
        screenHeight = 21;
        createWorld();

        creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
        for(int i=0;i<NUM_ALIENS;++i){
            Creature c = creatureFactory.newAlien(i + 1);
            lists.addAlien((Alien)c);
        }

    }

    private void createWorld(){
        world = new WorldBuilder(80, 21).makeCaves().build();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        synchronized (terminal){
            int left = 0;
            int top = 0;

            displayTiles(terminal, left, top);

            lists.draw(terminal, left, top);

            terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
            terminal.writeCenter("---- Aliens: " + lists.currentAliens() + "/" + NUM_ALIENS +  "  TOTAL POINTS: " + points.points + "  -----", 22);
        }
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(thread == null)        {
            thread = new Thread(new MoverThread(lists, world));
            thread.start();
        }
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LoseScreen(points.points - 1000);
            case KeyEvent.VK_ENTER: return new WinScreen(points.points-1);
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: points.points--; player.moveBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: points.points--; player.moveBy( 1, 0); break;
            case KeyEvent.VK_SPACE: addRocket(); break;
        }
        return this;
    }


    public void addRocket(){
        Rocket r = creatureFactory.newRocket((Player)player);
        lists.addRocket(r);
        points.points--;
    }


}
