package com.wlangiewicz;

import asciiPanel.AsciiPanel;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: w
 * Date: 3/23/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeadAlien extends Alien {
    public long timestamp = 0;

    public DeadAlien(Alien a) {
        super(a.world, '*', AsciiPanel.brightYellow);
        this.x = a.x;
        this.y = a.y;
    }

    public boolean timeToDie()
    {
        return System.currentTimeMillis() - timestamp > 5000;
    }


}
