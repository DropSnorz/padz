package model.event;

public enum TriggerType {


	PLAY_ALL_WHEN_CLIP_PLAYED ("Play all clips on clip play"),

	STOP_ALL_WHEN_CLIP_PLAYED ("Stop all clips on clip play"),

	DO_NOTHING_WHEN_CLIP_PLAYED ("Do nothing on clip play");

	private String name = "";
	
	TriggerType(String name){

		this.name = name;

	}

	public String toString(){

		return name;

	}


}
