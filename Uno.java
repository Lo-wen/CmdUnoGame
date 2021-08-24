import java.util.Random;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.ArrayList;
public class Uno
{
    public String kulay;
    public int halaga;				//THIS PRINTS UNO's CARDS 
    public Random rand;
    public String shapeOfCards;

    public void Uno(int v, String c)
    {
        halaga = v;
        kulay = c; 
    }
   	// Dito ay mag crecreate ng random cards 
    public Uno()
    {		
        rand = new Random();
        halaga = rand.nextInt(28); // 108 cards ang standard ng Uno at it Can be reduced to 27 (disregarding colors)
        // Assigning value
        if (halaga >= 14) 
            halaga -= 14;
        // Assigning color
        rand = new Random();
        switch(rand.nextInt(4) )
        {
            case 0: kulay = "BLUE"; 
                break;
            case 1: kulay = "RED"; 
                break;						//switch statements 
            case 2: kulay = "YELLOW"; 
                break;
            case 3: kulay = "GREEN"; 
                break;
        }
        // If the card is a wild card
        if (halaga >= 13)
            kulay = "NONE";
    }
        public String getPlayerscard()
    {
        /* Returns what the player sees
         * Ex. [Green 3]
         */
        shapeOfCards = "[";
        if (kulay != "NONE")	//this.object
        {
            shapeOfCards += this.kulay + " ";
        }

        switch(this.halaga)
        {
            default: shapeOfCards += String.valueOf(this.halaga); 
                break;
            case 10: shapeOfCards += "SKIP"; 
                break;
            case 11: shapeOfCards += "REVERSE"; 
                break;
            case 12: shapeOfCards += "DRAW 2"; 
                break;
            case 13: shapeOfCards += "WILD"; 
                break;
            case 14: shapeOfCards += "WILD DRAW 4"; 
                break;
        }
        shapeOfCards += "]";
        return shapeOfCards;
    }

    // Determines if you can place this card on top of a given card
    // The color needs to be specified because if a wild card was chosen in the previous round, the color would have changed, but the card staying the same
    public boolean choosePlace(Uno un, String color)
    {
        if (kulay == color)				
            return true;
        else if (halaga == un.halaga)//un is for public Uno
            return true;
        else if (kulay == "none") // Wild cards
            return true;
        return false;
    }

