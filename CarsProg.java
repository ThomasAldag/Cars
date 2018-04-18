package car;

import java.io.IOException;

/**
 * Das Fahrzeug wird durch ein Programm gesteuert.
 * 
 * @author Thomas Aldag
 *
 */
public class CarsProg extends Cars {
	
	public CarsProg() {
		super("Cars-Prog");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new RacerProg[1];
		raceCar[0] = new RacerProg(1);
		raceCar[0].setGround(ground);
		ground.setRacer(raceCar);
	}
	/**
	 * Hauptprogramm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsProg c = new CarsProg();
		try {
			c.go("Strecke0.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
