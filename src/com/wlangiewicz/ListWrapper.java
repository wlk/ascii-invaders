package com.wlangiewicz;

import asciiPanel.AsciiPanel;

import java.util.ArrayList;

public class ListWrapper {
    private ArrayList<Alien> aliens = new ArrayList<Alien>();
    private ArrayList<Rocket> rockets = new ArrayList<Rocket>();
    private Points points = null;

    public ListWrapper(Points pts)
    {
        points = pts;
    }

    public void addAlien(Alien a)
    {
        aliens.add(a);
    }

    public void addRocket(Rocket r)
    {
        rockets.add(r);
    }

    public int currentAliens(){
        return aliens.size();
    }

    public void draw(AsciiPanel terminal, int left, int top)
    {
        synchronized (terminal)
        {
            for(Alien a : aliens) {
                terminal.write(a.glyph(), a.x - left, a.y - top, a.color());
            }

            for(Rocket r : rockets) {
                terminal.write(r.glyph(), r.x - left, r.y - top, r.color());
            }
        }

    }

    private void handleEndOfScreen(World world){
        //FIXME synchronizacja dostepu do world
        synchronized (world){
        ArrayList<Creature> aliensToRemove = new ArrayList<Creature>();
        for(Alien a: aliens){
            if(a.y >= 20){
                aliensToRemove.add(a);
            }
        }
        aliens.removeAll(aliensToRemove);
        world.removeCreatures(aliensToRemove);

        ArrayList<Creature> rocketsToRemove = new ArrayList<Creature>();
        for(Rocket r: rockets){
            if(r.y <= 0){
                rocketsToRemove.add(r);
            }
        }
        rockets.removeAll(rocketsToRemove);

        world.removeCreatures(rocketsToRemove);
        }
    }

    private Screen handleCollisions() {
        ArrayList<Alien> aliensToRemove = new ArrayList<Alien>();
        ArrayList<Rocket> rocketsToRemove = new ArrayList<Rocket>();
        for(Alien a : aliens){

            Creature c = a.colision();
            if(c != null){    // collision
                //System.out.println("Alien collision");
                if(c instanceof Player){
                    //System.out.println("Alien collision with player");
                    return new LoseScreen();
                }
                else if (c instanceof Rocket){
                    //System.out.println("Alien collision with rocket");
                    aliensToRemove.add(a);
                    rocketsToRemove.add((Rocket)c);
                    points.points += 10 * a.y;
                }
                else if (c instanceof Alien){

                }
            }
        }
        synchronized (aliens) {
            aliens.removeAll(aliensToRemove);
        }
        synchronized (rockets) {
            rockets.removeAll(rocketsToRemove);
        }
        return null;
    }

    public void tick(World world)
    {
        synchronized (aliens) {
            handleEndOfScreen(world);

            for(Alien a : aliens){
                a.move();
            }
            for(Rocket r : rockets){
                r.move();
            }

            Screen s = handleCollisions();
            if(aliens.size() == 0)
            {
                s = new WinScreen(points.points);
            }
            points.screen = s;
            if(s != null)
            {
                ApplicationMain.instance().screen = s;
            }
        }
    }
}
