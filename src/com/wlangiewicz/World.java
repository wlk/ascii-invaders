package com.wlangiewicz;

import java.awt.Color;
import java.util.ArrayList;

public class World {
    private Tile[][] tiles;
    private int width;
    public ArrayList<Creature> creatures = new ArrayList<Creature>();

    public int width() { return width; }

    private int height;
    public int height() { return height; }

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public void addCreature(Creature c){
          creatures.add(c);
    }

    public void removeCreature(Creature c){
        creatures.remove(c);
    }

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char glyph(int x, int y){
        return tile(x, y).glyph();
    }

    public Creature creature(int x, int y){
        for (Creature c : creatures){
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }

    public Color color(int x, int y){
        return tile(x, y).color();
    }

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;

        if (creature instanceof Player){
            System.out.println("creating player");
            creature.x = width/2;
            creature.y = height-1;
        }
        else if(creature instanceof Alien)
        {
            //System.out.println("creating alien");
            ((Alien)creature).setPosition();
        }

    }

    public void removeCreatures(ArrayList<Creature> creaturesToRemove) {
        creatures.removeAll(creaturesToRemove);
    }
}
