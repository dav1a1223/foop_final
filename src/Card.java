import java.util.Scanner;

class Card{
    protected int cost;
    protected String faction;
    protected int cardType; // 0 creature, 1 building, 2 spell
    protected int atk;
    protected int def;
    protected int floopCost;
    protected String name;
    protected String cardAbility;
    public Card(){
        cost = 0;
        faction = null;
    }
    public void card_ability(int playerNum, int index, Land l, Player p){};
    public int get_cost(){ return this.cost ;}
    public void print_card(){
        if(cardType == 0){
            System.out.print("Creature name: " + name);
            System.out.print("   Cost: " + cost);
            System.out.print("   Attack: " + atk);
            System.out.print("   Defense: " + def);
            System.out.print("   Floop cost: " + floopCost);
            System.out.println("   Floop ability: " + cardAbility);
        }
        else if(cardType == 1){
            System.out.print("Building name: " + name);
            System.out.print("   Building cost: " + cost);
            System.out.println("   Building ability: " + cardAbility);
        }
        else if(cardType == 2){
            System.out.print("Spell name: " + name);
            System.out.print("   Spell cost: " + cost);
            System.out.println("   Spell ability: " + cardAbility);
            
        }
    
    }
}
class Creature_Card extends Card{
    public Creature_Card(){
        super();
        atk = 0;
        def = 0;
        floopCost = 0;
        cardType = 0;
    }
    

}
/*  Creature  Cards   */
/*-----------------Corn Creature----------------------*/
class Travelin_Farmer extends Creature_Card {
	public Travelin_Farmer(){
		cost = 1;
		faction = "Corn";
		atk = 5;
		def = 5;
		floopCost = 1;
                name = "Travelin_Farmer";
                cardAbility = "Get +1 attack for each adjacent empty lane";
	}
	/* get 1 atk for each adjacent empty lane*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		//System.out.println("i am farmer.");
		if(index-1 >= 0 && l.battle_field[playerNum][index-1].is_creature_on_field() == false)
			l.battle_field[playerNum][index].modify_current_atk(1);
		if(index+1 <= 3 && l.battle_field[playerNum][index+1].is_creature_on_field() == false)
			l.battle_field[playerNum][index].modify_current_atk(1);
	}
}
class Corn_Dog extends Creature_Card {
	public Corn_Dog(){
		cost = 2;
		faction = "Corn";
		atk = 9;
		def = 11;
		floopCost = 3;
                name = "Corn_Dog";
                cardAbility = "Adjacent creatures gain +4 attack";
	}
	/* adjacent creature get 4 atk */
	public void card_ability(int playerNum, int index, Land l, Player p){
		if(index-1 >= 0 && l.battle_field[playerNum][index-1].is_creature_on_field() == true)
			l.battle_field[playerNum][index-1].modify_current_atk(4);
		if(index+1 <= 3 && l.battle_field[playerNum][index+1].is_creature_on_field() == true)
			l.battle_field[playerNum][index+1].modify_current_atk(4);
	}
}
class Legion_Of_Earlings extends Creature_Card {
	public Legion_Of_Earlings(){
		cost = 5;
		faction = "Corn";
		atk = 23;
		def = 23;
		floopCost = 3;
                name = "Legion_Of_Earling";
                cardAbility = "All creatures gain +5 attack";
	}
	/* every my creature get 5 atk*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		for(int i=0; i<4; i++){
			if(l.battle_field[playerNum][i].is_creature_on_field() == true)
				l.battle_field[playerNum][i].modify_current_atk(5);
		}
	}
}
class Ethan_Allfire extends Creature_Card {
	public Ethan_Allfire(){
		cost = 1;
		faction = "Corn";
		atk = 5;
		def = 1;
		floopCost = 1;
                name = "Ethan_Allfire";
                cardAbility = "Lower attack of opposing creature by 3 and kill itself";
	}
	/* lower opposing 3 atk and kill itself*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		int oppPlayerNum = 1 - playerNum;
		if(l.battle_field[oppPlayerNum][index].is_creature_on_field()==true){
			int finalOppAtk = l.battle_field[oppPlayerNum][index].get_current_atk() - 3;
			if(finalOppAtk <= 0)
				l.battle_field[oppPlayerNum][index].set_current_atk(0);
			else
				l.battle_field[oppPlayerNum][index].set_current_atk(finalOppAtk);
			l.battle_field[playerNum][index].set_current_def(0);  // kill itself
		}
	}
}
class Cornball extends Creature_Card {
	public Cornball(){
		cost = 1;
		faction = "Corn";
		atk = 2;
		def = 5;
		floopCost = 2;
                name = "Cornball";
                cardAbility = "Gain +1 attack";
	}
	/* get 1 atk*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		l.battle_field[playerNum][index].modify_current_atk(1);
	}
}
class The_Sludger extends Creature_Card {
	public The_Sludger(){
		cost = 3;
		faction = "Corn";
		atk = 15;
		def = 15;
		floopCost = 1;
                name = "The_Sludger";
                cardAbility = "Lower attack of adjacent creatures by 2 and increase attack by 4";
	}
	/* lower adjacent 2 def and add 4 atk*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		if(index-1 >= 0 && l.battle_field[playerNum][index-1].is_creature_on_field()==true){
			l.battle_field[playerNum][index-1].modify_current_atk(4);
			l.battle_field[playerNum][index-1].modify_current_def(-2);
		}
		if(index+1 <= 3 && l.battle_field[playerNum][index+1].is_creature_on_field()==true){
			l.battle_field[playerNum][index+1].modify_current_atk(4);
			l.battle_field[playerNum][index+1].modify_current_def(-2);
		}
	}
}
/*-------------Sand Creature--------------------*/
class Sand_Jackal extends Creature_Card {
	public Sand_Jackal(){
		cost = 3;
		faction = "Sand";
		atk = 6;
		def = 21;
		floopCost = 2;
                name = "Sand_Jackal";
                cardAbility = "Lower attack of the opposing creature by your current attack";
	}
	/* lower opposing's atk = Sand_Jackal's recently atk*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		int oppPlayerNum = 1 - playerNum;
		if(l.battle_field[oppPlayerNum][index].is_creature_on_field() == true){
			int lowerNum = l.battle_field[playerNum][index].get_current_atk();
			int finalAtk = l.battle_field[oppPlayerNum][index].get_current_atk() - lowerNum;
			if(finalAtk >= 0)
				l.battle_field[oppPlayerNum][index].set_current_atk(finalAtk);
			else
				l.battle_field[oppPlayerNum][index].set_current_atk(0);
		}
	}
}
class Sandfoot extends Creature_Card {
	public Sandfoot(){
		cost = 3;
		faction = "Sand";
		atk = 10;
		def = 17;
		floopCost = 3;
                name = "Sandfoot";
                cardAbility = "Gain +4 defense for each existing creature in your lane";
	}
	/*gain 4 def for each creature on my lane*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		int count = 0;
		for(int i=0; i<4; i++){
			if(l.battle_field[playerNum][i].is_creature_on_field() == true)
				count++;
		}
		l.battle_field[playerNum][index].modify_current_def(4*count);
	}
}
class Beach_Mum extends Creature_Card {
	public Beach_Mum(){
		cost = 2;
		faction = "Sand";
		atk = 9;
		def = 11;
		floopCost = 3;
                name = "Beach_Mum";
                cardAbility = "Gain +2 attack and +3 defense";
	}
	/*get 2 atk and 3 def*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		l.battle_field[playerNum][index].modify_current_atk(2);
		l.battle_field[playerNum][index].modify_current_def(3);
	}
}
class Sand_Eyebat extends Creature_Card {
	public Sand_Eyebat(){
		cost = 1;
		faction = "Sand";
		atk = 4;
		def = 4;
		floopCost = 1;
                name = "Sand_Eyebat";
                cardAbility = "Lower defense of opposing creature by 2";
	}
	/*lower opp its def by 2*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		int oppPlayerNum = 1 - playerNum;
		if(l.battle_field[oppPlayerNum][index].is_creature_on_field() == true)
			l.battle_field[oppPlayerNum][index].modify_current_def(2);
	}
}
class Sandy extends Creature_Card {
	public Sandy(){
		cost = 5;
		faction = "Sand";
		atk = 28;
		def = 18;
		floopCost = 2;
                name = "Sandy";
                cardAbility = "Gain +3 attack and +7 defense";
	}
	/*get 3 atk and 7 def*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		l.battle_field[playerNum][index].modify_current_atk(3);
		l.battle_field[playerNum][index].modify_current_def(7);
	}
}
class Burning_Hand extends Creature_Card {
	public Burning_Hand(){
		cost = 1;
		faction = "Sand";
		atk = 5;
		def = 2;
		floopCost = 1;
                name = "Burning_Hand";
                cardAbility = "Lower defense of opposing creature by 2";
	}
	/*lower the opposing def 2*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		int oppPlayerNum = 1 - playerNum;
		if(l.battle_field[oppPlayerNum][index].is_creature_on_field() == true)
			l.battle_field[oppPlayerNum][index].modify_current_def(2);
	}
}
class Ms_Mummy extends Creature_Card {
	public Ms_Mummy(){
		cost = 1;
		faction = "Sand";
		atk = 3;
		def = 6;
		floopCost = 3;
                name = "Ms_Mummy";
                cardAbility = "Adjacent creatures gain +3 defense";
	}
	/*adjacent creature gain 3 def*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		if(index-1 >= 0 && l.battle_field[playerNum][index-1].is_creature_on_field() == true)
			l.battle_field[playerNum][index-1].modify_current_def(3);
		if(index+1 <= 3 && l.battle_field[playerNum][index+1].is_creature_on_field() == true)
			l.battle_field[playerNum][index+1].modify_current_def(3);
	}
}
class Sand_Angel extends Creature_Card {
	public Sand_Angel(){
		cost = 1;
		faction = "Sand";
		atk = 2;
		def = 6;
		floopCost = 1;
                name = "Sand_Angel";
                cardAbility = "Give adjacent creatures +2 defense";
	}
	/* give adjacent 2 def*/
	public void card_ability(int playerNum, int index, Land l, Player p){
		if(index-1 >= 0 && l.battle_field[playerNum][index-1].is_creature_on_field() == true)
			l.battle_field[playerNum][index-1].modify_current_def(2);
		if(index+1 <= 3 && l.battle_field[playerNum][index+1].is_creature_on_field() == true)
			l.battle_field[playerNum][index+1].modify_current_def(2);
	}
}
/*-------------------Building Card-----------------------*/

class Building_Card extends Card{
    public Building_Card(){
    	super();
    	cardType = 1;
    }
}
class Silo_Of_Truth extends Building_Card{
	public Silo_Of_Truth(){
		super();
		cost = 1;
                name = "Silo_Of_Truth";
                cardAbility = "Creature in this lane gain +2 attack for each card in your opponent's hand";
	}
	// count opp creature number, get 2*num atk
	public void card_ability(int playerNum, int index, Land l, Player p){
		int oppPlayerNum = 1 - playerNum;
		int count = 0;
		for(int i=0; i<4; i++){
			if(l.battle_field[oppPlayerNum][i].is_creature_on_field() == true)
				count++;
		}
		l.battle_field[playerNum][index].modify_current_atk(2*count);
		l.battle_field[playerNum][index].set_creature_atk_buff(l.battle_field[playerNum][index].get_creature_atk_buff()+2*count);
	};
}
class Sand_Sphinx extends Building_Card{
	public Sand_Sphinx(){
		super();
		cost = 1;
                name = "Sand_Sphinx";
                cardAbility = "All creatures gain +5 attack";
	}
	// get 5 def 
	public void card_ability(int playerNum, int index, Land l, Player p){
		l.battle_field[playerNum][index].modify_current_def(5);
		l.battle_field[playerNum][index].set_creature_def_buff(l.battle_field[playerNum][index].get_creature_def_buff()+5);
	}
}
class Sand_Castle extends Building_Card{
	public Sand_Castle(){
		super();
		cost = 1;
                name = "Sand_Castle";
                cardAbility = "Creatures in this lane gets +4 attack and +4 defense";
	}
	// get 4 atk and 4 def
	public void card_ability(int playerNum, int index, Land l, Player p){
		l.battle_field[playerNum][index].modify_current_def(4);
		l.battle_field[playerNum][index].set_creature_def_buff(l.battle_field[playerNum][index].get_creature_def_buff()+4);
		l.battle_field[playerNum][index].modify_current_atk(4);
		l.battle_field[playerNum][index].set_creature_atk_buff(l.battle_field[playerNum][index].get_creature_atk_buff()+4);
	}
}
/*-------------------Spell Card-----------------------*/

class Spell_Card extends Card{
    public Spell_Card(){
    	super();
    	cardType = 2;
    }
}
class Volcano extends Spell_Card{
	public Volcano(){
		super();
		cost = 4;
                name = "Sand_Sphinx";
                cardAbility = "Clear all creatures and building in this lane";
	}
	// clear all thing on this index field.
	public void card_ability(int playerNum,int j, Land l, Player p){
                System.out.print("Enter the field on which you want to use the volcano spell card");
		Scanner s = new Scanner(System.in);
		int index = s.nextInt();
		for(int i=0; i<2; i++){
			l.battle_field[i][index].creature_dies();
			l.battle_field[i][index].remove_building();
		}
	}
}
class Cerebral_Bloodstorm extends Spell_Card{
	public Cerebral_Bloodstorm(){
		super();
		cost = 3;
                name = "Celebral_Bloodstorm";
                cardAbility = "Decrease a creature's defense by it's own attack";
	}
	// decrease one def = its atk
	public void card_ability(int playerNum,int j, Land l, Player p){
                System.out.println("Please enter the lane in which you plan to use the card");
		Scanner s = new Scanner(System.in);
		int index = s.nextInt();
		int oppPlayerNum = 1 - playerNum;
		int decreaseNum = 0 - l.battle_field[oppPlayerNum][index].get_current_atk();
		l.battle_field[oppPlayerNum][index].modify_current_def(decreaseNum);
	}
}
class Strawberry_Butt extends Spell_Card{
	public Strawberry_Butt(){
		super();
		cost = 3;
                name = "Strawberry_Butt";
                cardAbility = "draw 2 cards";
	}
	// draw 2 cards
	public void card_ability(int playerNum,int j, Land l, Player p){
		p.draw_cards(2);
	}
}
class Corn_Scepter extends Spell_Card{
	public Corn_Scepter(){
		super();
		cost = 2;
                name = "Corn_Scepter";
                cardAbility = "Choose a corn creature and attack opposing creature in the lane";
	}
	// hit a corn's opp
	public void card_ability(int playerNum,int j, Land l, Player p){
                System.out.println("Please enter the index of the creature to trigger the spell on");
		Scanner s = new Scanner(System.in);
		int index = s.nextInt();
		if(l.battle_field[playerNum][index].get_faction().equals("Corn")){
			int decreaseNum = 0 - l.battle_field[playerNum][index].get_current_atk();
			l.battle_field[1-playerNum][index].modify_current_def(decreaseNum);
		}
	}
}
class Tome_Of_Ankhs extends Spell_Card{
	public Tome_Of_Ankhs(){
		super();
		cost = 2;
                name = "Tome_Of_Ankhs";
                cardAbility = "Choose a sandy land creature and attack the opposing creature in the lane";
	}
	// hit a corn's opp
	public void card_ability(int playerNum,int j, Land l, Player p){
		System.out.println("Please enter the index of the creature you wish to trigger the spell upon");
                Scanner s = new Scanner(System.in);
		int index = s.nextInt();
		if(l.battle_field[playerNum][index].get_faction().equals("Sand")){
			int decreaseNum = 0 - l.battle_field[playerNum][index].get_current_atk();
			l.battle_field[1-playerNum][index].modify_current_def(decreaseNum);
		}
	}
}
