package Model;

public enum TriggerType {

	//Objets directement construits

	PLAY_ALL_WHEN_CLIP_PLAYED ("Jouer tous les clips du set"),

	STOP_ALL_WHEN_CLIP_PLAYED ("Arrêter les clips du set"),

	DO_NOTHING_WHEN_CLIP_PLAYED ("Ne rien faire");

	private String name = "";
	
	TriggerType(String name){

		this.name = name;

	}

	public String toString(){

		return name;

	}


}
