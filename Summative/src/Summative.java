/**
 * Program Name: Dos Card Game
 * Author: George Wang
 * Date: Jan 25, 2021
 * Course: ICS3U1
 */

/*
To understand the code better, you should run the program, read the instructions, and come back here.

A card is represented with 3 character spaces (2 for the number component, 1 for the colour component)

A move is a sequence of 2-3 cards. It will involve 1 playing area card and 1-2 hand cards.
A move is represented in this format:
10R(00):05R(00)-05R(01) or 10R(00):10R(02)
The card before the colon is the playing area card. Its index in the playing area is represented by two digits in brackets.
The cards after the colon are the hand cards. Their indexes in the hand are represented by two digits in brackets.
If this move is a DOUBLE NUMBER or DOUBLE COLOUR, a dash is used to separate the two hand cards.

A combo is a group of moves, separated with |.
Example: 10R(00):05R(00)-05R(01)|10R(01):10R(02)
Only the computer uses combos, the player just uses a sequence of moves.
The number of moves in a combo cannot be greater than the number of cards in the playing area. 
This is because you can only play a maximum of one move per card in the playing area.

Further comments in the code will explain how the computer program works.

*/


//Import necessary classes
import java.util.Random;
import java.util.Scanner;




public class Summative {

    //An arbitrary length for the playing area, computerHand, and userHand arrays.
    public static final int MAXARRAYLENGTH = 98;

    public static String[] computerHand = new String[MAXARRAYLENGTH]; //array to store the computer's hand
    public static String[] playingArea = new String[MAXARRAYLENGTH]; //array to store the playing area
    public static String[] userHand = new String[MAXARRAYLENGTH]; //array to store the player's hand

    public static boolean[] userHandCross = new boolean[MAXARRAYLENGTH]; //used to determine which hand cards are crossed out during a player's turn.
    public static boolean[] playingAreaCross = new boolean[MAXARRAYLENGTH]; //used to determine which playing area cards are crossed out a player's turn.

    public static String[] deckPile = new String[108]; //array to store all the cards. Dealing and drawing cards will come from this pile.

    //Initialize our needed classes
    public static Scanner input = new Scanner(System.in);
    public static Random random = new Random();

    //scores for the scoreboard
    public static int playerScore = 0;
    public static int computerScore = 0;

