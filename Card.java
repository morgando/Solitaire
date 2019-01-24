
/**
 * Methods in this class are used to access the instance variables that define a Card. 
 * A Card has a rank, suit, and
 * direction it is facing (up or down [true or false]).
 * 
 * @author Morgan Douglas 
 * @version 11/1/16
 */
public class Card
{
    // instance variables - replace the example below with your own
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card
     * @param   r -- rank of card
     * @param   s -- suit of card
     */
    public Card(int r, String s)
    {
        // initialise instance variables
        rank = r;
        suit = s;
        isFaceUp = false;
    }

    /**
     * @return  rank of Card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * @return  suit of Card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * @return  true if Card is red; otherwise false
     */
    public boolean isRed()
    {
        if(suit.equals("d") || suit.equals("h"))
            return true;
        return false;
    }

    /**
     * @return  true if Card is facing upward; otherwise false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * @postcondition   Card turned upward
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * @postcondition   Card turned downward
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

    /**
     * @return  file name of Card
     */
    public String getFileName()
    {
        String v = "0";
        if(rank == 10 || rank == 1 || rank == 11 || rank == 12 || rank == 13)
        {
            if(rank==10)
                v = "t";
            if(rank==1)
                v = "a";
            if(rank==11)
                v = "j";
            if(rank==12)
                v = "q";
            if(rank==13)
                v = "k";
        }
        else
            v = rank + "";
        if(isFaceUp)
            return "cards//" + v + suit + ".gif";
        else
            return "cards//back.gif";
    }
}
