package com.wlangiewicz;

public class PlayerAi extends CreatureAi {

    public PlayerAi(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile){

            creature.x = x;
            creature.y = y;

    }
}
