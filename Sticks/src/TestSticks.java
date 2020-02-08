// Title:            Sticks
// Files:            Config.java, Sticks.java, TestSticks.java
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
 * This file contains many testing methods for the Sticks project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the Sticks methods
 * 3) provide examples of creating, accessing and modifying arrays
 * <p>
 * Toward these objectives, the expectation is that part of the
 * grade for the Sticks project is completing the commenting of
 * these TestSticks methods. Specific places are noted with TODO but add
 * any other comments you feel would be useful.
 * <p>
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 */

import java.util.Arrays;

/**
 * This class contains a number of methods for testing methods in the Sticks
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 *
 * @author Jim Williams
 */
public class TestSticks {

    // TestSticks class depends upon Sticks class and calls Sticks methods.
    // But Sticks should not depend in any way upon TestSticks.
    // These constant and method declarations are only for use within this
    // testing class
    private static final int NUM_ACTIONS = Config.MAX_ACTION - Config.MIN_ACTION
            + 1;

    /**
     * This is the main method that runs the various tests. Uncomment the tests
     * when you are ready for them to run.
     *
     * @param args (unused)
     */
    public static void main(String[] args) {

        // Milestone 1
        // Test main by playing to see if output is as specified.

        // Read the tests, see if you can describe what is happening.

        // Milestone 2
        testBasicChooseAction();
        testAiChooseAction();
        testInitializeActionRanking();
        testActionRankingToString();
        testUpdateActionRankingOnLoss();
        testUpdateActionRankingOnWin();
        //
        // Milestone 3
        testCreateAndInitializeStrategyTable();
        testStrategyTableToString();
        testUpdateStrategyTableOnWin();
        testUpdateStrategyTableOnLoss();
        testPlayAiVsAi();
        testTrainAi();

    }

    /**
     * This runs some tests on the basicChooseAction method. 1.
     * basicChooseAction should return 0 when there is no stick. 2.
     * basicChooseAction should return 0 when there is negative stick. 3.
     * basicChooseAction should return Config.MIN_ACTION If there are less than
     * Config.MAX_ACTION sticks remaining. 4. basicChooseAction should return
     * number between Config.MIN_ACTION and Config.MAX_ACTION when there are
     * more than Config.MAX_ACTION sticks remaining. 5. Seed random object.
     * basicChooseAction should return the same sequence of random numbers.
     */
    private static void testBasicChooseAction() {
        boolean error = false;

        // 1.
        // call the basicChooseAction method passing the 0 value
        // for the sticksRemaining parameter which should result in 0 being
        // returned.
        int response = Sticks.basicChooseAction(0);
        if (response != 0) {
            error = true;
            System.out.println("testBasicChooseAction 1: for 0 sticks, "
                    + "response should be 0.");
        }

        // 2.
        response = Sticks.basicChooseAction(-5);
        if (response != 0) {
            error = true;
            System.out.println("testBasicChooseAction 2: for negative sticks, "
                    + "response should be 0.");
        }

        // 3.
        response = Sticks.basicChooseAction(2);
        if (response != 1) {
            error = true;
            System.out.println("testBasicChooseAction 3: for 2 sticks, "
                    + "response should be 1.");
        }

        // 4.
        response = Sticks.basicChooseAction(10);
        if (response < Config.MIN_ACTION || response > Config.MAX_ACTION) {
            error = true;
            System.out.println("testBasicChooseAction 4: for 10 sticks,"
                    + " response should be between " + Config.MIN_ACTION
                    + " or " + Config.MAX_ACTION);
        }

        // 5.
        int[] responses = new int[NUM_ACTIONS];
        Config.RNG.setSeed(123); // set seed to get repeatable "random" values

        // call a bunch of times so there is reasonable chance of seeing an
        // equal
        // distribution.
        for (int i = 0; i < 1000; i++) {
            int action = Sticks.basicChooseAction(10);
            responses[action - Config.MIN_ACTION]++;
        }
        if (responses[0] != 329 || responses[1] != 339 || responses[2] != 332) {
            error = true;
            System.out.println("testBasicChooseAction 5: for seed 123 "
                    + "responses were expected to be [329, 339, 332] "
                    + " but found " + Arrays.toString(responses));

        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testBasicChooseAction: failed");
        } else {
            System.out.println("testBasicChooseAction: passed");
        }
    }

