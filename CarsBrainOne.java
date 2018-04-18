package car;

import java.io.IOException;

public class CarsBrainOne extends Cars {

	private static final long serialVersionUID = 1L;

	public CarsBrainOne() {
		super("Cars Brain One");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new RacerBrainOne[1];
		this.raceCar[0] = new RacerBrainOne(1);
		this.raceCar[0].setGround(ground);
		ground.setRacer(raceCar);		
	}
	/**
	 * Weiterentwickeln der Fahrzeuge
	 */
	public void development() {
		// Mutationen durchführen
		((RacerBrainOne)raceCar[0]).mutant();
	}
	/**
	 * Hauptmethode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsBrainOne c = new CarsBrainOne();
		try {
			c.go("Strecke0.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