    public static void main(String[] args) throws Exception {

        boolean quit = false; //to determine when to end the loop and end the program

        while(!quit){ 
            
            //fill each array with empty (EEE) slots.
            for (int i = 0; i < MAXARRAYLENGTH; i++) {
                computerHand[i] = "EEE";
                playingArea[i] = "EEE";
                userHand[i] = "EEE";
            }
            for (int i = 0; i < deckPile.length; i++) {
                deckPile[i] = "EEE";
            }
    
    
            //populate the deck with the correct amount of cards for each colour
            fillDeckPile();

            //deal cards from the deck pile to the hands and playing area
            dealCards();


            //Print out the welcome page
            System.out.println("WELCOME TO");

            System.out.println("  _____     ____     _____ \n" +
            " |  __ \\   / __ \\   / ____| \n"+ 
            " | |  | | | |  | | | (___   \n " + 
            "| |  | | | |  | |  \\___ \\ \n" + 
            " | |__| | | |__| |  ____) | \n" + 
            " |_____/   \\____/  |_____/  "
            );
            System.out.println("Enter (p) to start a game");
            System.out.println("Enter (i) to read the instructions");
            System.out.println("Enter (s) to see the scoreboard");

            char resp = input.nextLine().charAt(0); //get the user response

            while(resp != 'p' && resp != 'i' && resp != 's'){ //while the response is not one of the options, keep on asking for another response
                System.out.println("That response is invalid. Please try again.");
                resp = input.nextLine().charAt(0);
            }

            if(resp == 'p'){ //if the response is to play


                //get an random integer (either 0 or 1)
                int firstPlay = random.nextInt(2);

                if(firstPlay == 0){ //if 0, the computer goes first
                    
                    //Print the banner 
                    System.out.println("----------------------------------------------------------------------------------------------------------------");

                    System.out.println("   ⎽⎽⎽⎽⎽    ⎽⎽⎽⎽    ⎽⎽  ⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽   ⎽⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽⎽⎽⎽⎽     ⎽⎽⎽⎽⎽⎽⎽   ⎽    ⎽   ⎽⎽⎽⎽⎽    ⎽   ⎽  \n" + 
                    "  / ⎽⎽⎽⎽|  / ⎽⎽ \\  |  \\/  | |  ⎽⎽ \\  | |  | | |⎽⎽   ⎽⎽| |  ⎽⎽⎽⎽| |  ⎽⎽ \\  ( )  / ⎽⎽⎽⎽|   |⎽⎽   ⎽⎽| | |  | | |  ⎽⎽ \\  | \\ | | \n" + 
                    " | |      | |  | | | \\  / | | |⎽⎽) | | |  | |    | |    | |⎽⎽    | |⎽⎽) | |/  | (⎽⎽⎽        | |    | |  | | | |⎽⎽) | |  \\| |  \n" + 
                    " | |      | |  | | | |\\/| | |  ⎽⎽⎽/  | |  | |    | |    |  ⎽⎽|   |  ⎽  /       \\⎽⎽⎽ \\       | |    | |  | | |  ⎽  /  | . ` | \n" + 
                    " | |⎽⎽⎽⎽  | |⎽⎽| | | |  | | | |      | |⎽⎽| |    | |    | |⎽⎽⎽⎽  | | \\ \\       ⎽⎽⎽⎽) |      | |    | |⎽⎽| | | | \\ \\  | |\\  | \n" +
                    "  \\⎽⎽⎽⎽⎽|  \\⎽⎽⎽⎽/  |⎽|  |⎽| |⎽|       \\⎽⎽⎽⎽/     |⎽|    |⎽⎽⎽⎽⎽⎽| |⎽|  \\⎽\\     |⎽⎽⎽⎽⎽/       |⎽|     \\⎽⎽⎽⎽/  |⎽|  \\⎽\\ |⎽| \\⎽|");

                    
                    displayCards(); //Display the hands and playing area
                    System.out.println("The computer goes first. Enter anything to allow it to move: "); //prompt the user to enter anything to allow the computer to move
                    String buffer = input.nextLine();

                    computeMove(); //the computer calculates the best move possible and performs it

                    //reorganize the playing area and the computerHand
                    playingArea = reOrganize(playingArea); 
                    computerHand = reOrganize(computerHand);

                    while(arrayLength(playingArea) < 2){ //if the playing area has less than two cards, fill it back up to two cards
                        addToArray(playingArea, drawCard());
                    }

                }
    
                int exitStatus;
                
                while((exitStatus=performRound()) == 0); //keep on performing rounds until an exit status is returned that is not 0
        
                if(exitStatus == 1){ //if exitStatus is 1, then the player has won
                    System.out.println("Congratulations, you won!");
                    playerScore++; 
                }else{ //otherwise, that means the computer has won
                    System.out.println("Too bad, the computer has won.");
                    computerScore++;
                }

                //ask the user if they want to return to the menu or quit, and get an answer
                System.out.println("Enter 'q' to quit or anything else to return to the main menu"); 
                char returnOrQuit = input.nextLine().charAt(0);

                if(returnOrQuit == 'q'){ //quit if the response is 'q'
                    quit = true;
                }
            }else if(resp == 'i'){ //if the user want to read the instructions
                //print the instructions
                System.out.println("GENERAL BACKGROUND");
                System.out.println("DOS is a card game about matching cards from your hand to cards in the playing area.");
                System.out.println("DOS is an actual card game developed by Mattel Games, the same brand who made Uno.");
                System.out.println("This computer program is a recreation of the card game.");
                System.out.println("Normally, you would play the card game with 2-4 players but for this program, it will be you vs a computer.");
                System.out.println("");
                System.out.println("OBJECTIVE");
                System.out.println("The objective of the game is to get rid of all the cards in your hand.");
                System.out.println("");
                System.out.println("THE CARDS");
                System.out.println("Each card has a number and a colour.");
                System.out.println("There are 4 different colour possibilities: red(R), blue (B), green(G), and yellow(Y).");
                System.out.println("There is also a multicoloured colour(M), which can represent any of the 4 colours above.");
                System.out.println("There are 10 different number possibilities: 1-10 inclusive, represented with two digits (01-10).");
                System.out.println("There is also a wildcard number(##), which can represent any number above.");
                System.out.println("Any number(1-10 and ##) can be any of the four colours except for 02, which is only multicoloured.");
                System.out.println("");
                System.out.println("A card is symbolized with 3 characters, two representing the number and one representing the colour. (02R)");
                System.out.println("");
                System.out.println("YOUR TURN");
                System.out.println("When it is your turn, you have four options for moves to make per card in the playing area. You can play:");
                System.out.println("SINGLE NUMBER: when your card’s number matches a playing area’s card’s number and the colours don’t match.");
                System.out.println("SINGLE COLOUR: when your card’s number matches a playing area’s card’s number and the colours do match. \nYou get to place one of your cards in the playing area for the next turn.");
                System.out.println("DOUBLE NUMBER: when the sum of two of your card’s numbers equals a playing area’s card’s number and the colours of all three cards don’t match.");
                System.out.println("DOUBLE COLOUR: when the sum of two of your card’s numbers equals a playing area’s card’s number and the colours of all three cards match. ");
                System.out.println("You get to place one of your cards in the playing area for the next turn and the computer has to draw a card from the deal pile.");
                System.out.println("Keep in mind that wildcards(##) can represent any number and multicoloured cards can represent any colour.");
                System.out.println("");
                System.out.println("You can only play one move option per playing area card. Therefore you cannot play two moves on one playing area card. ");
                System.out.println("");
                System.out.println("THE GAME");
                System.out.println("Each player is dealt 7 cards and two cards are placed in the playing area.");
                System.out.println("When the game starts, there is a random coin flip to decide who goes first. \nThroughout the game, you will see who’s turn it is by the big banner text above the cards.");
                System.out.println("");
                System.out.println("Every time it is the computer’s turn, you must enter an input to allow the computer to go. You can see what the computer did with text above the large text.");
                System.out.println("");
                System.out.println("Every time it is your turn, you enter one move into the input.");
                System.out.println("Each of the playing area’s cards and each of the cards in your hand will have an index associated with it.");
                System.out.println("To make a move, type the index of the playing area card first, then 1-2 indexes of your hand next.");
                System.out.println("The end result should look like this: 0 0 2 ");
                System.out.println("Make sure to separate each index with one space at least.");
                System.out.println("");
                System.out.println("This move will be performed and the cards the move used will be crossed out.");
                System.out.println("If it is a SINGLE COLOUR or a DOUBLE COLOUR, there will be a prompt asking for what card you would like to place in the playing area.");
                System.out.println("You will enter 1 index with no extra spaces.");
                System.out.println("");
                System.out.println("If you have a valid move you can make, you must make that move.");
                System.out.println("Once you have made all the moves, enter 'x' to end your turn.");
                System.out.println("");
                System.out.println("If you have played on every card in the playing area, the program will automatically end your turn.");
                System.out.println("If you have no moves to play, the computer will automatically detect that and you enter anything to pick up a card.");
                System.out.println("You will then try to make a move.");
                System.out.println("If you still have no moves to play, you will be prompted to enter an index to place a card in the playing area.");
                System.out.println("The turn then ends.");
                System.out.println("");
                System.out.println("If the playing area ever has less than 2 cards after a turn, it is replenished before the next turn begins.");
                System.out.println("");
                System.out.println("If the computer hand is empty, the game will end and the computer will have won.");
                System.out.println("If the player hand is empty, the game will end and the player will have won.");
                System.out.println("");
                System.out.println("EXTRA NOTES");
                System.out.println("Make sure your terminal is at least 30 lines tall.");
                System.out.println("");
                System.out.println("Enter anything to return to the menu");
                String buffer = input.nextLine(); //will be looped back to the menu
            }else{ //if not 'p' or 'i', then it must be 's' 
                //show the scoreboard
                System.out.println("The Scoreboard: ");
                System.out.println("Player's score: " + playerScore);
                System.out.println("Computer's score: " + computerScore);

                System.out.println("Enter anything to return to the menu");
                String buffer = input.nextLine(); //will be looped back to the menu
            }
    
            
        }

        System.out.println("Thank you for playing!"); //print thank-you message

    }

    public static void createColourDeck(String colour){
        //adds the correct amount of cards for the input colour
        for (int i = 1; i < 10; i++) {
            if(i <= 5 && i != 2){ //if the number is between 1 to 5 excluding 2, add three of the cards.
                addToArray(deckPile, "0" + i+colour);
                addToArray(deckPile, "0" + i+colour);
                addToArray(deckPile, "0" + i+colour);
            }else if(i > 5 && i < 10){ //if the number is between 6 to 9, add two of the cards.
                addToArray(deckPile, "0" + i+colour);
                addToArray(deckPile, "0" + i+colour);
            }
        }
        //add the tens and wild cards
        addToArray(deckPile, "10" + colour);
        addToArray(deckPile, "10" + colour);
        addToArray(deckPile, "##" + colour);
        addToArray(deckPile, "##" + colour);

    }

    public static void addToArray(String[] inputArray, String item) {
        //add an item to the array, replacing an empty slot
        boolean quit = false;
        for (int i = 0; i < inputArray.length && !quit; i++) {
            if (inputArray[i].equals("EEE")) {
                inputArray[i] = item;
                quit = true;
            }
        }
    }

    public static void dealCards(){
        for (int i = 0; i < 7; i++) { //draw 7 cards for the userHand
            addToArray(userHand, drawCard());
        }
        for (int i = 0; i < 7; i++) { //draw 7 cards for the computerHand
            addToArray(computerHand, drawCard());
        }
        for (int i = 0; i < 2; i++) { //draw 2 cards for the playing area.
            addToArray(playingArea, drawCard());
        }
    }

    public static void fillDeckPile(){
        //add the correct amount of cards for each colour
        createColourDeck("R");
        createColourDeck("B");
        createColourDeck("Y");
        createColourDeck("G");

        //add the multicoloured DOS cards
        for (int i = 1; i < 13; i++) {
            addToArray(deckPile, "02M");
        }
    }