    /**
     * This method runs tests on the aiChooseAction method. 1. aiChooseAction
     * should return 0 when number of stick is less or equal to zero. 2.
     * aiChooseAction should return 0 when actionRanking is null. 3.
     * aiChooseAction should not return a number which is larger than
     * Config.MAX_ACTION or smaller than Config.MIN_ACTION. 4. aiChooseAction
     * should return Config.MAX_ACTION since the possibility of other actions is
     * zero. 5. Seed random object. basicChooseAction should return the same
     * sequence of random numbers.
     */
    private static void testAiChooseAction() {
        boolean error = false;

        // 1.
        int action = Sticks.aiChooseAction(0, null);
        if (action != 0) {
            error = true;
            System.out.println("testAiChooseAction 1: for 0 sticks or null "
                    + "actionRanking, response should be 0.");
        }

        // 2.
        int[] actionRanking = new int[]{1, 100, 0};
        action = Sticks.aiChooseAction(-5, actionRanking);
        if (action != 0) {
            error = true;
            System.out.println("testAiChooseAction 2: for negative sticks,"
                    + " response should be 0.");
        }

        // 3.
        action = Sticks.aiChooseAction(10, actionRanking);
        if (action < Config.MIN_ACTION || action > Config.MAX_ACTION) {
            error = true;
            System.out
                    .println("testAiChooseAction 3: invalid action " + action);
        }

        // 4.
        // create and initialize to 0 an action ranking array
        actionRanking = new int[NUM_ACTIONS];

        // set the highest index to the highest ranking
        // so we expect the MAX_ACTION to be chosen
        actionRanking[actionRanking.length - 1] = 100;

        action = Sticks.aiChooseAction(10, actionRanking);

        if (action != Config.MAX_ACTION) {
            error = true;
            System.out.println("testAiChooseAction 4: expected "
                    + Config.MAX_ACTION + " rather than " + action);
        }

        // 5.
        actionRanking = new int[]{1, 6, 3}; // test for 3 actions
        int[] responses = new int[actionRanking.length];

        // set seed to get repeatable "random" values
        Config.RNG.setSeed(123);

        // call a bunch of times so there is reasonable chance of seeing the
        // expected distribution.
        for (int i = 0; i < 10000; i++) {
            action = Sticks.aiChooseAction(10, actionRanking);
            responses[action - Config.MIN_ACTION]++;
        }
        if (responses[0] != 1037 || responses[1] != 5819
                || responses[2] != 3144) {
            error = true;
            System.out.println("testAiChooseAction 5: for seed 123 "
                    + "responses were expected to be [1037, 5819, 3144] "
                    + " but found " + Arrays.toString(responses));

        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testAiChooseAction: failed");
        } else {
            System.out.println("testAiChooseAction: passed");
        }
    }

