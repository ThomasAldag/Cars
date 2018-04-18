package car;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Arrays;

/**
 * Es wird ein Wagen mit seiner Position und Sicht definiert.
 * 
 * Um die Berechnung möglichst einfach zu halten besteht die Sicht aus Linen mit einen bestimmten Abstand
 * es wird nur der Punkt ermittelt. * 
 * 
 * @author Thomas Aldag
 *
 */
public class Racer {

	private Ground ground;				//
	private int nr;						// zur unterscheidung der Fahrzeuge
	protected int degree=0;				// Richtung in Grad
	protected int direction=0;			// lenkung in grad -5 = links 5 = rechts
	protected int speed=2;				// Geschwindigkeit in Pixel
	protected int sehwert=1;			// Sehwert
	protected int distance=0;			// Zurückgelegte Strecke (aufaddieren der Speedwerte)
	protected boolean stop=false;		// der Wagen ist außerhalb der Strecke
	protected boolean win=false;		// der Wagen merkt sich, ob er über die Ziellinie gefahren ist
	private int[][] objekt = {
			{ 10, 5 },		// vorne links
			{ 10, -5 },		// vorne rechts
			{ -10, -5 },	// hinten rechts
			{ -10, 5 }		// hinten links
	};
	private Point[] pob;
	/**
	 * Punkte die Informationen liefern. Jeder Punkt liefert den Farbwert (0 oder 1) zurück (schwarz oder weiß)
	 */
	private int[][] sensor = {
			{ 20,  12 }, { 30,  12 }, { 40,  12 },
			{ 20,   6 }, { 30,   6 }, { 40,   6 },
			{ 20,   0 }, { 30,   0 }, { 40,   0 },
			{ 20,  -6 }, { 30,  -6 }, { 40,  -6 },
			{ 20, -12 }, { 30, -12 }, { 40, -12 }

	};
	private Point[] psensor;
	
	public Racer(int n) {
		init();
		nr=n;
	}
	
	private void init() {
		pob = new Point[objekt.length];
		for (int i=0; i < pob.length; i++) pob[i] = new Point(objekt[i][0],objekt[i][1]);
		psensor = new Point[sensor.length];
		for (int i=0; i < psensor.length; i++) psensor[i] = new Point(sensor[i][0],sensor[i][1]);
	}
	/**
	 * stop zurücksetzen und die zurückgelegte Strecke auf 0 setzen.
	 */
	public void initStrecke() {
		stop = false;
		distance = 0;
	}
	/**
	 * Den Untergrund für das Fahrzeug setzen, damit das Fahrzeug Informationen vom Untergrund erfragen kann.
	 * 
	 * @param g Der Untergrund
	 */
	public void setGround(Ground g) {
		this.ground = g;
	}
	/**
	 * Geschwindigkeit in Pixel setzen.
	 * 
	 * @param s Geschwindigkeit
	 */
	public void setSpeed(int s) {
		speed = s;
	}
	/**
	 * Das Fahrzeug auf eine Position setzen. Der Punkt links, oben ist der Punkt 0,0
	 * Außerdem wird die Ausrichtung des Fahrzeugs gesetzt.
	 * 
	 * @param x
	 * @param y
	 * @param degree
	 */
	public void setPosition(int x, int y, int degree) {
		stop = false;
		win = false;
		sehwert = 1;
		this.degree = degree;
		for (int i=0; i < pob.length; i++) {
			pob[i].setXY(objekt[i][0], objekt[i][1]);
			pob[i].rotate0(degree);
			pob[i].translate(x, y);
		}
		for (int i=0; i < psensor.length; i++) {
			psensor[i].setXY(sensor[i][0], sensor[i][1]);
			psensor[i].rotate0(degree);
			psensor[i].translate(x,y);
		}
	}
		
	public int getNummer() {
		return this.nr;
	}
	
