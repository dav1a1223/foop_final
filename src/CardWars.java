import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;

class Field {
    private String faction;
    private Creature_Card creatureOnField;
    private Building_Card buildingOnField;
    private boolean creatureExists;
    private boolean buildingExists;
    private boolean floopAvailable;
    //private boolean hasFlooped;
    private int currentAtk;
    private int currentDef;
    private int creatureAtkBuff;  // if < 0 then is nerf
    private int creatureDefBuff;  // if < 0 then is nerf
    public Field() {
        creatureExists = false;
        buildingExists = false;
        floopAvailable = false;
        //hasFlooped = false;
        currentAtk = 0;
        currentDef = 0;
        creatureAtkBuff = 0;
        creatureDefBuff = 0;
    }
    public void creature_dies(){                        //things to do when creature dies
        creatureExists = false;
        currentAtk = 0;
        currentDef = 0;
        creatureOnField = null;
    }
    public void check_creature(){                       //check if creature dies
        if (currentDef <= 0){
            creature_dies();
        }
    }
    public void place_creature(Card creatureToBePlaced) {creatureOnField = (Creature_Card) creatureToBePlaced;}
    public void place_building(Card buildingToBePlaced) {buildingOnField = (Building_Card) buildingToBePlaced;}
    public void remove_building() {
        creatureAtkBuff = 0;
        creatureDefBuff = 0;
        buildingOnField = null;
    }
    public String get_faction() { return faction; }
    public void set_faction(String newFaction){this.faction = newFaction;}
    public void set_creature_exists(boolean newCreatureExist){this.creatureExists = newCreatureExist;}
    public void set_building_exists(boolean newBuildingExist){this.buildingExists = newBuildingExist;}
    public void set_floop_available(boolean newFloopAvailable){this.floopAvailable = newFloopAvailable;}
    public Creature_Card get_creature() { return creatureOnField; }
    public Building_Card get_building() { return buildingOnField; }
    public boolean is_creature_on_field() { return creatureExists; }
    public boolean is_building_on_field() { return buildingExists; }
    public boolean is_floop_available() { return floopAvailable; }
    public int get_creature_atk_buff() { return creatureAtkBuff; }
    public int get_creature_def_buff() { return creatureDefBuff; }
    public void set_creature_atk_buff(int buff) { creatureAtkBuff = buff; }
    public void set_creature_def_buff(int buff) { creatureDefBuff = buff; }
    public void modify_creature_atk_buff(int changeAtkBuff) { creatureAtkBuff += changeAtkBuff; }
    public void modify_creature_def_buff(int changeDefBuff) { creatureDefBuff += changeDefBuff; }
    public int get_current_atk(){return currentAtk;}
    public int get_current_def(){return currentDef;}
    public void modify_current_atk(int changeAtk){this.currentAtk += changeAtk;}
    public void modify_current_def(int changeDef){this.currentDef += changeDef;}
    public void set_current_atk(int newAtk){this.currentAtk = newAtk;}
    public void set_current_def(int newDef){this.currentDef = newDef;}
    public void print_field(){
        if(is_creature_on_field()){
            System.out.print(creatureOnField.name + " :");
            System.out.print("Creature current attack = " + currentAtk);
            System.out.print("   Creature current defense = " + currentDef);
        }
        else{
            System.out.print("No creature on this field");
        }
        
        if(is_building_on_field()){
            System.out.println("  /   "+buildingOnField.name);
        }
        else{
            System.out.println("  /    No building on this field");
        }
        
    }
}

class Land{
    public Field battle_field[][];
    Land() {
        this.battle_field = new Field[2][4];
    }
    public void print_land(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                battle_field[i][j].print_field();
            
            }
            System.out.println();
        }
    }
}

