package car;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Das Fahrzeug wird getestet.
 * 
 * Siehe Testmethoden
 * 
 * @author Thomas Aldag
 *
 */
public class CarsTest extends Cars {

	public CarsTest() {
		super("Cars Test");
	}
	/**
	 * Fahrzeuge erzeugen und zur Rennstrecke hinzufügen.
	 */
	public void initRacer() {
		raceCar = new Racer[1];
		raceCar[0] =new Racer(1);
		this.raceCar[0].setGround(this.ground);
		ground.setRacer(raceCar);		
	}
	/**
	 * Geradeaus nach rechts fahren
	 */
	public void test1() {
		raceCar[0].setPosition(200, 100, 0);
		raceCar[0].setSpeed(6);
		for (int i=0; i < 20; i++) {
			waiting();
			System.out.println("runde="+i);
			raceCar[0].drive();
			ground.repaint();
		}
	}
	/**
	 * Geradeaus nach links fahren
	 */
	public void test2() {
		raceCar[0].setPosition(200, 100, 180);
		raceCar[0].setSpeed(6);
		for (int i=0; i < 20; i++) {
			waiting();
			System.out.println("runde="+i);
			raceCar[0].drive();
			ground.repaint();
		}		
	}
	/**
	 * Im Kreis nach links fahren
	 */
	public void test3() {
		raceCar[0].setPosition(200, 200, 0);
		raceCar[0].setSpeed(4);
		for (int i=0; i < 60; i++) {
			waiting();
			raceCar[0].left();
			System.out.println("runde="+i);
			raceCar[0].drive();
			ground.repaint();
		}		
	}
	/**
	 * Im Kreis nach rechts fahren
	 */
	public void test4() {
		raceCar[0].setPosition(200, 200, 0);
		raceCar[0].setSpeed(4);
		for (int i=0; i < 60; i++) {
			waiting();
			raceCar[0].right();
			System.out.println("runde="+i);
			raceCar[0].drive();
			ground.repaint();
		}		
	}
	/**
	 * Hauptprogramm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CarsTest rt = new CarsTest();
		rt.test1();
		rt.test2();
		rt.test3();
		rt.test4();
	}

}
