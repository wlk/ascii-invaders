package com.wlangiewicz;


import java.awt.*;

public class Alien extends Creature {
    public int id = 0;

    public Alien(World world, char glyph, Color color) {
        super(world, glyph, color);
    }



    public void setPosition()
    {
        int x;
        int y;

        do {
            x = (int)(world.width()/10 + Math.random() * world.width()*0.8);
            y = (int)(Math.random() * world.height()/2);
        }
        while (false);
        this.x = x;
        this.y = y;
    }

    public void move()
    {
        synchronized (this){
            if(y >= 20){
                moveBy(0,1);
            }
            else {
                int p = (int)(Math.random()*100);
                if(p > 98)
                {
                    int dir = (int)(Math.random()*90);
                    if(dir < 35) //lewo
                    {
                        moveBy(-1,0);
                    }
                    else if(dir < 70)        //prawo
                    {
                        moveBy(1,0);
                    }
                    else       //w dol
                    {
                        moveBy(0,1);
                    }
                }
            }


        }
    }

    public void moveBy(int mx, int my){
        if(! (world.creature(x+mx, y+my) instanceof Alien ) && !(world.creature(x+mx, y+my) instanceof DeadAlien )){
            if(x+mx < world.width() && y+my < world.height()+1 && y+my >= 0 && x+mx >= 0)
            {
                ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
            }
        }

    }

}
