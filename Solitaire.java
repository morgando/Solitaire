import java.util.*;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

/**
 * @author  Morgan Douglas
 * @version 11/1/16
 * Solitaire class has the ability to launch a game of Solitaire. Its methods relate to the game.
 */
public class Solitaire
{
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    /**
     * Creates a game of solitaire.
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        for(int i=0; i<4; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        piles = new Stack[7];
        for(int i=0; i<7; i++)
        {
            piles[i] = new Stack<Card>();
        }
        waste = new Stack();
        stock = new Stack();
        createStock();
        deal();
        dealThreeCards();
        for(int i=0; i<4; i++)
        {
            foundations[i].push(new Card(13, "d")).turnUp();
        }
        display = new SolitaireDisplay(this);
    }

    //     /**
    //      * Creates a game of solitaire.
    //      */
    //     public Solitaire()
    //     {
    //         foundations = new Stack[4];
    //         for(int i=0; i<4; i++)
    //         {
    //             foundations[i] = new Stack<Card>();
    //         }
    //         piles = new Stack[7];
    //         for(int i=0; i<7; i++)
    //         {
    //             piles[i] = new Stack<Card>();
    //         }
    //         waste = new Stack();
    //         stock = new Stack();
    //         createStock();
    //         deal();
    //         dealThreeCards();
    //         display = new SolitaireDisplay(this);
    //     }

    /**
     * deals three cards from the stock to the waste and turns them upward
     */
    public void dealThreeCards()
    {
        if(stock.size()<3)
        {
            while(!stock.isEmpty())
            {
                waste.push(stock.pop()).turnUp();
            }
        }
        else
        {
            for(int i=0; i<3; i++)
            {
                waste.push(stock.pop()).turnUp();
            }
        }
    }

    /**
     * moves all cards from waste into stock and turns them downward
     */

    public void resetStock()
    {
        while(!waste.isEmpty())
        {
            stock.push(waste.pop()).turnDown();
        }
    }

    /**
     * creates the cards in the game and puts them into the stock randomly
     */
    public void createStock()
    {
        ArrayList<Card> stockArr = new ArrayList<Card>();
        for(int i=0; i<4; i++)
        {
            for(int j=1; j<=13; j++)
            {
                if(i==0)
                {
                    stockArr.add(new Card(j, "c"));
                }
                if(i==1)
                {
                    stockArr.add(new Card(j, "d"));
                }
                if(i==2)
                {
                    stockArr.add(new Card(j, "h"));
                }
                if(i==3)
                {
                    stockArr.add(new Card(j, "s"));
                }
            }
        }
        while (stockArr.size()>0)
            stock.push(stockArr.remove((int) (Math.random()*stockArr.size())));

    }

    /**
     * deals cards from stock into the 7 piles appropriately, 
     * turning the last card added to each pile upward
     */
    public void deal()

    {
        for (int i=0; i<7; i++)
        {
            for(int j=0; j<i+1; j++)
                piles[i].push(stock.pop());
        }
        for(int k=0; k<7; k++)
        {
            piles[k].peek().turnUp();
        }
    }
    //returns the card on top of the stock,
    //or null if the stock is empty
    /**
     * @return  top card in stock or null if stock empty
     */
    public Card getStockCard()
    {
        if(stock.isEmpty())
            return null;
        return stock.peek();
    }

    //returns the card on top of the waste,
    //or null if the waste is empty
    /**
     * @return  top card in waste or null if waste empty
     */
    public Card getWasteCard()
    {
        if(waste.isEmpty())
            return null;
        return waste.peek();
    }

    //precondition:  0 <= index < 4
    //postcondition: returns the card on top of the given
    //               foundation, or null if the foundation
    //               is empty
    /**
     * @param   index 0<= index <4 
     * @return  top card in one of foundations or null if foundation empty
     */
    public Card getFoundationCard(int index)
    {
        if(foundations[index].isEmpty())
            return null;
        return foundations[index].peek();
    }

    //precondition:  0 <= index < 7
    //postcondition: returns a reference to the given pile
    /**
     * @param 0<= int index <7
     * @return  reference to piles[index]
     */
    public Stack<Card> getPile(int index)
    {
        Stack<Card> ind = piles[index];
        return ind;
    }

    //called when the stock is clicked
    /**
     * @postcondition   doesn't do anything if anything else is selected. If nothing else is selected, dealThreeCards() will be
     * called if the stock isn't empty (resetStock() will be called if it's empty). Ultimately prints "stock clicked"
     */
    public void stockClicked()
    {
        //IMPLEMENT ME
        if(display.isPileSelected() || display.isWasteSelected())
        {
        }
        else
        {
            if(!stock.isEmpty())
            {
                dealThreeCards();
            }
            else
            {
                resetStock();
            }
        }
        System.out.println("stock clicked");
    }

    //called when the waste is clicked
    /**
     * @postcondition   unselects waste if already selected, otherwise selects waste. Ultimately, "waste clicked" is 
     * printed
     */
    public void wasteClicked()
    {
        //IMPLEMENT ME
        if(display.isWasteSelected())
            display.unselect();
        else if(!display.isPileSelected() && !waste.isEmpty())
            display.selectWaste();
        System.out.println("waste clicked");
    }

