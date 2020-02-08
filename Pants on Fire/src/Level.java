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
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game). <br/>
 * <br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 *
 * @author Zhaoyin Qin, Yu Huang
 */
public class Level {
    /**
     * This constructor initializes a new Level object, so that the GameEngine
     * can begin calling its update() method to advance the game's play. In the
     * process of this initialization, all of the objects in the current level
     * should be instantiated and initialized to their beginning states.
     *
     * @param randGen
     * is the only Random number generator that should be used
     * throughout this level, by the Level itself and all of the
     * Objects within.
     * @param level
     * is a string that either contains the word "RANDOM", or the
     * contents of a level file that should be loaded and played.
     */

    private Random randGen;
    private Hero hero;
    private Water[] water;
    private ArrayList<Pant> pants;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Fire> fires;

    public Level(Random randGen, String level) {
        this.randGen = randGen;
        // instantiation of a water array with capacity of 8
        water = new Water[8];
        // instantiation of a Pant ArrayList
        pants = new ArrayList<Pant>();
        // instantiation of a Fire ArrayList
        fires = new ArrayList<Fire>();
        // instantiation of a Fireball ArrayList
        fireballs = new ArrayList<Fireball>();
        // check if a level fire should be loaded
        if (!level.equals("RANDOM"))
            loadLevel(level);
        else
            createRandomLevel();
    }

    /**
     * This method creates and runs a new GameEngine with its first Level. Any
     * command line arguments passed into this program are treated as a list of
     * custom level filenames that should be played in a particular order.
     *
     * @param args is the sequence of custom level files to play through.
     */
    public static void main(String[] args) {
        GameEngine.start(null, args);
    }

