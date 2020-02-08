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

import java.util.ArrayList;

/**
 * This class represents the player's character which is a fire fighter who is
 * able to spray water that extinguishes Fires and Fireballs. They must save as
 * many Pants from burning as possible, and without colliding into any Fireballs
 * in the process.
 *
 * @author Zhaoyin Qin, Yu Huang
 */

public class Hero {

    private Graphic graphic;
    private float speed;
    private int controlType;


    /**
     * This constructor initializes a new instance of Hero at the appropriate
     * location and using the appropriate controlType. This Hero should move
     * with a speed of 0.12 pixels per millisecond.
     *
     * @param x           the x-coordinate of this new Hero's position
     * @param y           the y-coordinate of this new Hero's position
     * @param controlType specifies which control scheme should be used by the
     *                    player to move this hero around: 1, 2, or 3.
     */
    public Hero(float x, float y, int controlType) {
        this.graphic = new Graphic("HERO");
        this.speed = 0.12f;
        this.controlType = controlType;
        graphic.setPosition(x, y);
    }

    /**
     * This is a simple accessor for this object's Graphic, which may be used by
     * other objects to check for collisions.
     *
     * @return a reference to this Hero's Graphic object.
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * This method detects an handles collisions between any active Fireball
     * objects, and the current Hero. When a collision is found, this method
     * returns true to indicate that the player has lost the Game.
     *
     * @param fireballs the ArrayList of Fireballs that should be checked
     *                  against the current Hero's position for collisions.
     * @return true when a Fireball collision is detected, otherwise false.
     */
    public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
        for (int i = 0; i < fireballs.size(); i++) {
            // check if hero collides with Fireball
            if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic()))
                return true;
        }
        return false;
    }

    /**
     * This method is called repeated by the Level to draw and move (based on
     * the current controlType) the Hero, as well as to spray new Water in the
     * direction that this Hero is currently facing.
     *
     * @param time  is the amount of time in milliseconds that has elapsed since
     *              the last time this update was called.
     * @param water the array of Water that the Hero has sprayed in the past,
     *              and if there is an empty (null) element in this array, they
     *              can can add a new Water object to this array by pressing the
     *              appropriate controls.
     */
    public void update(int time, Water[] water) {
        // control Hero based on controlType
        if (this.controlType == 1) {
            keyboardMovementControl(time);
            keyboardDirectionControl();
        } else if (this.controlType == 2) {
            MouseDirectionControl();
            keyboardMovementControl(time);
        } else {
            MouseDirectionControl();
            MouseMovementControl(time);
        }
        graphic.draw();

        // check the keyboard action
        if (GameEngine.isKeyPressed("MOUSE")
                || GameEngine.isKeyPressed("SPACE")) {
            for (int i = 0; i < water.length; i++) {
                if (water[i] != null)
                    continue;
                else {
                    // create water object
                    water[i] = new Water(graphic.getX(), graphic.getY(),
                            graphic.getDirection());
                    break;
                }
            }
        }
    }

    /**
     * The helper method used to control the movement of Hero using keyboard.
     *
     * @param time is the amount of time in milliseconds that has elapsed since
     *             the last time this update was called.
     */
    private void keyboardMovementControl(int time) {
        if (GameEngine.isKeyHeld("A"))
            graphic.setX(graphic.getX() - speed * time);
        else if (GameEngine.isKeyHeld("D"))
            graphic.setX(graphic.getX() + speed * time);
        else if (GameEngine.isKeyHeld("W"))
            graphic.setY(graphic.getY() - speed * time);
        else if (GameEngine.isKeyHeld("S"))
            graphic.setY(graphic.getY() + speed * time);
        else
            return;
    }

    /**
     * The helper method used to control the direction of Hero using keyboard.
     */
    private void keyboardDirectionControl() {
        if (GameEngine.isKeyHeld("A"))
            graphic.setDirection(new Float(Math.PI));
        else if (GameEngine.isKeyHeld("D"))
            graphic.setDirection(0);
        else if (GameEngine.isKeyHeld("W"))
            graphic.setDirection(new Float(Math.PI * 3 / 2));
        else if (GameEngine.isKeyHeld("S"))
            graphic.setDirection(new Float(Math.PI / 2));
        else
            return;
    }

    /**
     * The helper method used to control the movement of Hero using mouse.
     *
     * @param time is the amount of time in milliseconds that has elapsed since
     *             the last time this update was called.
     */
    private void MouseMovementControl(int time) {
        // calculate and check if the distance between mouse and Hero is greater
        // or equal than 20
        if (Math.hypot(graphic.getX() - GameEngine.getMouseX(),
                graphic.getY() - GameEngine.getMouseY()) >= 20)
            graphic.setPosition(
                    graphic.getX() + graphic.getDirectionX() * speed * time,
                    graphic.getY() + graphic.getDirectionY() * speed * time);
    }

    /**
     * The helper method used to control the direction of Hero using mouse.
     */
    private void MouseDirectionControl() {
        graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
    }
}