    //precondition:  0 <= index < 4
    //called when given foundation is clicked
    /**
     * @param    0 <= int index < 4
     * @postcondition   if another pile is selected and card(s) from that pile can be moved, they will be moved from the pile to foundations[index]. if waste is selected the same 
     * will happen with the card moving from the waste to foundations[index]. the pile or waste will then be unselected. will check if foundations are completed and will print a
     * congratulatory message if they are.
     */
    public void foundationClicked(int index)
    {
        //IMPLEMENT ME
        if(display.isPileSelected())
        {
            if(canAddToFoundation(piles[display.selectedPile()].peek(), index))
            {
                foundations[index].push(piles[display.selectedPile()].pop());
                display.unselect();
            }
        }
        else if(display.isWasteSelected())
        {
            if(canAddToFoundation(waste.peek(), index))
            {
                foundations[index].push(waste.pop());
                display.unselect();
            }
        }
        System.out.println("foundation #" + index + " clicked");
        int i = 0;
        for(int z=0; z<4; z++)
        {
            if(foundations[z].peek().getRank() == 13)
                i++;
        }
        if(i == 4)
        {
            display.infoBox("CONGRATULATIONS! YOU WIN!", "notice");
            int d = display.buttonBox("Would you like to restart game?");
            if(d==JOptionPane.YES_OPTION)
            {
                giveUp();
            }
        }
    }
    //precondition:  0 <= index < 7
    //postcondition: Returns true if the given card can be
    //legally moved to the top of the given
    //pile
    /**
     * @param   0 <= index < 7
     * @param   Card card
     * @return  true if card can be moved to piles[index]; false if card cannot be moved to piles[index]
     */
    public boolean canAddToPile(Card card, int index)
    {
        if(!piles[index].isEmpty())
        {
            if(card.getRank() == piles[index].peek().getRank()-1)
            {
                if(piles[index].peek().getSuit() == "c" || piles[index].peek().getSuit() == "s")
                {
                    if(card.getSuit() == "d" || card.getSuit() == "h")
                        return true;
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    if(card.getSuit() == "c" || card.getSuit() == "s")
                        return true;
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * @param   0 <= index < 7
     * @postcondition   will evaluate the situation depending on which stacks are already selected, moving cards between piles as needed, and unselecting piles already selected.
     */
    public void pileClicked(int index)
    {
        //IMPLEMENT ME
        if(display.isPileSelected())
        {
            if(display.selectedPile() == index)
                display.unselect();
            else
            {
                Stack<Card> z = removeFaceUpCards(display.selectedPile());
                if(!piles[index].isEmpty())
                {
                    if(piles[index].peek().isFaceUp())
                    {
                        if(canAddToPile(z.peek(), index))
                        {
                            addToPile(z, index);
                        }
                        else
                        {
                            addToPile(z, display.selectedPile());
                        }
                    }
                    else
                    {
                        addToPile(z, index);
                    }
                }
                else
                {
                    addToPile(z, index);
                }
                display.unselect();
            }
        }
        else
        {
            if(display.isWasteSelected())
            {
                if(!piles[index].isEmpty())
                {
                    if(piles[index].peek().isFaceUp())
                    {
                        if(canAddToPile(waste.peek(), index))
                        {
                            piles[index].push(waste.pop());
                        }
                    }
                    else
                    {
                        piles[index].push(waste.pop());
                    }
                }
                else
                {
                    piles[index].push(waste.pop());
                }
                display.unselect();
            }
            if(!display.isPileSelected() || !display.isWasteSelected())
                if(!piles[index].isEmpty() && piles[index].peek().isFaceUp())
                {
                    display.selectPile(index);
                    System.out.println("pile #" + index + " clicked");
                }
                else
                if(!piles[index].isEmpty() && !piles[index].peek().isFaceUp())
                {
                    piles[index].peek().turnUp();
                }
        }
    }
    //precondition:  0 <= index < 7
    //postcondition: Removes all face-up cards on the top of
    //the given pile; returns a stack
    //containing these cards
    /**
     * @param   0 <= index < 7
     * @postcondition   all face up cards removed from piles[index]
     * @return  stack containing removed cards
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> faceUp = new Stack<Card>(); 
        if(piles[index].isEmpty())
        {
            return faceUp;
        }
        else
        {
            while(!piles[index].isEmpty() && piles[index].peek().isFaceUp())
            {
                faceUp.push(piles[index].pop());
            }
            return faceUp;
        }
    }

    //precondition:  0 <= index < 7
    //postcondition: Removes elements from cards, and adds
    //               them to the given pile.
    /**
     * @param   0 <= index < 7
     * @param   Stack<Card> cards
     * @postcondition   adds cards in a stack of cards to piles[index]
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while(!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }
    //precondition:  0 <= index < 4
    //postcondition: Returns true if the given card can be
    //legally moved to the top of the given
    //foundation
    /**
     *@param    0 <= index < 4
     *@param    Card card
     *@return   true if card can be added to foundations[index]; false if card cannot be added to foundations[index]
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        if(foundations[index].isEmpty())
        {
            if(card.getRank() == 1)
            {
                return true;
            }
        }
        else
        {
            if(foundations[index].peek().getRank() +1 == card.getRank() && foundations[index].peek().getSuit()
            == card.getSuit())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @postcondition   Solitaire game emptied
     */
    private void emptyEverything()
    {
        for(int i=0; i<7; i++)
        {
            while(!piles[i].isEmpty())
                piles[i].pop();
        }
        for(int i=0; i<4; i++)
        {
            while(!foundations[i].isEmpty())
                foundations[i].pop();
        }
        while(!waste.isEmpty())
            waste.pop();
        while(!stock.isEmpty())
            stock.pop();
    }

    /**
     * @postcondition   Solitaire game emptied and reset
     */
    public void giveUp()
    {
        emptyEverything();
        new Solitaire();
    }
}
