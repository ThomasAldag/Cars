package car;

import java.util.Arrays;
import java.util.concurrent.SynchronousQueue;

import tag.NeuroMatrix;
import tag.NeuroNet;
import tag.NeuroNetSimple;

/**
 * Ein Fahrzeug mit neuronalen Netzwerk.
 * 
 * Es gibt 15 Eingangsneuronen. Für jeden Sensor ein Neuron.
 * Es gibt 2 Ausgangsneuronen. Ein Neuron für die Richtung und ein Neuron für die Geschwindigkeit.
 * 
 * @author Thomas Aldag
 *
 */
public class RacerNetSimple extends Racer {

	protected NeuroNetSimple brain;			// das "Gehirn"
	protected int mask[] = {
			0b0000000000000001,
			0b0000000000000010,
			0b0000000000000100,
			0b0000000000001000,
			0b0000000000010000,
			0b0000000000100000,
			0b0000000001000000,
			0b0000000010000000,
			0b0000001000000000,
			0b0000010000000000,
			0b0000100000000000,
			0b0001000000000000,
			0b0010000000000000,
			0b0100000000000000,
			0b1000000000000000
	};

	public RacerNetSimple(int n) {
		super(n);
		brain = new NeuroNetSimple(15,20,6);
	}

	public NeuroNetSimple getBrain() {
		return brain;
	}
	
	public void setBrain(NeuroNetSimple n) {
		brain = new NeuroNetSimple(n);
	}
	/**
	 * Die Werte in der Entscheidungstabelle werden mutiert (10%)
	 */
	public void mutant() {
		brain.mutant(0.1);
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
		int[] input = new int[15];
		int[] output;
		if (stop) return;
		getLook(); // sehwert berechnen
		for (int i=0; i < 15; i++) 
			if ((sehwert & mask[i]) > 0)	input[i] = 1;
			else 							input[i] = 0;
		output = brain.query(input);

		direction = 0;
		if (output[0] > 0) direction = -5; // links
		if (output[1] > 0) direction = 5; // rechts
		if (output[2] > 0) direction = 0; // geradeaus
		speed = 2;
		if (output[3] > 0) speed = 2;
		if (output[4] > 0) speed = 4;
		if (output[5] > 0) speed = 6;
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
