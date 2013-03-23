package com.wlangiewicz;

import java.awt.*;

public class Rocket extends Creature {

    public Rocket(World world, char glyph, Color color) {
        super(world, glyph, color);
    }

    public void setPosition(Player p)
    {
        this.x = p.x;
        this.y = p.y-1;
    }

    public void move(){
        moveBy(0, -1);
    }
}
