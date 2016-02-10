package Model;

public class GainEffect implements IEffect {
	double gainValue=1;
	@Override
	public void ProcessDoubleReplacing(double[] inputData/*Tableau*/, int nFrames/*Taille du tableau*/) {
		// TODO Auto-generated method stub, parcourir le tableau
		// TODO et modifier les valeurs 
		for (int i=0; i<nFrames;i++){
			inputData[i]= (inputData[i]*gainValue);
		}
	}
	public void setGain(double gainValue){
		this.gainValue=gainValue;
	}
}