    /**
     * This method runs tests on the initializeActionRanking method. 1.
     * initializeActionRanking return when actionRanking is null. 2.
     * initializeActionRanking should assign one to every element in the array
     * no matter what values were stored originally.
     */
    private static void testInitializeActionRanking() {
        boolean error = false;

        // 1.
        // per specification, no error should occur, simply returns.
        Sticks.initializeActionRanking(null);

        // 2.
        // typically actionRankings only contain three elements
        // but the specification for this method said
        // utilize the length of the array and not constants
        // to initialize.
        int[] actionRanking = new int[]{0, 0, 3, -56, 0, 88};

        Sticks.initializeActionRanking(actionRanking);
        for (int i = 0; i < actionRanking.length; i++) {
            if (actionRanking[i] != 1) {
                error = true;
                System.out.println("testInitializeActionRanking 2: index " + i
                        + " has a non-one value: "
                        + Arrays.toString(actionRanking));
            }
        }

        // 3.
        actionRanking = new int[]{0, 0, 0};

        Sticks.initializeActionRanking(actionRanking);
        for (int i = 0; i < actionRanking.length; i++) {
            if (actionRanking[i] != 1) {
                error = true;
                System.out.println("testInitializeActionRanking 3: index " + i
                        + " has a non-one value: "
                        + Arrays.toString(actionRanking));
            }
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testInitializeActionRanking: failed");
        } else {
            System.out.println("testInitializeActionRanking: passed");
        }
    }

    /**
     * This method tests the actionRankingToString method. 1. The returned
     * String should match String expected 2. The returned String should end
     * with a new line character
     */
    private static void testActionRankingToString() {
        boolean error = false;

        // 1.
        int sticksLeft = 10;
        // create and initialize an actionRanking array.
        int[] actionRanking = new int[]{23, 45, 101, 19};

        // call method, passing array, to see if the expected
        // values are returned.
        String out = Sticks.actionRankingToString(sticksLeft, actionRanking);
        String expected = "10\t23,45,101,19\n";
        if (!out.equals(expected)) {
            System.out.println("testActionRankingToString 1: Unexpected "
                    + "output: " + out);
            error = true;
        }

        // 2.
        if (!out.endsWith("\n")) {
            System.out.println("testActionRankingToString 2: output didn't "
                    + "end with \\n");
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testActionRankingToString: failed");
        } else {
            System.out.println("testActionRankingToString: passed");
        }
    }

    /**
     * This method is utilized by other testing routines.
     *
     * @return A randomly picked action between and including Config.MIN_ACTION
     * and Config.MAX_ACTION;
     */
    private static int pickTestingAction() {
        int action;
        action = Config.RNG.nextInt(NUM_ACTIONS) + Config.MIN_ACTION;
        return action;
    }

    /**
     * This method tests the updateActionRankingOnLoss method. 1. Randomly
     * generate an action and assign 2 to be the ranking of this action.
     * updateActionRankingOnLoss should decrement the ranking of this action by
     * 1. The ranking of this action should be 1 after this method is called.
     */
    private static void testUpdateActionRankingOnLoss() {
        boolean error = false;

        // 1.
        int action = pickTestingAction();

        int[] actionRanking = new int[NUM_ACTIONS];

        int actionIndex = action - Config.MIN_ACTION;

        actionRanking[actionIndex] = 2;

        // expect the selected action to be decremented by one
        Sticks.updateActionRankingOnLoss(actionRanking, action);

        if (actionRanking[actionIndex] != 1) {
            System.out.println("testUpdateActionRankingOnLoss 1: Unexpected "
                    + "actionRanking contents: "
                    + Arrays.toString(actionRanking));
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testUpdateActionRankingOnLoss: failed");
        } else {
            System.out.println("testUpdateActionRankingOnLoss: passed");
        }
    }

    /**
     * This method tests the updateActionRankingOnWin method. 1. Randomly
     * generate an action and the ranking of this action is 0.
     * updateActionRankingOnWin should increment the ranking of this action by
     * 1. In this case, updateActionRankingOnWin increase the ranking of this
     * action to 1.
     */
    private static void testUpdateActionRankingOnWin() {
        boolean error = false;

        // 1.
        // select a action to test
        int action = pickTestingAction();

        // allocate an array with one element for each possible action
        int[] actionRanking = new int[NUM_ACTIONS];

        // call method. Should increase action ranking for the specific action
        // by 1
        Sticks.updateActionRankingOnWin(actionRanking, action);

        // calculate index into the actionRanking array
        int index = action - Config.MIN_ACTION;
        if (actionRanking[index] != 1) {

            System.out.println("testUpdateActionRankingOnWin 1: Unexpected "
                    + "actionRanking contents: "
                    + Arrays.toString(actionRanking));
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testUpdateActionRankingOnWin: failed");
        } else {
            System.out.println("testUpdateActionRankingOnWin: passed");
        }
    }