	/**
	 * Hat das Fahrzeug die Ziellinie erreicht?
	 * 
	 * @return true, wenn die Sensoren die Ziellinie ermittelt haben
	 */
	public boolean isWin() {
		return this.win;
	}
	/**
	 * Wurde das Fahrzeug angehalten?
	 * 
	 * Das Fahrzeug wird angehalten, wenn es mit einer Ecke auf den weißen Untergrund stößt.
	 * 
	 * @return true, wenn das Fahrzeug an den weißen Untergrund stößt.
	 */
	public boolean isStop() {
		return this.stop;
	}
	/**
	 * Hauptroutine die das Fahrzeug bewegt.
	 */
	public void drive() {
		rotate(direction);
		goStraightOn();
	}
	/**
	 * Das Fahrzeug beweegt sich speed-Punkte in die Richtung (grad)
	 */
	private void goStraightOn() {
		Point pd;
		pd = new Point(speed,0);
		pd.rotate0(degree);
		for (int i=0; i < pob.length; i++) pob[i].translate(pd);
		for (int i=0; i < psensor.length; i++) psensor[i].translate(pd);
		distance += speed;		
	}
	/**
	 * Das Fahrzeug dreht sich auf eines der beiden hinteren Rädern
	 * 
	 * @param grad der Grad um 
	 */
	private void rotate(int grad) {
		Point pt;
		if (grad == 0) return;
		// rechts herum
		if (grad > 0) pt = new Point(pob[3]); // rechte Ecke bleibt fest
		// links herum
		else pt = new Point(pob[2]);
		for (int i=0; i < pob.length; i++) {
			pob[i].translateMinus(pt);
			pob[i].rotate0(grad);
			pob[i].translate(pt);
		}
		for (int i=0; i < psensor.length; i++) {
			psensor[i].translateMinus(pt);
			psensor[i].rotate0(grad);
			psensor[i].translate(pt);
		}
	}
	
	/**
	 * Ermittelt das Sichtfeld und den Seh-Wert.
	 * 
	 * Gestoppte Fahrzeuge liefern immer den Wert 0
	 * 
	 * Der Sehwert ergibt sich aus den Sensoren.
	 * Jeder Sensor der einen schwarzen Untergrund "sieht"
	 * liefert den Wert 2 hoch Position. Z.B. liefert der
	 * Sensor oben links den Wert 2^14 = ...
	 * 
	 * 02 01 00
	 * 05 04 03    +--------+
	 * 08 07 06 <- |Fahrzeug|
	 * 11 10 09    +--------+
	 * 14 13 12
	 * 
	 * @return 
	 */
	public int getLook() {
		int value = 0;
		int wert = 1;
		if (stop) {
			sehwert = 0;
			return 0;
		}
		for (int i=0; i < psensor.length; i++) {
			if (ground.isBlue(psensor[i].intX(),psensor[i].intY())) win=true;
			if (ground.isBlack(psensor[i].intX(),psensor[i].intY())) value += wert;
			wert *= 2;			
		}
		sehwert = value;
		return sehwert;
	}
	public int getSpeed() {
		return speed;
	}
	/**
	 * 
	 * @return
	 */
	public int getStrecke() {
		return distance;
	}
	/**
	 * 
	 * @return
	 */
	public int getSehwert() {
		return sehwert;
	}
	/**
	 * 
	 */
	public void left() {
		direction = -5;
		degree += direction;
		if (degree < 0) degree += 360;
	}
	/**
	 * 
	 */
	public void right() {
		direction = 5;
		degree += direction;
		if (degree > 360) degree -= 360;
	}
	/**
	 * Dreht das Fahrzeug und zeichnet es anschließend.
	 * Das Fahrzeug stoppt, wenn ein Eckpunkt des Fahrzeug die Fahrbahn verlassen hat.
	 * 
	 * @param g
	 */
	public void paintIt(Graphics g) {
		// Das Fahrzeug zeichnen in rot
		Polygon p = new Polygon();
		g.setColor(new Color(200, 0, 0));
		for (int i = 0; i < pob.length; i++) {
			p.addPoint(pob[i].intX(), pob[i].intY());
			if (!ground.isBlack(pob[i].intX(), pob[i].intY())) stop = true;
		}
		g.drawPolygon(p);
		// Die Sensoren Zeichnen in grau
		g.setColor(new Color(150, 150, 150));
		for (int i = 0; i < psensor.length; i++) g.drawOval(psensor[i].intX()-3,psensor[i].intY()-3, 5, 5);
	}
	
	
}
