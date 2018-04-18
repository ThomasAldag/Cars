package car;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Schleife um das Fahrzeug zu bewegen.
 * 
 * @author Thomas Aldag
 *
 */
public class Cars extends Frame {

	private static final long serialVersionUID = 1L;
	// Die Strecke
	protected Ground ground;
	// Eine Liste von Fahrzeugen
	protected Racer[] raceCar;
	// Der Index des besten Fahrzeugs
	protected int carindex;
	// Ein Fahrzeug ist im Ziel angekommen
	protected boolean finished=false;
	/**
	 * Konstruktor. Das Fenster wird angelegt und die Fahrzeuge initialisiert.
	 * 
	 * @param title Der Fenstertitel
	 */
	public Cars(String title) {
		super(title);
	    addWindowListener(
	      new WindowAdapter() {
	        public void windowClosing( WindowEvent ev ) {
	          dispose();
	          System.exit( 0 ); } } );
		try {
			screen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initRacer();
	}
	/**
	 * Fenster erstellen
	 * 
	 * @throws IOException 
	 */
	private void screen() throws IOException {
		setVisible(true);
		setSize(600,600);
		ground = new Ground();
		add (ground);
		pack();
	}
	/**
	 * Die folgenden beiden Methoden müssen überlagert werden.
	 */
	public void initRacer() {}
	public void development() {}
	/**
	 * Schleife, bis ein Fahrzeug das Ziel erreicht hat.
	 * 
	 * @throws IOException
	 */
	public void go(String streckenname) throws IOException {
		int runde=0;
		ground.readStrecke(streckenname);
		finished=false;
		while (!finished) {
			System.out.println("Runde="+(++runde)+" max.Strecke="+raceCar[carindex].getStrecke());
			aRound();
			carindex = bestCar();
			development();
		}
	}
	/**
	 * Die Fahrzeuge werden auf die Startposition gesetzt.
	 */
	private void startPositionRacer() {
		for (int i=0; i < raceCar.length; i++) {
			raceCar[i].setPosition(290, 224,180);
			raceCar[i].initStrecke();
		}		
	}
	/**
	 * Warteschleife
	 */
	protected void waiting() {
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}	
	/**
	 * Eine Runde durchlaufen
	 */
	private void aRound() {
		boolean weiter=true;
		startPositionRacer();
		while (weiter) {
			waiting();
			weiter = false;
			for (int j=0; j < this.raceCar.length; j++) {
				raceCar[j].drive();
				if (!weiter) weiter=!raceCar[j].isStop();
				if (raceCar[j].isWin()) {
					finished = true;
					return;
				}
			}
			ground.repaint();
		}		
	}
	/**
	 * Heraussuchen des besten Wagen.
	 * 
	 * D.h. den Wagen mit der längsten Strecke.
	 * 
	 * @return Index des Wagens
	 */
	private int bestCar() {
		int strecke=0;
		int ci=0;
		System.out.print("Best Car ");
		for (int i=0; i < raceCar.length; i++) {
			System.out.print(" " + raceCar[i].getStrecke()+" ("+raceCar[i].getSpeed()+")");
			if (strecke <= raceCar[i].getStrecke()) {
				ci = i;
				strecke = this.raceCar[i].getStrecke();
			}
		}
		System.out.println(" Best "+ci);
		return ci;
	}
	
}
