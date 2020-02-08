// Title:            Pants on Fire
// Files:            Level.java, Fire.java, Fireball.java, Hero.java, Pant.java
//					 Water.java
// Semester:         (302) Summer 2016
//
// Author:           Zhaoyin Qin
// Email:            zqin23@wisc.edu
// CS Login:         zhaoyin
// Lecturer's Name:  Jim Williams
// Lab Section:      322
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     Yu Huang
//Partner Email:     yhuang299@wisc.edu
// Partner CS Login: yhuang
// Lecturer's Name:  Gary Dahl
// Lab Section:      345
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _X_ Write-up states that Pair Programming is allowed for this assignment.
//    _X_ We have both read the CS302 Pair Programming policy.
//    _X_ We have registered our team prior to the team registration deadline.
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;

/**
 * This class represents a fire that is burning, which ejects a Fireball in a
 * random direction every 3-6 seconds. This fire can slowly be extinguished
 * through repeated collisions with water.
 *
 * @author Zhaoyin Qin, Yu Huang
 */

public class Fire {
    private Graphic graphic;
    private Random randGen;
    private int fireballCountdown;
    private int heat;

    /**
     * This constructor initializes a new instance of Fire at the appropriate
     * location and with the appropriate amount of heat. The Random number
     * generator should be used both to determine how much time remains before
     * the next Fireball is propelled, and the random direction it is shot in.
     *
     * @param x       the x-coordinate of this new Fire's position
     * @param y       the y-coordinate of this new Fire's position
     * @param randGen a Random number generator to determine when and in which
     *                direction new Fireballs are created and launched.
     */
    public Fire(float x, float y, Random randGen) {
        // create a Fire Graphic object
        this.graphic = new Graphic("FIRE");
        this.randGen = randGen;
        this.fireballCountdown = randGen.nextInt(3001) + 3000;
        this.heat = 40;
        graphic.setPosition(x, y);
    }

    /**
     * This is a simple accessor for this object's Graphic, which may be used by
     * other objects to check for collisions.
     *
     * @return a reference to this Fire's Graphic object.
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * This method detects and handles collisions between any active (!= null)
     * Water objects, and the current Fire. When a collision is found, the
     * colliding water should be removed, and this Fire's heat should be
     * decremented by 1. If this Fire's heat dips below one, then it should no
     * longer be drawn to the screen, eject new Fireballs, or collide with Water
     * and its shouldRemove() method should start returning true.
     *
     * @param water is the Array of water objects that have been launched by the
     *              Hero (ignore any null references within this array).
     */
    public void handleWaterCollisions(Water[] water) {
        for (int i = 0; i < water.length; i++) {
            if (water[i] != null) {
                // if water collides with fire
                if (this.graphic.isCollidingWith(water[i].getGraphic())) {
                    // decrement heat by 1
                    heat--;
                    // remove water
                    water[i] = null;
                }
            }
        }
    }

    /**
     * This method should return false until this Fire's heat drops down to 0 or
     * less. After that it should begin to return true instead.
     *
     * @return false when this Fire's heat is greater than zero, otherwise true.
     */
    public boolean shouldRemove() {
        return (heat < 1);
    }

    /**
     * This method is called repeatedly by the Level to draw and occasionally
     * launch a new Fireball in a random direction.
     *
     * @param time is the amount of time in milliseconds that has elapsed since
     *             the last time this update was called.
     * @return null unless a new Fireball was just created and launched. In that
     * case, a reference to that new Fireball is returned instead.
     */
    public Fireball update(int time) {
        if (heat >= 1) {
            // check countdown value
            if (this.fireballCountdown <= 0) {
                // reset countdown value
                this.fireballCountdown = randGen.nextInt(3001) + 300;
                // create a Fireball
                return new Fireball(graphic.getX(), graphic.getY(),
                        randGen.nextFloat() * new Float(Math.PI * 2));
            } else {
                graphic.draw();
                // decrement countdown value
                this.fireballCountdown -= time;
                return null;
            }
        }
        return null;
    }
}