    public static String drawCard(){
        //take a card from the deck pile
        int index;

        if(arrayLength(deckPile) == 0){ //if the deckPile is empty, repopulate it
            fillDeckPile();
        }

        //choose a card based on a randomly generated integer
        String card = deckPile[(index=random.nextInt(arrayLength(deckPile)))];
        deckPile[index] = "EEE";

        //keep the deckPile organized
        deckPile = reOrganize(deckPile);
        

        return card;
        
    }
    public static int arrayLength(String[] input){
        //instead of using the fixed length of the array, this counts the amount of non-empty cards
        input = reOrganize(input);
        for (int i = 0; i < input.length; i++) {
            if(input[i] == "EEE"){
                return i;
            }
        }
        return input.length;
    }

    public static String[] reOrganize(String[] array){
        //makes sure that there are no empty slots in between card slots
        String[] output = new String[array.length]; //make a new output array with the same length

        for (int i = 0; i < output.length; i++) {  //fill it with empty slots
            output[i] = "EEE";
        }

        for (int i = 0; i < array.length; i++) { //loop through the input array
            if(!array[i].equals("EEE")){ //if the item is not empty, add it to the output
                addToArray(output, array[i]);
            }
        }

        return output; //return that output

    }

    

    public static int performRound(){
        //allows the player to move, and the computer to move

        playerTurn(); //run the player's turn

        //reorganize the player's hand and playing area
        userHand = reOrganize(userHand);
        playingArea = reOrganize(playingArea);

        while(arrayLength(playingArea) < 2){ //if there are less than 2 cards in the playing area, fill it back up to two cards
            addToArray(playingArea, drawCard());
        }


        if(arrayLength(userHand) == 0){ //if the player has used up all their cards, return with a status of 1
            return 1;
        }

        //Print the computer's banner
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println("   ⎽⎽⎽⎽⎽    ⎽⎽⎽⎽    ⎽⎽  ⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽   ⎽⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽⎽   ⎽⎽⎽⎽⎽    ⎽    ⎽⎽⎽⎽⎽     ⎽⎽⎽⎽⎽⎽⎽   ⎽    ⎽   ⎽⎽⎽⎽⎽    ⎽   ⎽  \n" + 
        "  / ⎽⎽⎽⎽|  / ⎽⎽ \\  |  \\/  | |  ⎽⎽ \\  | |  | | |⎽⎽   ⎽⎽| |  ⎽⎽⎽⎽| |  ⎽⎽ \\  ( )  / ⎽⎽⎽⎽|   |⎽⎽   ⎽⎽| | |  | | |  ⎽⎽ \\  | \\ | | \n" + 
        " | |      | |  | | | \\  / | | |⎽⎽) | | |  | |    | |    | |⎽⎽    | |⎽⎽) | |/  | (⎽⎽⎽        | |    | |  | | | |⎽⎽) | |  \\| |  \n" + 
        " | |      | |  | | | |\\/| | |  ⎽⎽⎽/  | |  | |    | |    |  ⎽⎽|   |  ⎽  /       \\⎽⎽⎽ \\       | |    | |  | | |  ⎽  /  | . ` | \n" + 
        " | |⎽⎽⎽⎽  | |⎽⎽| | | |  | | | |      | |⎽⎽| |    | |    | |⎽⎽⎽⎽  | | \\ \\       ⎽⎽⎽⎽) |      | |    | |⎽⎽| | | | \\ \\  | |\\  | \n" +
        "  \\⎽⎽⎽⎽⎽|  \\⎽⎽⎽⎽/  |⎽|  |⎽| |⎽|       \\⎽⎽⎽⎽/     |⎽|    |⎽⎽⎽⎽⎽⎽| |⎽|  \\⎽\\     |⎽⎽⎽⎽⎽/       |⎽|     \\⎽⎽⎽⎽/  |⎽|  \\⎽\\ |⎽| \\⎽|");


        displayCards(); //display the hands and playing area
        System.out.println("It is now the computer's turn to go. Enter anything to allow it to move: ");
        String buffer = input.nextLine(); 

        computeMove(); //calculate the best combo and perform it

        //reorganize the computer's hand and playing area
        computerHand = reOrganize(computerHand); 
        playingArea = reOrganize(playingArea);

        while(arrayLength(playingArea) < 2){  //if there are less than 2 cards in the playing area, fill it back up to two cards
            addToArray(playingArea, drawCard());
        }


        if(arrayLength(computerHand) == 0){ //if the computer has used up all the cards in its hand, return with a status of 2
            return 2;
        }else{ //otherwise, return with a status of 0
            return 0;
        }
        
    }

    
    public static void playerTurn(){
        //runs for a player's turn

        boolean canMakeMove = true; //if it is possible to play a move 

        boolean hasMadeMove = false; //if the player has made a move already

        boolean hasPickedUp = false; //if the player has picked up a card

        boolean hasMadeIllegalMove = false; //if the player tried to make an illegal move 

        boolean doEnd = false; //if the player's turn has ended

        boolean hasMadeOutOfBounds = false; //if the player entered the index of a empty slot

        boolean hasSingleColour = false; //if the player has just played a single colour move

        boolean hasDoubleColour = false; //if the player has just played a double colour move

        boolean mustPlayError = false; //if the player has tried to end their move when they could make a move

        boolean hasAlreadyUsed = false; //if the player tried using a card that has already been used

        boolean hasDoneAll = false; //if the player has played on all of the cards in the playing area

        //used to store the cards sent to the playing area
        //the cards are only put into the playing area after the turn ends, so they are stored here
        String[] playingAreaBuffer = new String[userHand.length]; 
        for (int i = 0; i < playingAreaBuffer.length; i++) {
            playingAreaBuffer[i] = "EEE";
        }
        

        


        while(!doEnd){

            canMakeMove = true; //assume it is possible to make a move
            
            //keep the user's hand and playing area organized
            userHand = reOrganize(userHand);
            playingArea = reOrganize(playingArea);

            //print out the player's banner
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            System.out.println(" ⎽⎽     ⎽⎽   ⎽⎽⎽⎽    ⎽    ⎽   ⎽⎽⎽⎽⎽      ⎽⎽⎽⎽⎽⎽⎽   ⎽    ⎽   ⎽⎽⎽⎽⎽    ⎽   ⎽  \n" + 
            " \\ \\   / /  / ⎽⎽ \\  | |  | | |  ⎽⎽ \\    |⎽⎽   ⎽⎽| | |  | | |  ⎽⎽ \\  | \\ | | \n" + 
            "  \\ \\⎽/ /  | |  | | | |  | | | |⎽⎽) |      | |    | |  | | | |⎽⎽) | |  \\| | \n" +
            "   \\   /   | |  | | | |  | | |  ⎽  /       | |    | |  | | |  ⎽  /  | . ` | \n " + 
            "   | |    | |⎽⎽| | | |⎽⎽| | | | \\ \\       | |    | |⎽⎽| | | | \\ \\  | |\\  | \n" + 
            "    |⎽|     \\⎽⎽⎽⎽/   \\⎽⎽⎽⎽/  |⎽|  \\⎽\\      |⎽|     \\⎽⎽⎽⎽/  |⎽|  \\⎽\\ |⎽| \\⎽| ");
            
            displayCards(); //print out the hands and the playing area

            if(arrayLength(userHand) == 0){ //if the player's hand is empty, end the turn
                doEnd = true;
            }else{
                if(arrayLength(generateMoves(userHand)) == 0){ //using the generateMoves() method, if there are no possible moves, set canMakeMove to false
                    canMakeMove = false;
                }


                if(hasMadeIllegalMove){ //if the player has made an illegal move, print the message
                    System.out.println("That is an invalid move. Please try again.");
                    hasMadeIllegalMove = false;
                }else if(hasMadeOutOfBounds){ //if the player has referred to an empty index, print the message
                    System.out.println("There is no card in that index. Please try again.");
                    hasMadeOutOfBounds = false;
                }else if(mustPlayError){ //if the player has a valid move but tried to quit, print the message
                    System.out.println("You have a valid move and you must play it.");
                    mustPlayError = false;
                }else if(hasAlreadyUsed){ //if the player referred to a card that is already crossed out, print the message
                    System.out.println("That card has already been used.");
                    hasAlreadyUsed = false;
                }

                if(!canMakeMove && !hasPickedUp && !hasMadeMove){// if the player cannot make its first move and it hasn't picked up
                    
                    //prompt the user to pick up card
                    System.out.println("You have no valid moves. Enter anything to pick up a card: ");
                    String buffer = input.nextLine();
                    
                    addToArray(userHand, drawCard()); //add a drawn card to the user's hand

                    hasPickedUp = true; //set hasPickedUp to true

                }else if(!canMakeMove && hasPickedUp && !hasMadeMove){ //if the player cannot make its first move and it has picked up
                    
                    //ask the player for a card to place in the playing Area
                    System.out.println("You still have no valid moves. Enter the card you want to place in the playing area: ");
                    String card = input.nextLine();

                    if(!isANumber(card)){ //if the index of the card is not a number
                        hasMadeIllegalMove = true; 
                    }else if(Integer.valueOf(card) >= arrayLength(userHand)){ // if the index of the card is out of range
                        hasMadeOutOfBounds = true;
                    }else{ //otherwise add the card to the playing area, remove it from the hand, and end the turn
                        addToArray(playingArea, userHand[Integer.valueOf(card)]);
                        userHand[Integer.valueOf(card)] = "EEE";
                        doEnd = true;
                    }

                }else if(hasDoneAll && !(hasSingleColour || hasDoubleColour)){ // if all the cards in the playing area are crossed out and the player isn't going to add another card
                    System.out.println("You have no more moves. Enter anything to end your turn.");
                    String buffer = input.nextLine();  //prompt the user to end the turn
                    doEnd = true; //end the turn


                }else if(hasSingleColour){ //if the previous turn was a SINGLE COLOUR
                    System.out.println("Please enter the card you would like to put in the playing area: ");
                    String move = input.nextLine(); //accept a card index from the user

                    if(!isANumber(move)){ //if the card index was not a number
                        hasMadeIllegalMove = true; //signal the illegal move message 
                    }else if(Integer.valueOf(move) >= arrayLength(userHand)){ //if the card index is out of range
                        hasMadeOutOfBounds = true; //signal the out-of-bounds message
                    }else if(userHandCross[Integer.valueOf(move)]){ //if the card is already in use
                        hasAlreadyUsed = true; //signal the already-used message
                    }else{ //otherwise...
                        addToArray(playingAreaBuffer, userHand[Integer.valueOf(move)]); //add the card to the playingArea buffer
                        userHandCross[Integer.valueOf(move)] = true; //cross out the card in the hand
                        hasSingleColour = false; //close the SINGLE COLOUR turn
                    }
                }else if(hasDoubleColour){ //if the previous turn was a DOUBLE COLOUR
                    System.out.println("You made the computer pick up a card.");
                    System.out.println("Please enter the card you would like to put in the playing area: ");
                    String move = input.nextLine(); //accept a card index from the user

                    if(!isANumber(move)){ //if the card index is not a number
                        hasMadeIllegalMove = true; //signal the illegal move message 
                    }else if(Integer.valueOf(move) >= arrayLength(userHand)){ //if the card index is out of range
                        hasMadeOutOfBounds = true; //signal the out-of-bounds message
                    }else if(userHandCross[Integer.valueOf(move)]){  //if the card is already in use
                        hasAlreadyUsed = true; //signal the already-used message
                    }else { //otherwise
                        addToArray(playingAreaBuffer, userHand[Integer.valueOf(move)]); //add the card to the playingArea buffer
                        userHandCross[Integer.valueOf(move)] = true; //cross out the card in the hand
                        hasDoubleColour = false; //close the DOUBLE COLOUR turn
                    }
                }else { //if the player can actually play a move
                    if(hasMadeMove){ //if the player has already made a move, print the option to end turn
                        System.out.println("Please enter your move or enter 'x' to end your turn: ");
                    }else{//otherwise just ask the player for a move
                        System.out.println("Please enter your move: ");
                    }
                    
                    String move = input.nextLine(); //get the move
            
                    if(move.equals("x")){ //if the move is 'x'
                        if(!hasMadeMove){ //if player hasn't made a move yet
                            mustPlayError = true; //give the the mustPlayError
                        }else{// otherwise end the turn
                            doEnd = true; 
                        }
    
                    }else{ //otherwise 
                        int[] cardIndices = convMoveToIndexes(move); //seperate the input into different indexes
    
                        if(cardIndices.length == 2){ //if there are two indexes (single move)
    
                            if(cardIndices[0] >= arrayLength(playingArea) || cardIndices[1] >= arrayLength(userHand)){ //if at least one of them are out of the hand/playing area range
                                hasMadeOutOfBounds = true; //give the out-of-bounds error
                            }else if (playingAreaCross[cardIndices[0]] || userHandCross[cardIndices[1]]){ //if the playing area card or the player's hand card is already used
                                hasAlreadyUsed = true; //give the already-used error
                            }else if(checkViableMove(playingArea[cardIndices[0]] + "(00):" + userHand[cardIndices[1]] + "(00)")){ //format the cards into the move format (the indexes don't matter) and check if it's viable
                                if((playingArea[cardIndices[0]].charAt(2) == userHand[cardIndices[1]].charAt(2)) || playingArea[cardIndices[0]].charAt(2) == 'M' || userHand[cardIndices[1]].charAt(2) == 'M'){ //if it's a SINGLE COLOUR match
                                    hasSingleColour = true; //turn on hasSingleColour
                                }
                                userHandCross[cardIndices[1]] = true; //cross off the index of the card in the player's hand
                                playingAreaCross[cardIndices[0]] = true; //cross off the index of the card in the playing area
                                hasMadeMove = true; //set hasMadeMove to true
                            }else{ //if not viable, give the hasMadeIllegalMove error 
                                hasMadeIllegalMove = true; 
                            }
                            
                            
                        }else if (cardIndices.length == 3){ //if there are three indexes (double move)
                            if(cardIndices[0] >= arrayLength(playingArea) || cardIndices[1] >= arrayLength(userHand) || cardIndices[2] >=arrayLength(userHand)){ //if at least one of the indexes are out of range
                                hasMadeOutOfBounds = true; //give the out-of-bounds error
                            }else if (playingAreaCross[cardIndices[0]] || userHandCross[cardIndices[1]] || userHandCross[cardIndices[2]]){ //if any of the card indexes are already used
                                hasAlreadyUsed = true; //give the already-used error
                            }else if(checkViableMove(playingArea[cardIndices[0]] + "(00):" + userHand[cardIndices[1]] + "(00)-" + userHand[cardIndices[2]] + "(00)")){ //format the cards into the proper move format (indexes don't matter) and check if it's viable

                                if((playingArea[cardIndices[0]].charAt(2) == userHand[cardIndices[1]].charAt(2) && playingArea[cardIndices[0]].charAt(2) == userHand[cardIndices[2]].charAt(2))
                                    ||
                                    (playingArea[cardIndices[0]].charAt(2) == 'M' && userHand[cardIndices[1]].charAt(2) == userHand[cardIndices[2]].charAt(2))
                                    ||
                                    (userHand[cardIndices[1]].charAt(2) == 'M' && playingArea[cardIndices[0]].charAt(2) == userHand[cardIndices[2]].charAt(2))
                                    ||
                                    (userHand[cardIndices[2]].charAt(2) == 'M' && userHand[cardIndices[1]].charAt(2) == playingArea[cardIndices[0]].charAt(2))
                                    ||
                                    (userHand[cardIndices[1]].charAt(2) == 'M' && userHand[cardIndices[2]].charAt(2) == 'M') 
                                    ){ //check if the move is a DOUBLE COLOUR move 
                                    
                                    addToArray(computerHand, drawCard()); //add a drawnn card to the computer's hand
                                    hasDoubleColour = true; //turn on DOUBLE COLOUR 
                                    
                                }

                                userHandCross[cardIndices[1]] = true; //cross out the card indexes in the player's hand
                                userHandCross[cardIndices[2]] = true;
                                playingAreaCross[cardIndices[0]] = true; //cross out the card index in the playing area
                                hasMadeMove = true; //the player has now made a move
                                
                            }else{ //if the move is not viable,
                                hasMadeIllegalMove = true; //give the illegal move error
                            }
                            
                        }else{ //if the number of indexes are not 2 or 3
                            hasMadeIllegalMove = true; //give the illegal move error
                        }

                    }
                
                }


            }

            if(isCompletelyUsed(userHand, userHandCross)){ //if all of the cards in the player's hand are crossed
                doEnd = true; //quit the loop
            }

            if(isCompletelyUsed(playingArea, playingAreaCross)){ //if all of the cards in the playing area are crossed
                hasDoneAll = true; 
            }

        }//end looping moves

        for (int i = 0; i < arrayLength(playingAreaBuffer); i++) { //loop through all of the cards in the playing area buffer
            addToArray(playingArea, playingAreaBuffer[i]); //add them to the playing area
        }

        for (int i = 0; i < playingArea.length; i++){//loop through the playing area
            if(playingAreaCross[i]){ //if the card is crossed out
                playingArea[i] = "EEE"; //remove it
                playingAreaCross[i] = false; //set it back to uncrossed
            }
            
        }

        for (int i = 0; i < userHand.length; i++) { //loop through the player's hand
            if(userHandCross[i]){ //if the card is crossed out
                userHand[i] = "EEE"; //remove it 
                userHandCross[i] = false; //set it back to uncrossed
            }
            
        }

    }

