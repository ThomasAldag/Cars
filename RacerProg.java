package car;

import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * Die Logik der Steuerung
 * 
 * 1. Sind auf der linken Seite die ersten beiden Punkte weiß, dann steuer nach rechts
 * 2. Sind auf der rechten Seite die ersten beiden Punkte weiß, dann steuer nach links
 * 3. Sind die ersten beiden Reihen schwarz, dann setze die Geschwindigkeit auf 6
 * 4. Ist  die ersten Reihe schwarz, dann setze die Geschwindigkeit auf 4
 * 5. Die normale Geschwindigkeit ist 2
 * 
 * Änderung 1
 * 
 * Nur lenken, wenn alle drei Sensoren auf einer Seite auf weißem Untergrund zeigen.
 * 
 * @author Thomas Aldag
 */
public class RacerProg extends Racer {

	public RacerProg(int n) {
		super(n);
	}
	/**
	 * Setzt Geschwindigkeit und Richtung entsprechend der Regeln
	 * 
	 * 02 01 00
	 * 05 04 03    +--------+
	 * 08 07 06 <- |Fahrzeug|
	 * 11 10 09    +--------+
	 * 14 13 12
	 * 
	 */
	private void setSpeedDirection() {
		if (stop) return;
		getLook(); // sehwert berechnen
		direction = 0;
		// Regel  1. Sind auf der linken Seite die ersten beiden Punkte weiß, dann steuer nach rechts
		//if ((sehwert & 0b110000000000000) == 0) right();
		// Verbesserte Regel
		if ((sehwert & 0b111000000000000) == 0) right();
		// Regel 2. Sind auf der rechten Seite die ersten beiden Punkte weiß, dann steuer nach links
		//if ((sehwert & 0b000000000000110) == 0) left();
		// Verbesserte Regel
		if ((sehwert & 0b000000000000111) == 0) left();
		// Regel 5
		speed = 2;
		// Regel 4. Ist  die ersten Reihe schwarz, dann setze die Geschwindigkeit auf 4
		if ((sehwert & 0b000100100100000) == 0b000100100100000) speed = 4;
		// Regel 3: Sind die ersten beiden Reihen schwarz, dann setze die Geschwindigkeit auf 5
		if ((sehwert & 0b110110110110110) == 0b110110110110110) speed = 6;
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
