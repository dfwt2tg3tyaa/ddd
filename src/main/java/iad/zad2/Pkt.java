package iad.zad2;

public class Pkt {
		private double x;
		private double y;
		Pkt() {
		}
		public Pkt(double f, double g) {
			this.x = f;
			this.y = g;
		}
//		public int getLabel() {
//			return label;
//		}
//		public void setLabel(int label) {
//			this.label = label;
//		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double[][] getDouble() {
			double b[][] = new double[1][2];
			b[0][0] = getX();
			b[0][1] = getY();
			return b;
		}
		
		
		
	}