package car;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Die Rennstrecke auf der das Fahrzeug fahren soll.
 * 
 * @author Thomas Aldag
 *
 */
public class Ground extends Canvas {
	
	private static final long serialVersionUID = 1L;
	
	private Image backBuffer;
	private Graphics bBG;
	
	private BufferedImage img;
	private Racer[] raceCar;
	/**
	 * 
	 * @throws IOException
	 */
	public Ground() throws IOException {
		this.readStrecke("Strecke0.png");
	}
	/**
	 * Strecke aus der Grafikdatei lesen.
	 * 
	 * @param streckenname
	 * @throws IOException
	 */
	public void readStrecke(String streckenname) throws IOException {
		img = ImageIO.read(new File("recourse/"+streckenname));
	}
/*	
	 public static final boolean isGreyscaleImage(PixelGrabber pg) {
		 return pg.getPixels() instanceof byte[];
	 }
*/ 
	/**
	 * Ermittelt, ob der Pixel an der Position x,y schwarz ist.
	 * 
	 * Der Pixelwert wert mit 0xFFFFFF maskiert um die RGB-Werte zu ermitteln.
	 * 
	 * @param x
	 * @param y
	 * @return true, wenn der Pixel an der Stelle x,y schwarz ist.
	 */
	public boolean isBlack(int x, int y) {
		if (x < 0 || y < 0 ) return false;
		return (img.getRGB(x, y) & 0xFFFFFF) < 0x808080;
	}
	/**
	 * Ermittelt, ob der Pixel an der Position x,y blau ist.
	 *
	 * Der Pixelwert wert mit 0xFFFFFF maskiert um die RGB-Werte zu ermitteln.
	 * 
	 * @param x
	 * @param y
	 * @return true, wenn der Pixel an der Stelle x,y blau ist.
	 */	
	public boolean isBlue(int x, int y) {
		if (x < 0 || y < 0 ) return false;
		int p = img.getRGB(x, y) & 0xFFFFFF;
		return p <= 0x0000FF && p >=0x0000AA;
	}
	/**
	 * Setzt die Liste der Fahrzeuge
	 * 
	 * @param r
	 */
	public void setRacer(Racer[] r) {
		this.raceCar = r;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(img.getWidth(this), img.getHeight(this));
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void update( Graphics g ) {
		paint( g );
	}
	/**
	 * Strecke und Fahrzeuge zeichnen
	 */
	public void paint(Graphics g) {
		// Einen Buffer für die Ausgabe erstellen sofern er nicht vorhanden ist
		if( backBuffer == null ) {
			backBuffer = createImage( getWidth(), getHeight() );
			bBG = backBuffer.getGraphics();
		}
		// Die Strecke in den Buffer zeichnen
		if (img != null) bBG.drawImage(img, 0, 0, this);		
		if (this.raceCar != null) {
			for (int i=0; i<this.raceCar.length; i++) this.raceCar[i].paintIt(bBG);
		}
		// Die Zeichenfläche löschen
		g.clearRect(0, 0, img.getWidth(), img.getHeight());
		// und den Buffer zeichnen
		g.drawImage( backBuffer, 0, 0, this );
	}

}