    public static boolean isCompletelyUsed(String[] array, boolean[] cross){
        //check if all of the cards in an array are crossed out (their associated boolean index is true)
        
        for (int i = 0; i < arrayLength(array); i++) { //loop through the array
            if(!cross[i]){ //if the card is not crossed out
                return false; //return false
            }
        }

        return true; //otherwise when ended, return true
    }


    public static boolean isANumber(String input){
        //check if a string input is a number

        if(input.length() == 0){ //if the input is "", return false
            return false;
        }

        for (int i = 0; i < input.length(); i++) {//loop through each character in the input string
            if(input.charAt(i) < '0' || input.charAt(i) > '9'){ //if the character is not a digit
                return false; //return false
            }
        }

        return true; //otherwise when ended, return true
    }

    public static int[] convMoveToIndexes(String input){
        //convert a string into a series of indexes

        String[] preOutput = new String[(input.length()/2) + 1]; //the most amount of numbers in an x length string is x/2 + 1

        for (int i = 0; i < preOutput.length; i++) {//fill the output array with empty strings
            preOutput[i] = "";
        }

        boolean insideNumber = false; //if the previous character was a number

        int currIndex = 0; //keeps track of the index in the preOutput
        
        for (int i = 0; i < input.length(); i++) { //loop through the characters in the input string
            if(input.charAt(i) < '0' || input.charAt(i) > '9'){ //if the character is not a digit
                if(insideNumber == true){ //if the previous character was a number
                    currIndex++; //go to the next index in the preOutput array
                }
                insideNumber = false; //the previous character is now not a number
                
            }else{ //if the current character is a digit
                preOutput[currIndex] = preOutput[currIndex] + input.charAt(i); //add it to the current index of preOutput 
                insideNumber = true; //the previous character is now a number
            }
        }


        // create a new array to remove all of the empty indexes 
        int[] output = new int[findFirstEmpty(preOutput)]; 
        for (int i = 0; i < output.length; i++) {
            output[i] = Integer.valueOf(preOutput[i]);
        }

        //return that array
        return output;
    }

