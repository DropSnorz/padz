package model;

public class PadGridModel {

	
	private int sizeX;
	private int sizeY;
	private Set defaultSet;
	private AudioClip padGrid[][];
	
	
	public PadGridModel(int x, int y, Set defaultSet){
		
		sizeX = x;
		sizeY = y;
		padGrid = new AudioClip[sizeX][sizeY];
		this.defaultSet = defaultSet;
		initModel();
	}
	

	private void initModel(){
		
		for(int i =0; i < sizeX; i++){
			
			for(int j = 0; j < sizeY;j++){
				
				padGrid[i][j] = new LoadedAudioClip(defaultSet);
			}
		}
	}
	
	public void addAudioClip(AudioClip audioClip, int x, int y){
		
		if(x < sizeX && y<sizeY){
			
			 padGrid[x][y] = audioClip;
		}
	}
	
	public AudioClip getAudioClip(int x, int y){
		
		if(x < sizeX && y<sizeY){
			
			return padGrid[x][y];
		}
		
		return null;
		
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

}