class Battle{
    public ArrayList<Player> players;
    protected Land land;
    private ArrayList<Card> deckOne, deckTwo;
    private int drawResult;                                                 //Record the result : either 0 or 1
    // If 0 is drawn, then player 0 gets deckOne(with corn creatures), and land.battle_field[0][0-3].faction is set to "corn"
    //                     player 1 gets deckTwo(with corn creatures), and land.battle_field[1][0-3].faction is set to "sand"
    private int round;
    private int playerTurn;
    private int playerOption;
    Battle(){
        players = new ArrayList<>();
        deckOne = new ArrayList<>();
        deckTwo = new ArrayList<>();
        land = new Land();
        for(int i = 0; i < 2; i++)
            players.add(new Player());
        drawResult = -1;
        round = 0;
        playerTurn = -1;
        playerOption = 0;
    }
    public void print_welcome(){
        System.out.println("Welcome to Card Wars !!");
    }
    public void set_game_deck(){                                            //Add the creatures to the two battle decks
        System.out.println("Setting  game decks...");
        int cardNum = 4;
        for(int i = 0; i < 4; i++){   
            if(cardNum <= 4){
                deckOne.add(new Travelin_Farmer());
            }
            if(cardNum <= 3)
                deckOne.add(new Corn_Dog());
            if(cardNum <= 2){
                deckOne.add(new Ethan_Allfire());
                deckOne.add(new Cornball());
                deckOne.add(new Sand_Castle());
            }
            if(cardNum <= 1){
                deckOne.add(new Legion_Of_Earlings());
                deckOne.add(new The_Sludger());
                deckOne.add(new Volcano());
                deckOne.add(new Cerebral_Bloodstorm());
                deckOne.add(new Strawberry_Butt());
                deckOne.add(new Corn_Scepter());
                deckOne.add(new Silo_Of_Truth());
                deckOne.add(new Sand_Sphinx());
            } 
            cardNum--;
        }
        cardNum = 4;                                                        //Reset cardNum
        for(int i = 0; i < 4; i++){                                         //Add four farmers and three corndogs
            if(cardNum <= 2){
                deckTwo.add(new Beach_Mum());
                deckTwo.add(new Sand_Eyebat());
                deckTwo.add(new Sand_Angel());
                deckTwo.add(new Burning_Hand());
                deckTwo.add(new Sand_Castle());
            }
            if(cardNum <= 1){
                deckTwo.add(new Sand_Jackal());
                deckTwo.add(new Sandfoot());
                deckTwo.add(new Sandy());
                deckTwo.add(new Ms_Mummy());
                deckTwo.add(new Volcano());
                deckTwo.add(new Cerebral_Bloodstorm());
                deckTwo.add(new Strawberry_Butt());
                deckTwo.add(new Silo_Of_Truth());
                deckTwo.add(new Sand_Sphinx());
            }
            cardNum--;
        }
    }
    
    public void print_draw(){
        System.out.print(drawResult);
    }
    
    public void set_player_deck(){                                            //Set the decks of the two players and the hero according to the draw result
        if(drawResult == 0){
            players.get(0).copy_deck(deckOne);
            players.get(1).copy_deck(deckTwo);
        }
        else{
            players.get(0).copy_deck(deckTwo);
            players.get(1).copy_deck(deckOne);
        }   
        players.get(0).shuffle_deck();
        players.get(1).shuffle_deck();
        System.out.println("Player " + drawResult + " gets the corn deck~");
        System.out.println("Player " + (1 - drawResult) + " gets the sand deck~");
    }
    
    public void set_hero(){
        if(drawResult == 0){
            players.get(0).set_hero(new Jake());
            players.get(0).set_hero_cool_down(players.get(0).get_hero().abilityRound);
            players.get(1).set_hero(new Princess_Bubblegum());
            players.get(1).set_hero_cool_down(players.get(1).get_hero().abilityRound);
        }
        else{
            players.get(0).set_hero(new Princess_Bubblegum());
            players.get(1).set_hero_cool_down(players.get(1).get_hero().abilityRound);
            players.get(1).set_hero(new Jake());
            players.get(0).set_hero_cool_down(players.get(0).get_hero().abilityRound);
        }
        System.out.println("Player " + drawResult + " gets hero Jake~");
        System.out.println("Player " + (1 - drawResult) + " gets hero Princess Bubblegum~");
    }
    
