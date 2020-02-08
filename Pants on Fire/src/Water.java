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

/**
 * This Water class represents a splash of Water that is sprayed by the Hero
 * to extinguish Fireballs and Fires, as they attempt to save the Pants.
 *
 * @author Zhaoyin Qin, Yu Huang
 */

public class Water {

    private Graphic graphic;
    private float speed;
    private float distanceTraveled;

    /**
     * This constructor initializes a new instance of Water at the specified
     * location and facing a specific movement direction. This Water should
     * move with a speed of 0.7 pixels per millisecond.
     *
     * @param x         the x-coordinate of this new Water's position
     * @param y         the y-coordinate of this new Water's position
     * @param direction the angle (in radians) from 0 to 2pi that this new Water
     *                  should be both oriented and moving according to.
     */
    public Water(float x, float y, float direction) {
        this.graphic = new Graphic("WATER");
        // set the speed of Water
        this.speed = 0.7f;
        graphic.setPosition(x, y);
        graphic.setDirection(direction);
    }

    /**
     * This is a simple accessor for this object's Graphic, which may be used
     * by other objects to check for collisions.
     *
     * @return a reference to this Water's Graphic object.
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * This method is called repeatedly by the Game to draw and move the current
     * Water. After this Water has moved a total of 200 pixels or further, it
     * should stop displaying itself and this method should return null instead
     * of a reference to the current instance of a Water object.
     *
     * @param time is the amount of time in milliseconds that has elapsed since
     *             the last time this update was called.
     * @return a reference to this Water object until this water has traveled
     * 200 or more pixels. It should then return null after traveling this far.
     */
    public Water update(int time) {
        // check the distance of water moved
        if (distanceTraveled <= 200) {
            // draw the water
            graphic.draw();
            // update the position of water
            distanceTraveled += speed * time;
            graphic.setPosition(
                    graphic.getX() + graphic.getDirectionX() * speed * time,
                    graphic.getY() + graphic.getDirectionY() * speed * time);
            return this;
        } else
            return null;
    }
}