    //MAINMETHOD
    public static void main(String[] args){
 
        ArrayList<Uno> player = new ArrayList<Uno>();
        ArrayList<Uno> computer = new ArrayList<Uno>();
        int win; // 0 - no result; 1 - win; -1 - loss. 
        Scanner input;
        Uno topCard; // card on top of the "pile"
        int choiceIndex; // Index of chosen card for both player and computer
        String currentColor; // Mainly used for wild cards

        JOptionPane.showMessageDialog(null, "Welcome to Uno. The number #1 Fun");
            JOptionPane.showMessageDialog(null, "How to play this game?\n"
            	+ "Choose only the color of the given topcard\n" 
            	+ "If the topcard is wild at first, then you have to draw, if not it must be the same as wild" 
            	+ "for ex. Topcard : Wild choose wild and pick your own color\n" 
            	+ "If the topcard is color reverse you have 1 luck of turn\n" 
            	+ "If the topcard is skip then you dont have the right to play once\n" 
            	+ "If you dont have same color then you must pick draw\n"
            	+ "If you insert an invalid choice or color the game will automatic skip your turn\n");            
            JOptionPane.showMessageDialog(null,"Good luck PLAYER! You can still quit and comeback anytime");

    try{
        gameLoop:
        while (true)
        {
            player.clear();
            computer.clear();
            win = 0;
            topCard = new Uno();
            currentColor = topCard.kulay;

            System.out.println("\n**********************************************************UNO #1 FUN****************************************************");
            draw(7, player);
            draw(7, computer);
             //ang draw method ay kailangan para sa code na to, ginagamit ito para umulit ulit ang mga cards 
            							//at sa tulong ng clear method matatanggal nito ang mga natapos na cards. in short(paubusan)
            						//nilagay ko po sa pinakahuli yung method ng draw.

            //player turns
            for (boolean pTurn = true; win == 0; pTurn ^= true)
            {
                choiceIndex = 0;
                System.out.println("\nTOP CARD: " + topCard.getPlayerscard());

                if (pTurn) /*****Player's turn******/
                {
                    // Displaying user's deck
                    System.out.println("YOUR TURN! Your choices! Your chance:");
                    for (int i = 0; i < player.size(); i++)
                    {
                        System.out.print(String.valueOf(i + 1) + ". " + 
                        ((Uno) player.get(i) ).getPlayerscard() + "\n");
                    }
                    System.out.println(String.valueOf(player.size() + 1 ) + ". " + "Draw card" + "\n" + 
                                       String.valueOf(player.size() + 2) + ". " + "Quit");
                    // Repeats every time the user doesn't input a number
                    do
                    {
                        System.out.print("\nInput the number of choices: ");
                        input = new Scanner(System.in);
                    } while (!input.hasNextInt() );
                    // The choices were incremented to make them seem more natural (i.e not starting with 0)
                    choiceIndex = input.nextInt() - 1;

                    // Taking action
                    if (choiceIndex == player.size() )
                        draw(1, player);
                    else if (choiceIndex == player.size() + 1)
                        break gameLoop;
                    else if ( ((Uno) player.get(choiceIndex)).choosePlace(topCard, currentColor) )
                    {
                        topCard = (Uno) player.get(choiceIndex);
                        player.remove(choiceIndex);
                        currentColor = topCard.kulay;
                        // Producing the action of special cards                        
                        if (topCard.halaga >= 10)
                        {
                            pTurn = false; // Skipping turn

                            switch(topCard.halaga)
                            {
                                case 12: // Draw 2
                                System.out.println("Drawing 2 cards...");
                                draw(2,computer);
                                break;

                                case 13: case 14: // Wild cards                         
                                do // Repeats every time the user doesn't input a valid color
                                {
                                    System.out.print("\nEnter the color you want: ");
                                    input = new Scanner(System.in);
                                } while(!input.hasNext("R..|r..|G....|g....|B...|b...|Y.....|y.....") ); //Something I learned recently "inputhasNext"
                                if(input.hasNext("R..|r..") )
                                    currentColor = "Red";
                                else if(input.hasNext("G....|g....") )
                                    currentColor = "Green";
                                else if(input.hasNext("B...|b...") )
                                    currentColor = "Blue";
                                else if(input.hasNext("Y.....|y.....") )
                                    currentColor = "Yellow";

                                System.out.println("You chose " + currentColor);
                                if(topCard.halaga == 14) // Wild draw 4
                                {
                                    System.out.println("Drawing 4 cards...");
                                    draw(4,computer);
                                }
                                break;
                            }
                        }
                    }else System.out.println("Invalid choice. Turn skipped.");


                }else 
                //computer's turn
                {
                    System.out.println("Computer's turn! The computer now has " + String.valueOf(computer.size() ) 
                                        + " cards left!" + ((computer.size() == 1) ? "...Uno!":"") );
                    // Finding a card to place
                    for (choiceIndex = 0; choiceIndex < computer.size(); choiceIndex++)
                    {
                        if ( ((Uno) computer.get(choiceIndex)).choosePlace(topCard, currentColor) ) // Searching for playable cards
                            break; 
                    }

                    if (choiceIndex == computer.size() )
                    {
                         System.out.println("The computer has nothing! Drawing cards...");
                         draw(1,computer);
                    }else 
                    {
                         topCard = (Uno)computer.get(choiceIndex);
                         computer.remove(choiceIndex);
                         currentColor = topCard.kulay;
                         System.out.println("Computer chooses " + topCard.getPlayerscard() + " !");

                         // Must do as part of each turn because topCard can stay the same through a round
                         if (topCard.halaga >= 10)
                         {
                             pTurn = true; // Skipping turn

                             switch (topCard.halaga)
                             {
                                 case 12: // Draw 2
                                 System.out.println("Drawing 2 cards for you...");
                                 draw(2,player);
                                 break;

                                 case 13: case 14: // Wild cards                         
                                 do // Picking a random color that's not none
                                 {
                                     currentColor = new Uno().kulay;
                                 } while (currentColor == "none");

                                 System.out.println("New color is " + currentColor);
                                 if (topCard.halaga == 14) // Wild draw 4
                                 {
                                     System.out.println("Drawing 4 cards for you...");
                                     draw(4,player);
                                 }
                                 break;
                             }
                         }
                    }
                    // If ang cards ay wala na
                    if (player.size() == 0)
                        win = 1;
                    else if (computer.size() == 0)
                        win = -1;
                }
            } 
            //Resulta
            if (win == 1)
                System.out.println("Yay!! Nanalo ka :)");
            else 
                System.out.println("Ooops! Talo :(");

            System.out.print("\nGusto mo pa ba?");
            input = new Scanner(System.in);
                break;
        	}
        }catch(Exception e){
        	System.out.println("OUT OF REACH");
        } //dito na magtatapos
        System.out.println("Goodbye");
    }
    // For shuffling cards
    public static void draw(int cards, ArrayList<Uno> deckofCards)
    {
        for (int i = 0; i < cards; i++)
            deckofCards.add(new Uno() );
    }
}