    public void set_initial_land(){                                             //Set the field of lands according to draw result
        System.out.println("Setting up the corn field and sand field");
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                land.battle_field[i][j] = new Field();
                if(drawResult == 0){
                    if(i == 0)
                        land.battle_field[i][j].set_faction("Corn");
                    else 
                        land.battle_field[i][j].set_faction("Sand");
                }
                else{
                    if(i == 0)
                        land.battle_field[i][j].set_faction("Sand");
                    else 
                        land.battle_field[i][j].set_faction("Corn");
                }
            
            }
        }
    }
    
    public void set_initial_mana(){                                                     //Each player begins with one mana
        for(int i = 0; i < 2; i++){
            players.get(i).set_mana(1);
        }
        System.out.println("Each player begins with one mana");
    }
    
    public boolean player_check(){
        //Check if both players are still alive
        return players.get(0).get_hero().hp > 0 && players.get(1).get_hero().hp > 0;
    }

    public void player_initial_draw(){
            players.get(0).draw_cards(5);
            players.get(1).draw_cards(5);
        System.out.println("Each player begins with 5 cards");
    }
    
    public void battle_round(){                                                          //For each round do...
        while(player_check()){
            Card card_to_activate = null;
            round++;                                                                   //Increase round number at the beginning of round
            playerTurn = (round - 1) % 2;
            System.out.println("Round " + round + " : ");
            System.out.println("Player " + playerTurn + "'s turn!");
            players.get(playerTurn).modify_mana(1);                                    //Assign one more crystal to the current player
            System.out.println("Player " + playerTurn + " is given one more mana, he/she starts the round with " + players.get(playerTurn).get_mana() + " mana");
            players.get(playerTurn).set_current_mana(0);
            players.get(playerTurn).modify_current_mana(players.get(playerTurn).get_mana());
            players.get(playerTurn).draw_cards(1);                                     //Player draws a card
            System.out.println("Player " + playerTurn + " draws a card");
            System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_hand_size() + " cards");
            while(playerOption != -1){
                System.out.println("Please enter the action you wish to perform");
                System.out.println("Enter 1 to activate hero ability, 2 to play a card, 3 to activate the floop abilitiy of one of your creatures, 4 to print card/field information, and -1 to finish the round");
                System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_current_mana() + " mana");
                Scanner s = new Scanner(System.in);
                playerOption = s.nextInt();
                if(playerOption == 1){
                    if(players.get(playerTurn).get_hero_cool_down() == 0){             //Check if hero ability cool down has reached 0
                        players.get(playerTurn).get_hero().hero_ability(playerTurn, land);
                        players.get(playerTurn).set_hero_cool_down(players.get(playerTurn).get_hero().abilityRound);    //Reset hero ability cd to "ability round"
                    }
                    else{
                        System.out.println("Hero ability not ready!");
                    }
                }
                else if(playerOption == 2){
                    System.out.println("Please enter the index of the card you wish to play, enter -1 to not play any cards");
                    System.out.println("Cards available: ");
                    for(int i = 0; i < players.get(playerTurn).get_hand_size(); i++){
                        System.out.print(i + " ");
                    }
                    System.out.println();
                    int index = s.nextInt();
                    if(index == -1){
                        System.out.println("Returning to options");
                    }
                    else if(players.get(playerTurn).get_hand().get(index).get_cost() <= players.get(playerTurn).get_current_mana()){
                        card_to_activate = players.get(playerTurn).play_card(index);
                        if(card_to_activate.cardType == 2){
                            if(players.get(playerTurn).get_current_mana() - card_to_activate.cost >= 0){
                                players.get(playerTurn).modify_current_mana(card_to_activate.cost * -1);
                                System.out.println("Activating spell...");
                                card_to_activate.card_ability(playerTurn, 0, land, players.get(playerTurn) );
                                System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_current_mana() + " mana");
                            }
                            else{
                                System.out.println("Not enough mana, returning to options");
                            }
                        }
                        else if (card_to_activate.cardType == 1){
                            if(players.get(playerTurn).get_current_mana() - card_to_activate.cost >= 0){
                                System.out.println("Please enter the the position you wish to place your building - 0, 1, 2, or 3");
                                System.out.println("Placing your building on a field with another existing buildings will replace it!");
                                int building_index = s.nextInt();
                                players.get(playerTurn).modify_current_mana(card_to_activate.cost * -1);
                                land.battle_field[playerTurn][building_index].remove_building();
                                land.battle_field[playerTurn][building_index].place_building(card_to_activate);
                                land.battle_field[playerTurn][building_index].set_building_exists(true);
                                card_to_activate.card_ability(playerTurn,building_index,land, players.get(playerTurn));
                                System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_current_mana() + " mana");
                            }
                               
                                
                        }
                        else if (card_to_activate.cardType == 0){
                            if(players.get(playerTurn).get_current_mana() - card_to_activate.cost >= 0){
                                System.out.println("Please enter the the position you wish to place your creature - 0, 1, 2, or 3");
                                System.out.println("Placing your creature on a field with another existing creature will replace it!");
                                int creature_index = s.nextInt();
                                players.get(playerTurn).modify_current_mana(card_to_activate.cost * -1);
                                land.battle_field[playerTurn][creature_index].creature_dies();
                                land.battle_field[playerTurn][creature_index].place_creature(card_to_activate);
                                land.battle_field[playerTurn][creature_index].set_creature_exists(true);
                                land.battle_field[playerTurn][creature_index].set_current_atk(card_to_activate.atk);
                                land.battle_field[playerTurn][creature_index].set_current_def(card_to_activate.def);
                                land.battle_field[playerTurn][creature_index].set_floop_available(true);
                                System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_current_mana() + " mana");
                            }
                        }
                    }
                    else{
                        System.out.println("Not enough mana!");
                        players.get(playerTurn).add_to_hand(card_to_activate);
                    }          
                }
                else if(playerOption == 3){
                    System.out.println("Please enter the the position of the creature you wish to floop - 0, 1, 2, or 3");
                    int floop_index = s.nextInt();
                    if(land.battle_field[playerTurn][floop_index].is_creature_on_field()){
                        if(land.battle_field[playerTurn][floop_index].is_floop_available()){
                            if(players.get(playerTurn).get_current_mana() - land.battle_field[playerTurn][floop_index].get_creature().floopCost >= 0){
                                players.get(playerTurn).set_current_mana(players.get(playerTurn).get_current_mana() - land.battle_field[playerTurn][floop_index].get_creature().floopCost);
                                land.battle_field[playerTurn][floop_index].get_creature().card_ability(playerTurn, floop_index, land, players.get(playerTurn));
                                land.battle_field[playerTurn][floop_index].set_floop_available(false);
                                System.out.println("Player " + playerTurn + " currently has " + players.get(playerTurn).get_current_mana() + " mana");
                            }
                            else{
                                System.out.println("Current mana isn't enough.");
                            }
                        }
                        else{
                            System.out.println("You may only floop a creature once each turn!");
                        }   
                    }
                    else{
                        System.out.println("Creature doesn't exist on that field, you may only floop creatures that exist!");
                    }  
                }
                else if(playerOption == 4){
                    System.out.println("Enter 0 to print your hand and 1 to print field information");
                    int op = s.nextInt();
                    if(op == 0){
                        players.get(playerTurn).print_hand();
                    }
                    else if(op == 1){
                        land.print_land();
                    }
                    
                }
                
                
                else if(playerOption == -1){
                    playerOption = 0;
                    break;
                }
                
                else
                    System.out.println("Please enter an available option!");
                
                // set player option back to 0
                playerOption = 0;
            }
            
            // Actual battle happens below
            for(int i = 0; i < 4; i++){
                if(land.battle_field[playerTurn][i].is_creature_on_field()){           //Check if creature in this lane exists
                    if(land.battle_field[1 - playerTurn][i].is_creature_on_field()){   //Check if creature in opposing lane exists
                        land.battle_field[1 - playerTurn][i].modify_current_def((-1) * land.battle_field[playerTurn][i].get_current_atk());
                        if(land.battle_field[1 - playerTurn][i].get_current_def() <= 0){        //Creature in opposing lane dies
                            land.battle_field[1 - playerTurn][i].creature_dies();
                        }
                    }
                    else{   //If no creature exists in opposing lane, hero HP is damaged
                        players.get(1- playerTurn).get_hero().hp -= land.battle_field[playerTurn][i].get_current_atk();
                        
                            //Waiting for GUI~
                        if(players.get(1 - playerTurn).get_hero().hp <= 0){
                            System.out.println("Player " + (1 - playerTurn) + " dies!");
                            System.out.println("Player " + playerTurn + " wins!");
                            
                            break;
                        }
                    }
                
                }
            }
                //hero ability cooldown decreases one for each player every two rounds
            if(round % 2 == 0){
                if(players.get(playerTurn).get_hero_cool_down() > 0)
                    players.get(playerTurn).set_hero_cool_down(players.get(playerTurn).get_hero_cool_down() - 1);
                if(players.get(1 - playerTurn).get_hero_cool_down() > 0)
                    players.get(1 - playerTurn).set_hero_cool_down(players.get(playerTurn).get_hero_cool_down() - 1);
            }
        
            //Reset floop availability of existing creatures on the player's side to true at the end of each turn
            for(int i = 0; i < 4; i++){
                if(land.battle_field[playerTurn][i].is_creature_on_field())
                    land.battle_field[playerTurn][i].set_floop_available(true);
            }
            //Check if game is over at end of each round
            if(!player_check()){
                System.out.println("GAME OVER");
                break;
            }   
        
            
           
        }
    }
    
    
    
    public void random_draw(){  //Randomly generates 0 or 1 to decide which player gets which deck/faction/hero
        Random rand = new Random();
        drawResult = rand.nextInt(1);
        //Can also decide who goes first
    }    
}

public class CardWars{
	public static  void main (String[] args){
		Battle myBattle = new Battle();
                myBattle.print_welcome();
                myBattle.random_draw();
                myBattle.set_game_deck();
                myBattle.set_player_deck();
                myBattle.set_hero();
                myBattle.set_initial_land();
                myBattle.set_initial_mana();
                myBattle.player_initial_draw();
                myBattle.battle_round();
		
	}
}