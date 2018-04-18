package car;

import java.io.IOException;

public class CarsBrain extends Cars {

	public CarsBrain() {
		super("Cars-Brain");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new RacerBrain[3];
		for (int i=0; i < this.raceCar.length; i++) {
			this.raceCar[i] = new RacerBrain(i);
			this.raceCar[i].setGround(ground);
		}
		ground.setRacer(raceCar);		
	}
	/**
	 * Weiterentwickeln der Fahrzeuge
	 */
	public void development() {
		RacerBrain rb = (RacerBrain) raceCar[carindex];
		// Mutationen durchführen
		for (int j=0; j < raceCar.length; j++) {
			if (j != carindex) {
				if (raceCar[j].isWin()) finished=true;
				((RacerBrain)raceCar[j]).setBrain(rb.getBrain());
				((RacerBrain)raceCar[j]).mutant();
			}
		}		
	}
	/**
	 * Hauptmethode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsBrain c = new CarsBrain();
		try {
			c.go("Strecke0.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
