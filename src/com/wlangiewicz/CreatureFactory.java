package com.wlangiewicz;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Player player = new Player(world, '^', AsciiPanel.brightWhite);
        world.addCreature(player);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }

    public Creature newAlien(int id){
        Alien alien = new Alien(world, '#', AsciiPanel.brightGreen);
        alien.id = id;
        new PlayerAi(alien);
        world.addCreature(alien);
        world.addAtEmptyLocation(alien);
        return alien;
    }

    public Rocket newRocket(Player p){
        Rocket rocket = new Rocket(world, '|', AsciiPanel.brightRed);
        new PlayerAi(rocket);
        //System.out.println("adding new rocket");
        world.addCreature(rocket);
        rocket.setPosition(p);
        return rocket;
    }

    public static AlienRocket newAlienRocket(Alien a, World w){
        AlienRocket ar = new AlienRocket(w, '|', AsciiPanel.brightCyan);
        new PlayerAi(ar);
        w.addCreature(ar);
        ar.setPosition(a);
        return ar;
    }

    public static DeadAlien newDeadAlien(Alien a, World w)
    {
        DeadAlien da = new DeadAlien(a);
        new PlayerAi(da);
        w.addCreature(da);
        da.timestamp = System.currentTimeMillis();
        return da;
    }
}
