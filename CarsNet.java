package car;

import java.io.IOException;

public class CarsNet extends Cars {

	public CarsNet() {
		super("Cars-Net");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new RacerNet[3];
		for (int i=0; i < this.raceCar.length; i++) {
			this.raceCar[i] = new RacerNet(i);
			this.raceCar[i].setGround(ground);
		}
		ground.setRacer(raceCar);		
	}
	/**
	 * Weiterentwickeln der Fahrzeuge
	 */
	public void development() {
		// Mutationen durchführen
		for (int j=0; j < raceCar.length; j++) {
			if (j != carindex) { // carindex zeigt auf das beste Fahrzeug
				if (raceCar[j].isWin()) finished=true;
				((RacerNet)(raceCar[j])).setBrain(((RacerNet) (raceCar[carindex])).getBrain());
				((RacerNet)raceCar[j]).mutant();
			}
		}		
	}
	/**
	 * Hauptmethode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsNet c = new CarsNet();
		try {
			c.go("Strecke0.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