    /**
     * This method tests the createAndInitializeStrategyTable method. 1. The
     * number of rows of strategy table should equal the initial sticks. 2. The
     * number of columns of strategy table should equal the number of actions
     * that player can choose. 3. Test if the element on row 1 column 1 is 1,
     * since all elements in the 2D array should be 1.
     */
    private static void testCreateAndInitializeStrategyTable() {
        boolean error = false;

        // 1.
        int[][] strategyTable = Sticks.createAndInitializeStrategyTable(30);
        if (strategyTable.length != 30) {
            System.out.println("testCreateAndInitializeStrategyTable 1: "
                    + " strategy table expected length was 30, rather than : "
                    + strategyTable.length);
            error = true;
        }

        // 2.
        if (strategyTable[0].length != NUM_ACTIONS) {
            System.out.println("testCreateAndInitializeStrategyTable 2: "
                    + " strategy table expected width was " + NUM_ACTIONS
                    + " , rather than : " + strategyTable[0].length);
            error = true;
        }

        // 3.
        if (strategyTable[0][0] != 1) {
            System.out.println("testCreateAndInitializeStrategyTable 3: "
                    + " strategy table should be initialized to all 1's");
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testCreateAndInitializeStrategyTable: failed");
        } else {
            System.out.println("testCreateAndInitializeStrategyTable: passed");
        }
    }

    /**
     * This method tests the strategyTableToString method. 1. The returned
     * String should match the String expected.
     */
    private static void testStrategyTableToString() {
        boolean error = false;

        // 1.
        int[][] strategyTable = new int[][]{{1, 10, 11}, {4, 6, 2},
                {1, 1, 1}, {6, 3, 1}, {4, 4, 4}, {1, 4, 1}, {1, 5, 1},
                {3, 1, 1}, {1, 1, 7}, {1, 2, 1}};

        String expected = "\nStrategy Table\n" + "Sticks   Rankings\n"
                + "10	1,2,1\n" + "9	1,1,7\n" + "8	3,1,1\n" + "7	1,5,1\n"
                + "6	1,4,1\n" + "5	4,4,4\n" + "4	6,3,1\n" + "3	1,1,1\n"
                + "2	4,6,2\n" + "1	1,10,11\n";

        String str = Sticks.strategyTableToString(strategyTable);
        if (!str.equals(expected)) {
            System.out.println("testStrategyTableToString 1: unexpected output "
                    + " expected: " + expected + "\nwas:" + str);
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testStrategyTableToString: failed");
        } else {
            System.out.println("testStrategyTableToString: passed");
        }
    }

    /**
     * This method tests updateStrategyTableOnWin method. 1. Randomly generate
     * two actions and record the actions in the actionHistory(3 sticks left, 6
     * sticks left). 2. The initial ranking of all action is zero. 3.
     * updateStrategyTableOnWin should increment the ranking of corresponding
     * action by 1. In this case, the ranking should be increased to 1.
     */
    private static void testUpdateStrategyTableOnWin() {
        boolean error = false;

        // 1.
        int numSticks = 12;
        // create a 0 initialized strategy table
        int[][] strategyTable = new int[numSticks][NUM_ACTIONS];

        // create a 0 initialized history table
        int[] actionHistory = new int[numSticks];

        // generate a couple of actions
        int action1 = pickTestingAction();
        int action2 = pickTestingAction();

        // assign those actions to a couple of positions in the history
        actionHistory[2] = action1;
        actionHistory[5] = action2;

        // update the strategy table (currently all 0's)
        // based on the actions.
        Sticks.updateStrategyTableOnWin(strategyTable, actionHistory);

        // determine indexes for the actions
        int action1Index = action1 - Config.MIN_ACTION;
        int action2Index = action2 - Config.MIN_ACTION;

        // check to see if the appropriate elements in strategy table
        // have been updated.
        if (strategyTable[2][action1Index] != 1
                || strategyTable[5][action2Index] != 1) {
            System.out.println("testUpdateStrategyTableOnWin 1: unexpected "
                    + "value in strategy table:"
                    + Sticks.strategyTableToString(strategyTable));
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testUpdateStrategyTableOnWin: failed");
        } else {
            System.out.println("testUpdateStrategyTableOnWin: passed");
        }
    }