    public static int findFirstEmpty(String[] input){
        //find the index of the first empty string in the array
        for (int i = 0; i < input.length; i++) { //loop through the array
            if(input[i] == ""){ //if this index is empty, return the index
                return i;
            }
        }
        return input.length; //if completed the loop, return the array's length
    }

    public static void displayCards(){ 
        //print out the computer hand, playing area, and player's hand

        //print out the computer's hand
        System.out.println("COMPUTER HAND");
        System.out.println("-----");
        System.out.println("|   |");
        System.out.println("| ? |  x " + arrayLength(computerHand)); 
        System.out.println("|   |");
        System.out.println("-----");


        //print out the playing area
        System.out.println("PLAYING AREA");
        printCardFormat(playingArea, playingAreaCross);

        //print out the player's hand
        System.out.println("YOUR HAND");
        printCardFormat(userHand, userHandCross);

    }

    public static void printCardFormat(String[] array, boolean[] cross){
        //given a hand of cards and its respective boolean array of which cards are crossed, print them out

        //print the correct number of tops
        for (int i = 0; i < arrayLength(array); i++) {
            System.out.print("----- ");
        }

        System.out.println("");

        //print the correct number of top-middles (depending on each card's colour)
        for (int i = 0; i < arrayLength(array); i++) {
            if(cross[i]){ //if this card is crossed
                System.out.print("|\\ /| ");
            }else{ //if this card is not crossed
                System.out.print("|" + array[i].charAt(2) + "  | ");
            }
            
        }

        System.out.println("");

        //print out the correct number of middles 
        for (int i = 0; i < arrayLength(array); i++) {
            if(cross[i]){ //if this card is crossed
                System.out.print("| X | ");
            }else if(array[i].charAt(0) == '1'){ //if this card is a 10
                System.out.print("|1" + array[i].charAt(1) +" | ");
            }else{ //if this card is not a 10
                System.out.print("| " + array[i].charAt(1) +" | ");
            }
        }

        System.out.println("");

        //print out the correct number of bottom-middles (depending on each card's colour)
        for (int i = 0; i < arrayLength(array); i++) {
            if(cross[i]){
                System.out.print("|/ \\| ");
            }else{
                System.out.print("|  " + array[i].charAt(2) + "| ");
            }
            
        }

        System.out.println("");

        //print out the correct number of bottoms 
        for (int i = 0; i < arrayLength(array); i++) {
            System.out.print("----- ");
        }

        System.out.println("");

        //print out the card's indexes
        for (int i = 0; i < arrayLength(array); i++) {
            if(i > 9){ // if the index is a 1 digit number
                System.out.print(" [" + i + "] ");
            }else{// if the index is not a 1 digit number
                System.out.print(" [" + i + "]  ");
            }
            
        }
        System.out.println("");
    }