    /**
     * The GameEngine calls this method repeatedly to update all of the objects
     * within your game, and to enforce all of the rules of your game.
     *
     * @param time is the time in milliseconds that have elapsed since the last
     *             time this method was called. This can be used to control the
     *             speed that objects are moving within your game.
     * @return When this method returns "QUIT" the game will end after a short 3
     * second pause and a message indicating that the player has lost.
     * When this method returns "ADVANCE", a short pause and win message
     * will be followed by the creation of a new level which replaces
     * this one. When this method returns anything else (including
     * "CONTINUE"), the GameEngine will simply continue to call this
     * update() method as usual.
     */
    public String update(int time) {
        // update the position of Hero
        hero.update(time, water);
        // update the position of each Water
        for (int i = 0; i < water.length; i++)
            if (water[i] != null)
                water[i] = water[i].update(time);

        // check if any Pant collides with Fireball
        for (int i = 0; i < pants.size(); i++) {
            Fire fire = pants.get(i).handleFireballCollisions(fireballs);
            // add any new Fire to fires
            if (fire != null) {
                fires.add(fire);
            }
            // draw all existing Pants
            pants.get(i).update(time);
        }

        // check if any Fire collides with Water
        for (int i = 0; i < fires.size(); i++) {
            fires.get(i).handleWaterCollisions(water);
            Fireball fireball = fires.get(i).update(time);
            // add new Fireball to fireballs
            if (fireball != null) {
                fireballs.add(fireball);
            }
        }

        // check if any Fireball collides with Water
        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).handleWaterCollisions(water);
            // update position of Fireball
            fireballs.get(i).update(time);
        }

        // remove all dead Pants
        for (int i = 0; i < pants.size(); i++) {
            if (pants.get(i).shouldRemove()) {
                pants.remove(i);
                i--;
            }
        }

        // remove all dead Fireballs
        for (int i = 0; i < fireballs.size(); i++) {
            if (fireballs.get(i).shouldRemove()) {
                fireballs.remove(i);
                i--;
            }
        }

        // remove all dead Fires
        for (int i = 0; i < fires.size(); i++) {
            if (fires.get(i).shouldRemove()) {
                fires.remove(i);
                i--;
            }
        }

        // if Hero collides with Fireball, game lost
        if (hero.handleFireballCollisions(fireballs))
            return "QUIT";
        // if all Fires are eliminated, game won
        if (fires.isEmpty())
            return "ADVANCE";
        // if all Pants are lost, game lost
        if (pants.isEmpty())
            return "QUIT";
        return "CONTINUE";
    }

    /**
     * This method returns a string of text that will be displayed in the upper
     * left hand corner of the game window. Ultimately this text should convey
     * the number of unburned pants and fires remaining in the level. However,
     * this may also be useful for temporarily displaying messages that help you
     * to debug your game.
     *
     * @return a string of text to be displayed in the upper-left hand corner of
     * the screen by the GameEngine.
     */
    public String getHUDMessage() {
        return "Pants Left: " + pants.size() + "\n" + "Fires Left: " + fires.size();
    }

    /**
     * This method creates a random level consisting of a single Hero centered
     * in the middle of the screen, along with 6 randomly positioned Fires, and
     * 20 randomly positioned Pants.
     */
    public void createRandomLevel() {
        // create a Hero at the center of screen
        hero = new Hero(GameEngine.getWidth() / 2, GameEngine.getHeight() / 2,
                randGen.nextInt(3) + 1);
        // create 20 Pants randomly distributed in the screen
        for (int i = 0; i < 20; i++) {
            pants.add(new Pant(randGen.nextInt(GameEngine.getWidth()),
                    randGen.nextInt(GameEngine.getHeight()), randGen));
        }
        // create 6 Fires randomly distributed in the screen
        for (int i = 0; i < 6; i++) {
            fires.add(new Fire(randGen.nextInt(GameEngine.getWidth()),
                    randGen.nextInt(GameEngine.getHeight()), randGen));
        }
    }

    /**
     * This method initializes the current game according to the Object location
     * descriptions within the level parameter.
     *
     * @param level is a string containing the contents of a custom level file
     *              that is read in by the GameEngine. The contents of this file
     *              are then passed to Level through its Constructor, and then
     *              passed from there to here when a custom level is loaded. You
     *              can see the text within these level files by dragging them
     *              onto the code editing view in Eclipse, or by printing out the
     *              contents of this level parameter. Try looking through a few of
     *              the provided level files to see how they are formatted. The
     *              first line is always the "ControlType: #" where # is either 1,
     *              2, or 3. Subsequent lines describe an object TYPE, along with
     *              an X and Y position, formatted as: "TYPE @ X, Y". This method
     *              should instantiate and initialize a new object of the correct
     *              type and at the correct position for each such line in the
     *              level String.
     */
    public void loadLevel(String level) {
        // create a Scanner that produces values scanned from level
        Scanner scan = new Scanner(level);
        // store controlType
        int controlType = 0;
        // keep scanning if there is another line in the input of this scanner
        while (scan.hasNextLine()) {
            // scan and store a new line
            String line = scan.nextLine();
            // create a Scanner that produces values scanned from line
            Scanner scanLine = new Scanner(line);
            // keep scanning if this scanner has another token in its input
            while (scanLine.hasNext()) {
                // scan and store its next token
                String type = scanLine.next();
                if (type.equals("ControlType:")) {
                    // store the controlType of a level fire
                    controlType = scanLine.nextInt();
                } else if (type.equals("FIRE")) {
                    // scan @
                    scanLine.next();
                    // scan and store x coordinate
                    String x = scanLine.next();
                    float xCoordinate = Float.parseFloat(x.substring(0, x.length() - 1));
                    // scan and store y coordinate
                    float yCoordinate = scanLine.nextFloat();
                    // create a new Fire
                    fires.add(new Fire(xCoordinate, yCoordinate, randGen));
                } else if (type.equals("PANT")) {
                    // scan @
                    scanLine.next();
                    // scan and store x coordinate
                    String x = scanLine.next();
                    float xCoordinate = Float.parseFloat(x.substring(0, x.length() - 1));
                    // scan and store y coordinate
                    float yCoordinate = scanLine.nextFloat();
                    // create a new Pant
                    pants.add(new Pant(xCoordinate, yCoordinate, randGen));
                } else {
                    // scan @
                    scanLine.next();
                    // scan and store x coordinate
                    String x = scanLine.next();
                    float xCoordinate = Float.parseFloat(x.substring(0, x.length() - 1));
                    // scan and store y coordinate
                    float yCoordinate = scanLine.nextFloat();
                    // create a new Hero
                    hero = new Hero(xCoordinate, yCoordinate, controlType);
                }
            }
            scanLine.close();
        }
        scan.close();
    }
}
