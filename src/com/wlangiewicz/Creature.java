package com.wlangiewicz;

import java.awt.*;

public class Creature {
    protected World world;

    public int x;
    public int y;

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    public Creature colision(){
        for (Creature c : world.creatures){
            if (c.x == x && c.y == y && c != this)
                return c;
        }
        return null;
    }

    protected CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    public void setGlyph(char c){
        glyph = c;
    }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }

    public void moveBy(int mx, int my){
        if(x+mx < world.width() && y+my < world.height() && y+my >= 0 && x+mx >= 0)
        {
            ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
        }
    }
}
