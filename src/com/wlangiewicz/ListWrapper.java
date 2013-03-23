package com.wlangiewicz;

import asciiPanel.AsciiPanel;

import java.util.ArrayList;

public class ListWrapper {
    private int tickCount = 0;

    private ArrayList<Alien> aliens = new ArrayList<Alien>();
    private ArrayList<Rocket> rockets = new ArrayList<Rocket>();
    private ArrayList<DeadAlien> trupy = new ArrayList<DeadAlien>();
    private ArrayList<AlienRocket> alienRockets = new ArrayList<AlienRocket>();

    private Points points = null;

    public ListWrapper(Points pts) {
        points = pts;
    }

    public void addAlien(Alien a) {
        aliens.add(a);
    }

    public void addRocket(Rocket r) {
        rockets.add(r);
    }

    public void addAlienRocket(AlienRocket ar) {
        alienRockets.add(ar);
    }

    public int currentAliens() {
        return aliens.size();
    }

    public void draw(AsciiPanel terminal, int left, int top) {
        synchronized (terminal) {
            for (Alien a : aliens) {
                terminal.write(a.glyph(), a.x - left, a.y - top, a.color());
            }

            for (Rocket r : rockets) {
                terminal.write(r.glyph(), r.x - left, r.y - top, r.color());
            }

            for (DeadAlien da : trupy) {
                terminal.write(da.glyph(), da.x - left, da.y - top, da.color());
            }

            for (AlienRocket ar : alienRockets) {
                terminal.write(ar.glyph(), ar.x - left, ar.y - top, ar.color());
            }
        }

    }


    private void handleEndOfScreen(World world) {
        //FIXME synchronizacja dostepu do world
        synchronized (world) {
            ArrayList<Creature> aliensToRemove = new ArrayList<Creature>();
            for (Alien a : aliens) {
                if (a.y >= 20) {
                    aliensToRemove.add(a);
                }
            }
            aliens.removeAll(aliensToRemove);
            world.removeCreatures(aliensToRemove);

            ArrayList<Creature> rocketsToRemove = new ArrayList<Creature>();
            for (Rocket r : rockets) {
                if (r.y <= 0) {
                    rocketsToRemove.add(r);
                }
            }
            rockets.removeAll(rocketsToRemove);
            world.removeCreatures(rocketsToRemove);

            ArrayList<Creature> alienRocketsToRemove = new ArrayList<Creature>();
            for (AlienRocket r : alienRockets) {
                if (r.y >= 20) {
                    alienRocketsToRemove.add(r);
                }
            }
            alienRockets.removeAll(alienRocketsToRemove);
            world.removeCreatures(alienRocketsToRemove);

        }
    }

    private Screen handleCollisions(World w) {
        ArrayList<Alien> aliensToRemove = new ArrayList<Alien>();
        ArrayList<Rocket> rocketsToRemove = new ArrayList<Rocket>();
        for (Alien a : aliens) {
            Creature c = a.colision();
            if (c != null) {    // collision
                //System.out.println("Alien collision");
                if (c instanceof Player) {
                    //System.out.println("Alien collision with player");
                    return new LoseScreen(points.points - 1000);
                } else if (c instanceof Rocket) {
                    //System.out.println("Alien collision with rocket");
                    aliensToRemove.add(a);
                    rocketsToRemove.add((Rocket) c);
                    points.points += 10 * a.y;
                    trupy.add(CreatureFactory.newDeadAlien(a, w));
                } else if (c instanceof Alien) {

                }
            }
        }

        ArrayList<AlienRocket> alienRocketsToRemove = new ArrayList<AlienRocket>();
        ArrayList<Rocket> rocketsToRemove2 = new ArrayList<Rocket>();

        for (AlienRocket ar : alienRockets) {
            Creature c = ar.colision();
            if (c != null) {    // collision
                if (c instanceof Player) {
                    //System.out.println("Alien collision with player");
                    return new LoseScreen(points.points - 1000);
                } else if (c instanceof Rocket) {
                    alienRocketsToRemove.add(ar);
                    rocketsToRemove2.add((Rocket)c);
                    points.points += 13 * ar.y;
                }
            }
        }
        alienRockets.removeAll(alienRocketsToRemove);

        synchronized (aliens) {
            aliens.removeAll(aliensToRemove);
        }
        synchronized (rockets) {
            rockets.removeAll(rocketsToRemove);
            rockets.removeAll(rocketsToRemove2);
        }
        return null;
    }

    private void addAlienRocket(World w, Alien a) {
        //alien shooting
        int p = (int) (Math.random() * 100 * 100);
        if (p > 99.968 * 100) {
            AlienRocket ar = CreatureFactory.newAlienRocket(a, w);
            alienRockets.add(ar);
        }
    }

    public void tick(World world) {
        synchronized (aliens) {
            handleEndOfScreen(world);

            for (Alien a : aliens) {
                a.move();
                addAlienRocket(world, a);
            }
            for (Rocket r : rockets) {
                r.move();
            }
            if ((++tickCount % 10) == 0) {
                for (AlienRocket ar : alienRockets) {

                    ar.move();
                }
            }


            Screen s = handleCollisions(world);
            if (aliens.size() == 0) {
                s = new WinScreen(points.points);
            }
            points.screen = s;
            if (s != null) {
                ApplicationMain.instance().screen = s;
            }
        }
        ArrayList<DeadAlien> toKill = new ArrayList<DeadAlien>();
        for (DeadAlien da : trupy) {
            if (da.timeToDie()) {
                toKill.add(da);
            }
        }
        trupy.removeAll(toKill);
    }
}
