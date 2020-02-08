// Title:            Review Quiz
// Files:            ReviewQuiz.java, P1.jar
// Semester:         (302) Fall 2016
//
// Author:           Zhaoyin Qin
// Email:            zqin23@wisc.edu
// CS Login:         zhaoyin
// Lecturer's Name:  Jim Williams
// Lab Section:      322
//

import java.util.Scanner;

/**
 * Review Quiz program allows its users to submit and answer multiple choice review
 * questions in preparation for the CS302 Exams.
 *
 * @author Zhaoyin Qin
 */

public class ReviewQuiz {

    /**
     * Review Quiz begins here! Users are prompted with a set of menu options which can
     * be selected by entering a single character e.g. 'r' for "review certain number of
     * questions". Options also include review proficiency quiz, submit a problem, and quit.
     *
     * @param args Accept command line input. No need in this program.
     */
    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to the CS302 Review Quiz!");
        System.out.println("=================================" + "\n");

        // Scanner object instantiation
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter your email address: ");
        String emailAddress = scan.nextLine();
        System.out.println("");

        // Use a boolean variable to determine whether leave the loop
        boolean loop = true;
        while (loop) {
            // Main Menu
            System.out.println("MAIN MENU");
            System.out.println("    [R]andom Sample Quiz (fixed number of problems)");
            System.out.println("    [P]roficiency Quiz (fixed number of correct answers)");
            System.out.println("    [S]ubmit a Problem");
            System.out.println("    [Q]uit");

            // Prompt user to enter choice
            System.out.print("Enter your choice: ");

            // Take the choice of user
            String choice = scan.next();
            System.out.println("");

            switch (choice) {
                case "r":
                case "R":
                case "p":
                case "P":

                    // the selected section from user
                    int sectionSelection = 0;

                    while (sectionSelection != -1 || !P1.questionsAvailable()) {
                        // Keep looping until sectionSelection is -1 and there is at
                        // least 1 question
                        // in the database.
                        System.out.println(P1.getSections());
                        System.out.print("Choose another section to answer problems from (or -1 to stop): ");

                        // Prompt user to enter section number
                        sectionSelection = scan.nextInt();
                        if (sectionSelection != -1)
                            P1.toggleSection(sectionSelection);
                        System.out.println("");
                    }

                    // Prompt user to enter the number of problem needed for review
                    if (choice.equals("r") || choice.equals("R")) {
                        System.out.print("Enter the number of problems to review: ");
                    } else {
                        System.out.print("Enter the number of correct answers needed to stop: ");
                    }

                    // Take the number from user
                    int numProblem = scan.nextInt();
                    System.out.println("");

                    if (choice.equals("r") || choice.equals("R")) {
                        // Provide the number of questions user needed for review
                        for (int i = 0; i < numProblem; i++) {
                            // Print the question in the database
                            System.out.println((i + 1) + ". " + P1.getQuestion());
                            // Prompt user to enter answer of question
                            System.out.print("Enter your answer: ");
                            // Take the answer from user
                            char answer = scan.next().charAt(0);
                            System.out.println("");
                            // Compare the entered answer with correct answer
                            if (!Character.toString(P1.getAnswer()).equalsIgnoreCase(Character.toString(answer)))
                                System.out.println("The correct answer is: " + P1.getAnswer());
                            else
                                System.out.println("That's correct!!!");
                            System.out.println("");

                            // Prompt user to rate the difficulty of question
                            System.out.print(
                                    "Please rate the difficulty of this problem " + "(1-easy to 5-hard, or 0-report): ");

                            // Save the difficulty of the entered rating
                            P1.submitRating(scan.nextInt());
                            System.out.println("The average rating for this question has been: " + P1.getRating());
                            System.out.println("");

                            // Move to the next question
                            P1.gotoNextProblem();
                        }
                    } else {
                        // Order number
                        int count = 1;

                        // Correct question number
                        int correct = 0;
                        while (correct < numProblem) {
                            // Keep looping until correctly answered numProblem of questions
                            System.out.println(count + ". " + P1.getQuestion());
                            System.out.print("Enter your answer: ");
                            char answer = scan.next().charAt(0);
                            System.out.println("");
                            if (!Character.toString(P1.getAnswer()).equalsIgnoreCase(Character.toString(answer)))
                                System.out.println("The correct answer is: " + P1.getAnswer());
                            else {
                                System.out.println("That's correct!!!");
                                correct++;    // Increment if entered answer is correct
                            }
                            count++;    // Increment every iteration
                            System.out.println("");
                            System.out.print(
                                    "Please rate the difficulty of this problem (1-easy to 5-hard, or 0-report): ");
                            P1.submitRating(scan.nextInt());
                            System.out.println("The average rating for this question has been: " + P1.getRating());
                            System.out.println("");
                            P1.gotoNextProblem();
                        }
                    }

                    break;

                case "s":
                case "S":
                    // Prompt user to enter a question
                    System.out.println("Enter a multiple choice question (including the answer options): ");
                    System.out.println("Type END on a line by itself to finish question.");

                    // Consume the \n in the buffer
                    scan.nextLine();

                    // The whole question user want to enter
                    String line = scan.nextLine();

                    // A single line(part) of question
                    String question = "";
                    while (!line.equals("END")) {
                        // Keep entering the question until user enters END
                        // Append the newly entered line to the end of question(new line)
                        question += line + "\n";
                        line = scan.nextLine();
                    }

                    // Prompt user to enter answer
                    System.out.print("Enter the correct answer for this question (one letter): ");

                    // Take the answer from user
                    char answerToNewQuestion = scan.nextLine().toUpperCase().charAt(0);
                    System.out.println(P1.getSections());

                    // Prompt use to enter section
                    System.out.print("Enter the section for this problem: ");

                    // Take section number
                    int section = scan.nextInt();
                    System.out.println("");

                    // Confirmation to submit question
                    System.out.print("Please proofread your problem and confirm you would like to submit it (Y/N): ");

                    // Take the confirmation
                    char newAnswer = scan.next().toUpperCase().charAt(0);
                    if ((newAnswer == 'Y') || (newAnswer == 'y')) {
                        P1.submitProblem(question, answerToNewQuestion, section, emailAddress);
                        System.out.println("Thank you for submitting this new problem.");
                    } else
                        System.out.println("Problem was NOT submitted.");
                    System.out.println("");
                    break;

                case "q":
                case "Q":
                    // Quit the loop, set loop to false
                    loop = false;
                    break;

                default:
                    System.out.println("Sorry, but that menu choice was not recognized." + "\n");
                    break;
            }
        }
        System.out.println("==========================================");
        System.out.println("Thank you for using the CS302 Review Quiz!");
        // Save the change to db file
        P1.done();
    }
}
