import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.terminal.ImageTerminal;

public class main {
	static int liczbaPunktow = 400;
	static int liczbaWzorcow = 3;
	static ArrayList<Pkt> punkty = new ArrayList<Pkt>();
	static ArrayList<Pkt> wzorce = new ArrayList<Pkt>();
	static double p1;
	static double p2;
	static float zakres = 10;
	static Random rand = new Random();
	static int lPetli = 1000000;
	static double tab2[][] = new double[liczbaPunktow][2];
	static JavaPlot p = new JavaPlot();
	static ImageTerminal png = new ImageTerminal();
	static int j = 0;

	public static void main(String[] args) throws IOException {

		//double xProsta;
		double alfa = 0.3;
		double odleglosc;

		// losowanie punktów
		losuj();
		losujWzorce();

		int index;

		for (int i = 0; i < lPetli; i++) {
			for (int k = 0; k < wzorce.size(); k++) {
				odleglosc = 100; // domyślnie min odleglosc = 100;
				index = 0;

				// ustalamy, który punkt ma najbliżej (nr indeksu)
				for (int j = 0; j < punkty.size(); j++) {
					if (sprawdzOdleglosc(j, wzorce.get(k)) < odleglosc) {
						index = j;
						odleglosc = sprawdzOdleglosc(j, wzorce.get(k));
					}
				}
				// modyfikujemy pozostałe w otoczeniu
				alfa = 0.00001;
				for (int j = 0; j < punkty.size(); j++) {
					if (j != index && sprawdzOdleglosc(j, punkty.get(index)) < 10) {
						modyfikuj(j, alfa, punkty.get(index));
					}
				}
				//alfa = 0.001;
				modyfikuj(index, alfa, wzorce.get(k));
			}

			if (i % 1000 == 0) {
				rysuj(j);
				j++;
			}
		}
	}

	private static double sprawdzOdleglosc(int j, Pkt pkt)
	{
		return (Math.sqrt(Math.pow((punkty.get(j).getY() - pkt.getY()), 2)
				+ Math.pow((punkty.get(j).getX() - pkt.getX()), 2)));
	}
	private static void modyfikuj(int index, double alfa, Pkt wzorzec) {
		punkty.get(index).setY(
				(1 - alfa) * punkty.get(index).getY() + alfa * wzorzec.getY());
		punkty.get(index).setX(
				(1 - alfa) * punkty.get(index).getX() + alfa * wzorzec.getX());
	}

	private static void rysuj(int i) throws IOException {
		for (int k = 0; k < punkty.size(); k++) {
			tab2[k][0] = punkty.get(k).getX();
			tab2[k][1] = punkty.get(k).getY();
		}
		p = new JavaPlot();
		DataSetPlot n = new DataSetPlot(tab2);
		p.addPlot(n);
		p.getAxis("x").setBoundaries(-zakres, zakres);
		p.getAxis("y").setBoundaries(-zakres, zakres);
		PlotStyle greenStyle = new PlotStyle();
		greenStyle.setPointType(1);
		greenStyle.setLineType(NamedPlotColor.GREEN);

		File plikPNG = new File("iad" + i + ".png");
		try {
			plikPNG.createNewFile();
			png.processOutput(new FileInputStream(plikPNG));
		} catch (IOException e) {
			e.printStackTrace();
		}

		p.setTerminal(png);
		p.plot();
		ImageIO.write(png.getImage(), "png", plikPNG);
	}

	private static void losuj() {
		for (int x = 0; x < liczbaPunktow; x++) {
			p1 = (double) ((rand.nextFloat() - 0.5) * zakres * 2);
			p2 = (double) ((rand.nextFloat() - 0.5) * zakres * 2);
			punkty.add(new Pkt(p1, p2));
		}
	}

	private static void losujWzorce() {
		for (int x = 0; x < liczbaWzorcow; x++) {
			p1 = (double) ((rand.nextFloat() - 0.5) * zakres * 2);
			p2 = (double) ((rand.nextFloat() - 0.5) * zakres * 2);
			wzorce.add(new Pkt(p1, p2));
		}
	}

}