    public static void computeMove() { //used to calculate the computer's move and perform it

        boolean finish = false; //if to end the computer

        boolean hasPickedUp = false; //if the computer has picked up a card
        

        while(!finish){
            String[] moveList = generateMoves(computerHand); //generate an array of all the possible moves the computer can do (based on the computer's hand)

            if(arrayLength(moveList) == 0){ //if the array's length is 0 (no moves possible)
                if(!hasPickedUp){ // if the computer hasn't picked up a card yet
                    addToArray(computerHand, drawCard()); //add a drawn card to the computer's hand
                    System.out.println("The computer has picked up a card.");  
                    hasPickedUp = true;
                }else{// if the computer has picked up a card
                    int largeCardIndex = findLargestCard(computerHand); //find the card with the largest number in the computer's hand
                    addToArray(playingArea, computerHand[largeCardIndex]); //add that card to the playing area
                    computerHand[largeCardIndex] = "EEE"; //remove it from the computer's hand
                    finish = true; //end the computer's turn
                    System.out.println("The computer has placed a card in the playing area.");
                    
                }

            }else{// if there are moves possible
    
                //sort the array of possible moves into multiple lists(strings)
                //There is one list for every card in the playing area
                String[] groupedMoves = groupMovesByCard(moveList); 
                
                //generate a list of possible combinations using the multiple lists
                String[] possibleCombos = removeEmptySpace(comboMaker(removeEmptySpace(groupedMoves)));
    
                int[] comboCardUsage = new int[possibleCombos.length]; //create a new integer array the same length as the number of possible combos
    
                for (int i = 0; i < possibleCombos.length; i++) { //for each combo, count the number of cards it uses and put it in the respective index
                    comboCardUsage[i] = countCardUsage(possibleCombos[i]);
                }
                
                String bestCombo = findBestCombo(possibleCombos, comboCardUsage); //given combos and their respective card usages, find the best combo (the one with the most cards used)
                
                performCombo(bestCombo); //perform that combo

                finish = true; //end turn

                
            }
        }

    }

    public static String[] removeEmptySpace(String[] input){
        //given an array, remove all the empty indexes ("")

        String[] output = new String[input.length]; 
        int numEmpty = 0; //empty index count

        for (int i = 0; i < input.length; i++) { //loop through the input array
            if(input[i].length() == 0){ //if it's empty
                numEmpty++; //add another to the empty index count
            }else{ //if it's not empty
                output[i-numEmpty] = input[i]; //add that String to the output (shifted based on the amount of empty indexes)
            }
        }

        String[] output2 = new String[output.length - numEmpty]; //make another output (with the amount of non-empty indexes)

        for (int i = 0; i < output2.length; i++) { //loop through the 2nd output
            output2[i] = output[i]; //copy indexes from the first output to the 2nd
        }

        return output2; //return the 2nd output
    }

    public static String[] generateMoves(String[] hand){
        //given a hand, generate a list of all the possible moves

        String[] output = new String[1000]; //set the output to an arbitrary length
        for (int i = 0; i < output.length; i++) { //fill this output with empty spaces for now
            output[i] = "EEE";
        }

        //reorganize the playing area 
        playingArea = reOrganize(playingArea);

        
        for (int iTargetCard = 0; iTargetCard < arrayLength(playingArea); iTargetCard++) { //loop through each card in the playing area
            
            String move = "";

            for (int i = 0; i < arrayLength(hand); i++) { //loop through each card in the hand
                
                //make a single move out of this card
                move = playingArea[iTargetCard] + "(" + IntTo2Digits(iTargetCard) + "):" + hand[i]+ "(" + IntTo2Digits(i) + ")";
                
                if (checkViableMove(move)) { // if this move is a viable one, add it to the output
                    addToArray(output, move);
                }

                for (int j = i+1; j < arrayLength(hand); j++) { //loop through each card after in the hand
                    
                    //make a double move out of these two cards
                    move = playingArea[iTargetCard] + "(" + IntTo2Digits(iTargetCard) + "):"
                            + hand[i] + "(" + IntTo2Digits(i) + ")" + "-" + hand[j] + "("
                            + IntTo2Digits(j) + ")";

                    if (checkViableMove(move)) { // if this move is a viable one, add it to the output
                        addToArray(output, move);
                    }

                }

            }

        }
        return output; //return the output

    }

    public static String IntTo2Digits(int input) {
        //given an integer, format it into 2 characters
        if (input < 10) { //if the input is a 1 digit number
            return "0" + Integer.toString(input); //add a 0 to the front
        }else{//if the input is not a 1 digit number 
            return Integer.toString(input);  //return it 
        }
            
    }

    public static boolean checkViableMove(String move) {
        //given a potential move, check if it is a legal move
        
        if (move.contains("-")) { // if it is a DOUBLE move

            if (symbolCounter(move, '#') == 0){ //if there is no wildcards in this move
                if (Integer.valueOf(move.substring(0, 2)) == Integer.valueOf(move.substring(8, 10))
                    + Integer.valueOf(move.substring(16, 18))) { 
                    return true; //the integers must sum up together to return true
                }else{
                    return false;
                }
            }else if (symbolCounter(move, '#') == 2){//if there is 1 wildcard in this move
                if((move.substring(0, 2).equals("##") && 
                Integer.valueOf(move.substring(8, 10)) + Integer.valueOf(move.substring(16, 18)) <= 10) // if wildcard is the playing area card, and the playing cards sum to 10 or less
                || 
                (move.substring(8, 10).equals("##") && 
                Integer.valueOf(move.substring(0, 2)) > Integer.valueOf(move.substring(16, 18))) //if wildcard is in hand's 1st card, and the playing area card is greater than the hand's 2nd card 
                ||
                (move.substring(16, 18).equals("##") &&
                Integer.valueOf(move.substring(0, 2)) > Integer.valueOf(move.substring(8, 10))) //if the wildcard is in the hand's 2nd card, and the playing area card is greater than the hand's 1st card
                ){
                    return true; //return true
                }else{
                    return false;
                }
                
            } else if(symbolCounter(move, '#') == 4){ //if there are 2 wildcards in this move
                if(move.substring(0, 2).equals("##")){ //if one of the wildcards are in playing area
                    return true; //return true
                }else if(Integer.valueOf(move.substring(0, 2)) >= 2){ //if both of the wildcards are in the hand, and the playing area card is greater than 2
                    return true;  
                }else{ //otherwise return false
                    return false;
                }
            }else{ //if all three are wildcards, return true
                return true;
            }

        } else { //if it is a SINGLE move
            if(symbolCounter(move, '#') == 0){ //if there are no wildcards
                if( Integer.valueOf(move.substring(0, 2)) == Integer.valueOf(move.substring(8, 10))){ //if the numbers match
                    return true; //return true
                }else{ //otherwise return false
                    return false; 
                }
            }else{ //if 1 or more wild cards, return true
                return true;
            }

        }


    }

