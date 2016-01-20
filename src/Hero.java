class Hero{
    public Hero(){
        heroName = null;
        hp = 0;
        //System.out.println("I am a hero.");
    };
    public void hero_ability(int playerNum, Land l){};
    protected String heroName;
    protected int hp;
    protected int abilityRound;
}

class Jake extends Hero{
	public Jake(){
		super();
		heroName = "Jake";
		hp = 60;
		abilityRound = 5;
	}
	/* all corn creature atk +3 */
	public void hero_ability(int playerNum, Land l){
		System.out.println("All corn creatures gain +3 atk!");
		for(int i=0; i<4; i++){
			if(l.battle_field[playerNum][i].get_faction() == "Corn")
				l.battle_field[playerNum][i].modify_current_atk(3);
		}
	}
}
class Princess_Bubblegum extends Hero{
	public Princess_Bubblegum(){
		super();
		heroName = "Princess_Bubblegum";
		hp = 60;
		abilityRound = 4;
	}
	/* heal all creature */
	public void hero_ability(int playerNum, Land l){
		System.out.println("All creatures healed to full health!");
		for(int i=0; i<4; i++){
                    if(l.battle_field[playerNum][i].is_creature_on_field()){
                        if(l.battle_field[playerNum][i].get_current_def() < l.battle_field[playerNum][i].get_creature().def)
			l.battle_field[playerNum][i].set_current_def(l.battle_field[playerNum][i].get_creature().def);           
                    }
		}
		
	}
}