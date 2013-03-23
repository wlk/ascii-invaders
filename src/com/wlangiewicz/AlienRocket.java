package com.wlangiewicz;


import java.awt.*;

public class AlienRocket extends Creature {
    public AlienRocket(World world, char glyph, Color color) {
        super(world, glyph, color);
    }

    public void setPosition(Alien a)
    {
        this.x = a.x;
        this.y = a.y+1;
    }

    public void move(){
        moveBy(0, 1);
    }
}