    public static String[] groupMovesByCard(String[] moves){ 
        //given an array of moves, group the moves by which card involve in the playing area

        String[] playingAreaCardMoves = new String[arrayLength(playingArea)]; //make an array with the length of the number of cards in the playing area

        for (int i = 0; i < playingAreaCardMoves.length; i++) { //fill the array with empty strings
            playingAreaCardMoves[i] = "";
        }

        for (int i = 0; i < arrayLength(playingArea); i++) { //loop through the cards in the playing area
            for (int j = 0; j < arrayLength(moves); j++) { //loop through the input move array
                if(Integer.valueOf(moves[j].substring(4, 6)) == i){//if this move's index is referring to the current index in the playing area
                    playingAreaCardMoves[i] = playingAreaCardMoves[i] + moves[j] + ","; //add it to the current playing area card's list
                }
            }
            if(playingAreaCardMoves[i].length() > 0){ // if there are moves in this playing area card's list
                playingAreaCardMoves[i] = playingAreaCardMoves[i].substring(0, playingAreaCardMoves[i].length()-1); //cut off the extra comma at the end
            }
            
        }


        return playingAreaCardMoves; //return the array of lists of moves
    }

    public static String[] comboMaker(String[] input){
        //given an array of lists of moves (each list of moves correlates to each card in the playing area), give a list of possible combos 
        /*
        Explanation:
        comboMaker() takes an input of an array of lists of moves, one list per playing area card.
        We can imagine this input like this
        {"0, 1, 2, 3",
        "0, 1, 2",
        "0, 1, 2"}
        The numbers stand in for the indexes of moves in their list.

        We make combinations by mixing and matching numbers from different lists. 
        A systematic way to list the different combinations:
        n
        0: 0 | 0 | 0
        1: 0 | 0 | 1
        2: 0 | 0 | 2 
        3: 0 | 1 | 0
        4: 0 | 1 | 1 
        ...
        
        These represent combos that have the same number of moves as there are playing area cards. 
        However, a combo doesn't have to play on all playing area cards.
        Therefore we have to add an additional option to each list (an empty option)
        The list of different combinations would look more like this: (E represents an empty option)
        n
        0: 0 | 0 | 0
        1: 0 | 0 | 1
        2: 0 | 0 | 2 
        3: 0 | 0 | E
        4: 0 | 1 | 0
        5: 0 | 1 | 1 
        ...
        That empty option is the reason why we add 1 more to the length of the list when considering all possible combos

        When looking on this list, a pattern emerges. 
        The third column repeats periodically, with each period being the length of the last list. Lets call that length C.
        If we think of n as index of total number of possible combinations, the 3rd index in a combo is n % c
        The second column repeats periodically, wich each period being the length of the 2nd last list (called B) x C
        The 2nd index can therefore be represented by (n/C) % B

        You can guess that the first index can be represented by (n/(BC)) % A
        
        What generateTerm() does is accepts the array of lists, the current n value, which term it is looking for, and outputs which move index should be there

        */
        int product = 1;
        for (int i = 0; i < input.length; i++) { //loop through each list
            if(input[i].length() != 0){ // if the list is not empty
                product *= symbolCounter(input[i], ',') + 2; //multiply the (amount of moves in this list + 1 no move) to the total
            }
        }

        String[] output = new String[product-1]; //make a new array with number of total possible move combinations (subtract 1 because there is a move combination which involves no moves, which is pointless) as length
        String currCombo = ""; 

        for (int n = 0; n < product-1; n++) { //loop through the number of total possible move combinations
            currCombo = ""; //reset the current combo 
            for (int i = 0; i < input.length; i++) { //loop through each of the terms (each term correlates to one list which correlates to one playing card)
                if(generateTerm(input, n, i) < (symbolCounter(input[i], ',') + 1)){ //if this term is not referring to the "No move" option (where the term is equal to the length of the list)
                    if(input[i].split(",")[generateTerm(input, n, i)].length() != 0){// if this move is not empty
                        currCombo = currCombo + "|" +  input[i].split(",")[generateTerm(input, n, i)]; //add this move to the combo
                    }
                }

                
            }

            currCombo = currCombo.substring(1, currCombo.length()); // remove the first | 

            if(isComboCompatible(currCombo)){ //if the combo is compatible (moves don't conflict in terms of computer hand cards)
                output[n] = currCombo; //add this combo to the output
            }else{ //if the combo is not compatible, leave this particular index empty
                output[n] = ""; 
            }
        }

        return output; //return output
    }

    public static boolean isComboCompatible(String combo){
        //checks if the input combo is compatible (moves don't require the same computer hand cards)

        String[] movesInCombo = combo.split("\\|"); //create an array of moves (separate with |)
        String[] usedIndexesInHand = new String[movesInCombo.length*2]; //array to store the used cards (each move can have at most 2 cards, so at most there are twice as many cards used as moves)
        for (int i = 0; i < usedIndexesInHand.length; i++) { //fill this array with "EEE" slots
            usedIndexesInHand[i] = "EEE";
        }

        for (int i = 0; i < movesInCombo.length; i++) { //loop through each move in the combo
            if(isItemInArray(usedIndexesInHand, movesInCombo[i].substring(12, 14))){ //if this index is a part of the used indexes array
                return false; //return false
            }
            addToArray(usedIndexesInHand, movesInCombo[i].substring(12, 14)); //add this index to the used indexes

            if(movesInCombo[i].contains("-")){ //if this move is a double move
                if(isItemInArray(usedIndexesInHand, movesInCombo[i].substring(20, 22))){ //if the second index in the move is a part of the used indexes array
                    return false; //return false
                }
    
                addToArray(usedIndexesInHand, movesInCombo[i].substring(20, 22)); //add this index to the used indexes
            }
        }

        return true; //return true


    }

    public static boolean isItemInArray(String[] array, String item){ 
        //checks if this item is a member of this array

        for (int i = 0; i < array.length; i++) { //loop through this array
            if(item.equals(array[i])){ //if it equals this item
                return true; //return true
            }
        }
        return false; //if completed the loop, return false
    }

    public static int generateTerm(String[] input, int n, int termIndex){
        //calculate which move should be in this term in this index

        int denomProd = 1;
        for (int i = termIndex+1; i < input.length; i++) { //loop through the number of lists (from the termIndex to the last)
            denomProd *= symbolCounter(input[i], ',') + 2; //multiply the amount of moves +1 with the current total
        }

        return (n/denomProd) % (symbolCounter(input[termIndex], ',')+2); //using the formula, return the move index

    }
    
    public static int symbolCounter(String input, char symbol){
        //counts the number of times a character occurs in a string

        int count = 0;
        for (int i = 0; i < input.length(); i++) { //loop through the characters in the string
            if(input.charAt(i) == symbol){ //if this current character is the one we want to count
                count++; // add 1 to the total
            }
        }

        return count; //return the total
    }


