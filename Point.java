package car;

public class Point {

	public double x=0;
	public double y=0;
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void translate(Point p) {
		this.x += p.x;
		this.y += p.y;
	}
	
	public void translate(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void translate(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void translateMinus(Point p) {
		this.x -= p.x;
		this.y -= p.y;
	}
	
	public void rotate0(int grad) {
		double d = Math.toRadians(grad);
		double cosinus = Math.cos(d);
		double sinus = Math.sin(d);
		double xn = cosinus * x - sinus * y;
		double yn = sinus * x + cosinus * y;
		x = xn;
		y = yn;
	}
	
	public int intX() {
		return (int) Math.round(x);
	}
	
	public int intY() {
		return (int) Math.round(y);
	}
}
