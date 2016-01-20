import java.util.ArrayList;
import java.util.Collections;
class Player{
    Player(){
        id = 0;
        myHero = new Hero();
        heroCoolDown = 0;
        myDeck = new ArrayList<>();
        myHand = new ArrayList<>();
        mana = 0;
        current_mana = 0;
        
        //myFields = new ArrayList<Field>();
        //oppFields = new ArrayList<Field>();
    };
    private int id;
    private Hero myHero;
    private int heroCoolDown;
    private ArrayList<Card> myDeck; // the cards player hasn't draw
    private ArrayList<Card> myHand;  // the cards player hold on hand
    private int mana;
    private int current_mana;
    //private ArrayList<Field> myFields;  // the cards player play on field and alive
    //private ArrayList<Field> oppFields; // the cards opponent play on field and alive
    public int get_hand_size(){return myHand.size();}
    public int get_id(){return this.id;}
    public void set_id(int newId){this.id = newId;}
    public Hero get_hero(){return this.myHero;}
    public void set_hero( Hero newHero){this.myHero = newHero;}
    public int get_hero_cool_down(){return this.heroCoolDown;}
    public void set_hero_cool_down(int newCoolDown){this.heroCoolDown = newCoolDown;}
    public void set_deck(Card newCard){myDeck.add(newCard);}
    public void copy_deck(ArrayList<Card> newDeck){
        for(int i = 0; i < newDeck.size(); i++){
            myDeck.add(newDeck.get(i));
        }
    }
    public void print_hand(){
        if(get_hand_size() > 0){
            for(int i = 0; i < get_hand_size(); i++){
                System.out.print(i + ". ");
                myHand.get(i).print_card();
            }
        
        }

    }
    
    public void add_to_hand(Card addCard){myHand.add(addCard);}
    public ArrayList<Card> get_hand(){return myHand;}
    public int get_mana(){return this.mana;}
    public int get_current_mana(){return this.current_mana;}
    public void set_mana(int newMana){this.mana = newMana;}
    public void modify_mana(int changeMana){this.mana += changeMana;}
    public void set_current_mana(int newCurrentMana){this.current_mana = newCurrentMana;}
    public void modify_current_mana(int changeCurrentMana){this.current_mana += changeCurrentMana;}
    //public void set_fields(ArrayList<Field> newFields){ this.myFields = newFields;}
    //public ArrayList<Fields> get_fields(){return this.myFields;}
    //public ArrayList<Fields> get_opp_fields(){return this.oppFields;}
    public void shuffle_deck(){
        Collections.shuffle(myDeck);
    }

    public void draw_cards(int num){                                     // Draw a number of cards from deck and add it to player's hand
        //                                                               //Draw from top of deck
        for(int i = 0; i < num; i++){
            if(myDeck.size() - num >= 0)
                myHand.add(myDeck.remove(0));
            else
                System.out.println("Not enough cards");
        }
    }
    public Card play_card(int index){
        
        Card returnCard = myHand.get(index);
        myHand.remove(index);
        return returnCard;
    }
}