    /**
     * This tests the testUpdateStrategyTableOnLoss method. 1. The ranking of
     * all actions is 2. 2. Randomly generate two actions and record the actions
     * in the actionHistory(3 sticks left, 6 sticks left). 3.
     * updateStrategyTableOnLoss should decrement the ranking of corresponding
     * actions by 1. In this case, the actions should be decreased to 1.
     */
    private static void testUpdateStrategyTableOnLoss() {
        boolean error = false;

        // 1.
        int numSticks = 12; // pick a value

        // create a 0 initialized strategy table
        int[][] strategyTable = new int[numSticks][NUM_ACTIONS];

        // initialize every value to 2
        for (int sticksIndex = 0; sticksIndex < strategyTable.length; sticksIndex++) {
            for (int actionIndex = 0; actionIndex < strategyTable[sticksIndex].length; actionIndex++) {
                strategyTable[sticksIndex][actionIndex] = 2;
            }
        }

        // create a 0 initialized history table
        int[] actionHistory = new int[numSticks];

        // generate a couple of actions
        int action1 = pickTestingAction();
        int action2 = pickTestingAction();

        // assign those actions to a couple of positions in the history
        actionHistory[2] = action1;
        actionHistory[5] = action2;

        // update the strategy table (currently all 2's)
        // based on the actions. The action should be to reduce by 1.
        Sticks.updateStrategyTableOnLoss(strategyTable, actionHistory);

        // determine indexes for the actions
        int action1Index = action1 - Config.MIN_ACTION;
        int action2Index = action2 - Config.MIN_ACTION;

        // check to see if the appropriate elements in strategy table
        // have been updated.
        if (strategyTable[2][action1Index] != 1
                || strategyTable[5][action2Index] != 1) {
            System.out.println("testUpdateStrategyTableOnLoss 1: unexpected "
                    + "value in strategy table:"
                    + Sticks.strategyTableToString(strategyTable));
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testUpdateStrategyTableOnLoss: failed");
        } else {
            System.out.println("testUpdateStrategyTableOnLoss: passed");
        }
    }