    public static int findLargestCard(String[] hand){
        //given a hand, find the index of the card with the biggest value

        int index = -1;
        int largestValue = 0;
        for (int i = 0; i < hand.length; i++) { //loop through each card
            if(hand[i].charAt(0) != '#' && hand[i].charAt(0) != 'E'){ //if the card is not empty and is not a wild card
                if(Integer.valueOf(hand[i].substring(0, 2)) > largestValue){ //if the card's number is greater than the biggest number yet
                    index = i; //record its index
                    largestValue = Integer.valueOf(hand[i].substring(0, 2)); //record its value
                
                }
            }
        }

        if(index != -1){ //if the index has changed from the beginning, return the index
            return index;
        }
        for (int i = 0; i < hand.length; i++) { //otherwise, look for the wildcard to place
            if(hand[i].charAt(0) == '#'){
                return i;
            }
        }
        
        return 0; //otherwise, return 0
        

        

    }

    public static int countCardUsage(String combo){
        //given a combo, count the amount of cards it can use

        if(combo.length() == 0){ //if the combo is empty
            return 0; //the length is 0
        }else{ //if the combo is not empty
            int cardCounter = 0; 
            String[] movesInCombo = combo.split("\\|"); //divide the combo into its component moves (moves are separated by |)
            for (int i = 0; i < movesInCombo.length; i++) { //for each move
                if(!movesInCombo[i].contains("-")){ //if it's a SINGLE move
                    cardCounter++; //add one card to total
                    if(movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10)){ //if it's a SINGLE COLOUR move
                        cardCounter++; //add another card to total
                    }
                }else{ //if it's a DOUBLE move
                    cardCounter+=2; //add two cards to total
                    if((movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10) && movesInCombo[i].charAt(2) == movesInCombo[i].charAt(18))
                    ||
                    (movesInCombo[i].charAt(2) == 'M' && movesInCombo[i].charAt(10) == movesInCombo[i].charAt(18))
                    ||
                    (movesInCombo[i].charAt(10) == 'M' && movesInCombo[i].charAt(18) == movesInCombo[i].charAt(2))
                    ||
                    (movesInCombo[i].charAt(18) == 'M' && movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10))
                    ){ //if it's a DOUBLE COLOUR move
                        cardCounter++; //add another card to total
                    }

                }
            }

            return cardCounter; //return the total
        }
        
    }

    public static String findBestCombo(String[] combos, int[] cardUsages){ 
        //given a list of combos and their card usage numbers, find the combo with the largest card usage
        
        int bestComboIndex = 0;
        int bestCardUsage = cardUsages[0]; 

        for (int i = 1; i < combos.length; i++) { //loop through all the combos
            if(cardUsages[i] > bestCardUsage){ //if this combo's card usage is higher than the current recorded one
                bestCardUsage = cardUsages[i]; //record its card usage
                bestComboIndex = i; //record the combo's index

            }else if(cardUsages[i] == bestCardUsage){  //if this combo's card usage is the same as the current recorded one
                if((symbolCounter(combos[bestComboIndex], '#') > symbolCounter(combos[i], '#')) && combos[i].length() != 0){ //if this combo uses less wild cards than the best one
                    bestCardUsage = cardUsages[i]; //record its card usage
                    bestComboIndex = i; //record the combo's index
                }
            }
        }

        return combos[bestComboIndex]; //return the biggest recorded combo

    }

    public static void performCombo(String combo){
        //input a given combo, perform it

        //split the combo into its component moves (the moves are separated by |)
        String[] movesInCombo = combo.split("\\|");


        for (int i = 0; i < movesInCombo.length; i++) { //loop for each move
            if(!movesInCombo[i].contains("-")){ //if it is a single move (SINGLE NUMBER or SINGLE COLOUR)

                if(computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))].equals("EEE")){ //if this card is empty (perhaps it was drawn as the largest card for another move)
                    i = movesInCombo.length; //cancel the loop 
                }else{
                    //print out the correct message
                    System.out.println("The computer has played: " + computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))] + " on " + playingArea[Integer.valueOf(movesInCombo[i].substring(4,6))]);
                    
                    //remove the involved cards from the playing area and computer's hand
                    computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))] = "EEE";
                    playingArea[Integer.valueOf(movesInCombo[i].substring(4,6))] = "EEE";

                    if(movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10) && arrayLength(computerHand) != 0){ //if it is a SINGLE COLOUR (and there are still cards left in the hand)
                        int largestCardIndex = findLargestCard(computerHand);

                        if(!computerHand[largestCardIndex].equals("EEE")){
                            System.out.println("Because it was a single colour match, the computer has put " + computerHand[largestCardIndex] + " in the playing area.");
                            addToArray(playingArea, computerHand[largestCardIndex]); //add the largest card to the playing area
                            computerHand[largestCardIndex] = "EEE"; //remove that card from the computer's hand
                        }
                        
                        
                    }
                }
                
                
            }else{//if it is a double move (DOUBLE NUMBER or DOUBLE COLOUR)

                //print out the correct message

                //if the cards needed to be played are empty (perhaps one of them was drawn as the largest card in another move)
                if(computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))].equals("EEE") || computerHand[Integer.valueOf(movesInCombo[i].substring(20, 22))].equals("EEE")){
                    i = movesInCombo.length;
                }else{
                    System.out.println("The computer has played: " + computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))] + " and " + computerHand[Integer.valueOf(movesInCombo[i].substring(20, 22))] + " on " + playingArea[Integer.valueOf(movesInCombo[i].substring(4,6))]);
                
                    //remove the involved cards from the playing area and computer's hand
                    computerHand[Integer.valueOf(movesInCombo[i].substring(12, 14))] = "EEE";
                    computerHand[Integer.valueOf(movesInCombo[i].substring(20, 22))] = "EEE";
                    playingArea[Integer.valueOf(movesInCombo[i].substring(4,6))] = "EEE";

                    if(((movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10) && movesInCombo[i].charAt(2) == movesInCombo[i].charAt(18))
                    ||
                    (movesInCombo[i].charAt(2) == 'M' && movesInCombo[i].charAt(10) == movesInCombo[i].charAt(18))
                    ||
                    (movesInCombo[i].charAt(10) == 'M' && movesInCombo[i].charAt(18) == movesInCombo[i].charAt(2))
                    ||
                    (movesInCombo[i].charAt(18) == 'M' && movesInCombo[i].charAt(2) == movesInCombo[i].charAt(10))
                    ||
                    (movesInCombo[i].charAt(10) == 'M' && movesInCombo[i].charAt(18) == 'M')
                    ) && arrayLength(computerHand) != 0){ //if it is a DOUBLE COLOUR (and there are still cards left in the hand)
                        int largestCardIndex = findLargestCard(computerHand);
                        if(!computerHand[largestCardIndex].equals("EEE")){
                            System.out.println("Because it was a double colour match, the computer has put " + computerHand[largestCardIndex] + " in the playing area and made you draw a card.");
                            addToArray(playingArea, computerHand[largestCardIndex]); //add the largest card in the hand to the playing area
                            computerHand[largestCardIndex] = "EEE"; //remove that card from the hand

                            addToArray(userHand, drawCard()); //add a drawn card to the player's hand
                        }
                        
                    }
                }
                


            }
        }
    }


}
