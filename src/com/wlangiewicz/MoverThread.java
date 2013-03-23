package com.wlangiewicz;

import java.util.concurrent.TimeUnit;

public class MoverThread implements Runnable {
    private ListWrapper lists = null;
    private World world;
    public MoverThread(ListWrapper lw, World w)
    {
        lists = lw;
        world = w;
    }

    @Override
    public void run() {
        while(true)
        {
            lists.tick(world);
            try {
                Thread.sleep(30);
                ApplicationMain.instance().repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
