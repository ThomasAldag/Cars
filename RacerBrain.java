package car;

import java.util.Arrays;

public class RacerBrain extends Racer {

	protected byte[] brain;			// das "Gehirn"

	public RacerBrain(int n) {
		super(n);
		brain = new byte[65536];
		initBrain();
	}
	/**
	 * Die Entscheidungstabelle initialisieren mit Werten zwischen 0 und 8.
	 */
	private void initBrain() {
		for (int i=0; i < brain.length; i++) brain[i] = (byte)(Math.random() * 9);
	}


	public byte[] getBrain() {
		return this.brain;
	}
	
	public void setBrain(byte[] b) {
		this.brain = Arrays.copyOf(b, b.length);
	}
	/**
	 * Die Werte in der Entscheidungstabelle werden mutiert (10%)
	 */
	public void mutant() {
		int anzmut;
		anzmut = Math.floorDiv(brain.length,10); // 10% mutieren
		for (int i=0; i < anzmut; i++) {
			brain[((int)(Math.random() * brain.length))] = (byte)(Math.random() * 9);
		}
	}
	/**
	 * Setzt Geschwindigkeit und Richtung
	 * 
	 * Die Daten werden aus dem "Gehirn" ermittelt.
	 * 
	 * Insgesamt gibt es neu Ausgänge. Diese neun Aktionen setzen sich aus der Geschwindigkeit und der Richtungsänderung zusammen.
	 * 
	 * Die Geschwindigkeit kann auf 2, 4 oder 6 Pixel gesetzt werden (3 Möglichkeiten)
	 * Die Richtung kann links herum -5, rechts herum 5 oder geradeaus 0 sein (3 Möglichkeiten)
	 * 
	 * Die Kombination ergibt  3x3 = 9 Möglichkeiten
	 * 
	 * Die Geschwindigkeit ist im Array s gespeichert. Die Richtung im Array d. Beide Arrays zusammen bilden alle Möglichkeiten ab.
	 */
	private void setSpeedDirection() {
		int[] s = {2, 2, 2, 4, 4, 4, 6, 6, 6};
		int[] d = {0, -5, 5, 0, -5, 5, 0, -5, 5};
		int v;
		if (stop) return;
		getLook(); // sehwert berechnen
		v = brain[sehwert];
		speed = s[v];
		direction = d[v];
		degree += direction;
		if (degree < 0) degree += 360;
		if (degree > 360) degree -= 360;
	}	
	/**
	 * Hauptroutine, die das Fahrzeug bewegt.
	 */
	public void drive() {
		if (stop) return;
		setSpeedDirection();
		super.drive();
	}
	
	
}