    /**
     * This method tests the playAiVsAi method. 1. playAiVsAi should return a
     * value of either 1 or 2. 2. the returned value of playAiVsAi should be
     * winner of the game. 3. values of actions in actionHistory should all
     * between Config.Min_ACTION and Config.MAX_ACTION.
     */
    private static void testPlayAiVsAi() {
        boolean error = false;

        // 1.
        int startSticks = 15;

        int[][] p1StrategyTable = new int[startSticks][NUM_ACTIONS];
        int[][] p2StrategyTable = new int[startSticks][NUM_ACTIONS];

        // initialize strategy tables to 1's
        // since they are the same size we will do in the same loops
        for (int sticksIndex = 0; sticksIndex < p1StrategyTable.length; sticksIndex++) {
            for (int actionIndex = 0; actionIndex < p1StrategyTable[sticksIndex].length; actionIndex++) {
                p1StrategyTable[sticksIndex][actionIndex] = 1;
                p2StrategyTable[sticksIndex][actionIndex] = 1;
            }
        }

        // create and initialize action history to 0's
        int[] p1ActionHistory = new int[startSticks];
        int[] p2ActionHistory = new int[startSticks];

        int winner = Sticks.playAiVsAi(startSticks, p1StrategyTable,
                p1ActionHistory, p2StrategyTable, p2ActionHistory);

        // see if return value is valid
        if (winner < 1 || winner > 2) {
            System.out.println("testPlayAiVsAi 1: winner should be 1 or 2, "
                    + "not: " + winner);
            error = true;
        }

        // determine who picked up the last stick
        int lastStick = 0;
        for (int i = p1ActionHistory.length - 1; i >= 0; i--) {
            // see who is the last to pick up a stick
            // they won't both pick up a stick for a particular
            // number of sticks remaining.
            if (p1ActionHistory[i] > 0) {
                lastStick = 1;
            } else if (p2ActionHistory[i] > 0) {
                lastStick = 2;
            }
        }

        if (lastStick >= 1 && lastStick <= 2 && winner == lastStick) {
            System.out.println("testPlayAiVsAi 2: winner should be returned "
                    + "not loser.");
            error = true;
        }

        // see if all values in the history tables are either 0 or
        // between Config.MIN_ACTION and Config.MAX_ACTION.
        for (int i = 0; i < p1ActionHistory.length; i++) {
            int action = p1ActionHistory[i];
            if (action != 0) {
                if (action < Config.MIN_ACTION || action > Config.MAX_ACTION) {
                    System.out.println("testPlayAiVsAi 3: invalid action: "
                            + action + " at p1ActionHistory[" + i + "]");
                    error = true;
                }
            }
        }

        for (int i = 0; i < p2ActionHistory.length; i++) {
            int action = p2ActionHistory[i];
            if (action != 0) {
                if (action < Config.MIN_ACTION || action > Config.MAX_ACTION) {
                    System.out.println("testPlayAiVsAi 4: invalid action: "
                            + action + " at p2ActionHistory[" + i + "]");
                    error = true;
                }
            }
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testPlayAiVsAi: failed");
        } else {
            System.out.println("testPlayAiVsAi: passed");
        }
    }

    /**
     * This method tests the trainAi method. 1. Initial sticks should be larger
     * or equal to Config.MIN_STICKS. 2. trainAi should return null if initial
     * sticks are smaller than Config.MIN_STICKS. 3. The number of rows of
     * strategy table should equal the initial sticks. 4. The number of columns
     * of strategy table should equal the number of actions that player can
     * choose.
     */
    private static void testTrainAi() {
        boolean error = false;

        // 1.
        int startSticks = 5;
        int numberOfGamesToPlay = 5;
        int[][] strategyTable = Sticks.trainAi(startSticks,
                numberOfGamesToPlay);
        if (startSticks < Config.MIN_STICKS && strategyTable != null) {
            System.out
                    .println("testTrainAi 1: expected null strategy table for "
                            + " startSticks:" + startSticks
                            + " less than minimum of: " + Config.MIN_STICKS);
            error = true;
        }

        // 2.
        startSticks = 10;
        numberOfGamesToPlay = 5;
        strategyTable = Sticks.trainAi(startSticks, numberOfGamesToPlay);

        if (strategyTable.length != startSticks) {
            System.out
                    .println("testTrainAi 2: unexpected strategy table length: "
                            + strategyTable.length + " expected: "
                            + startSticks);
            error = true;
        }

        if (strategyTable[0].length != NUM_ACTIONS) {
            System.out
                    .println("testTrainAi 3: unexpected strategy table width: "
                            + strategyTable[0].length + " expected: "
                            + NUM_ACTIONS);
            error = true;
        }

        // can you think of other tests that would be useful?
        // if so, then you can add them.

        if (error) {
            System.out.println("testTrainAi: failed");
        } else {
            System.out.println("testTrainAi: passed");
        }
    }

}
