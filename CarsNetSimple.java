package car;

import java.io.IOException;

public class CarsNetSimple extends Cars {

	public CarsNetSimple() {
		super("Cars-NetSimple");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new RacerNetSimple[3];
		for (int i=0; i < this.raceCar.length; i++) {
			this.raceCar[i] = new RacerNetSimple(i);
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
				((RacerNetSimple)(raceCar[j])).setBrain(((RacerNetSimple) (raceCar[carindex])).getBrain());
				((RacerNetSimple)raceCar[j]).mutant();
			}
		}		
	}
	/**
	 * Hauptmethode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsNetSimple c = new CarsNetSimple();
		try {
			c.go("Strecke0.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
