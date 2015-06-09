import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.ImageTerminal;


public class Maine {

	
	 static ArrayList<double[]> wzorce_uczace = new ArrayList<double[]>();
	public static void main(String[] args) {
		ArrayList<double[]> wzorce_uczace = new ArrayList<double[]>();
		ArrayList<Neuron> Neurony = new ArrayList<Neuron>();
		double odleglosc_min=1000.0;
		int liczba_neuronow=10;
		int liczba_epok=5;
		Neuron Zwyciezca= new Neuron(2);
		double[][] w1={{2,6},{10,1},{8,6}};
		double[][] w2= new double[10][2];
		double[] w4={2,6};
		double[] w5={10,1};
	    double[] w3={8,6};
		wzorce_uczace.add(w4);
		wzorce_uczace.add(w5);
		wzorce_uczace.add(w3);
	/*
		for(int i=0;i<wzorce_uczace.size();i++)
		{
			for(int j=0;j<wzorce_uczace.get(i).length;j++)
			{
				double tmp=0.0;
				for(int k=0;k<wzorce_uczace.get(i).length;k++)
				{
					tmp=tmp+(wzorce_uczace.get(i)[k]*wzorce_uczace.get(i)[k]);
				}
				wzorce_uczace.get(i)[j]=wzorce_uczace.get(i)[j]/(Math.sqrt(tmp));
			}
		}
	
	*/
		
	for(int i=0;i<liczba_neuronow;i++)
	{
		Neuron n= new Neuron(2);
		Neurony.add(n);
		w2[i][0]=n.wagi[0];
		w2[i][1]=n.wagi[1];
	}
	//rysowanie(w1,w2,"poczatek");
	
	
	for(int k=0;k<liczba_epok;k++)
	{
	for(int j=0;j<wzorce_uczace.size();j++)
	{
		for(int i=0;i<Neurony.size();i++)
		{
			if(odleglosc_min>Neurony.get(i).odleglosc(wzorce_uczace.get(j)))
			{
				odleglosc_min=Neurony.get(i).odleglosc(wzorce_uczace.get(j));
			}	
		}
	
		for(int l=0;l<Neurony.size();l++)
		{
			if(odleglosc_min==Neurony.get(l).odleglosc)
			{
				//System.out.println("Aktualizuje neuron o wagach: "+Neurony.get(l).wagi[0]+" "+Neurony.get(l).wagi[1]);
				Neurony.get(l).aktualizacja_zwyciezcy(wzorce_uczace.get(j));
				Neurony.get(l).promien(k, liczba_epok);
				Neurony.get(l).wsp_nauka(k, liczba_epok);
				Zwyciezca=Neurony.get(l);
				break;
			}
			
		}
		
		for(int l=0;l<Neurony.size();l++)
		{
			Neurony.get(l).aktualizacja_sasiadow((wzorce_uczace.get(j)), Zwyciezca);
			Neurony.get(l).promien(k, liczba_epok);
			Neurony.get(l).wsp_nauka(k, liczba_epok);
		}
		
	odleglosc_min=1000.0;	
		
	}
	
	for(int i=0;i<10;i++)
	{
		w2[i][0]=Neurony.get(i).wagi[0];
		w2[i][1]=Neurony.get(i).wagi[1];
	}
	
	rysowanie(w1,w2,"lol"+k);
	}
	
	
	
	//rysowanie(w1,w2,"koniec");
	
	}


	public static void rysowanie(double[][] tab,double[][]tab2,String nazwa_pliku) 
	{

		ImageTerminal png = new ImageTerminal();
		File file = new File(nazwa_pliku+".png");
        try {
            file.createNewFile();
            FileInputStream as = new FileInputStream(file);
            png.processOutput(as);
            as.close();
        } catch (FileNotFoundException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            System.err.print(ex);
        }
        
        JavaPlot p = new JavaPlot("C:/gnuplot/bin/wgnuplot.exe");
        p.setTerminal(png);
        PlotStyle myStyle2 = new PlotStyle(Style.POINTS);
        DataSetPlot rysuj2 = new DataSetPlot( tab );
        rysuj2.setPlotStyle(myStyle2);
        PlotStyle myStyle = new PlotStyle(Style.POINTS);
        DataSetPlot rysuj = new DataSetPlot( tab2 );
        rysuj.setPlotStyle(myStyle);
        
        p.getAxis("x").setLabel("");
        p.getAxis("y").setLabel("");
        p.getAxis("x").setBoundaries(-5.0, 20.0);
        p.getAxis("y").setBoundaries(-5.0, 20.0);
        p.addPlot(rysuj2);
        p.addPlot(rysuj);
        p.setPersist(false);
        p.plot();
        try {
            ImageIO.write(png.getImage(), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

public static void normalizacja()
{
	
	for(int i=0;i<wzorce_uczace.size();i++)
	{
		for(int j=0;j<wzorce_uczace.get(i).length;j++)
		{
			double tmp=0.0;
			for(int k=0;k<wzorce_uczace.get(i).length;k++)
			{
				tmp=tmp+(wzorce_uczace.get(i)[k]*wzorce_uczace.get(i)[k]);
			}
			wzorce_uczace.get(i)[j]=wzorce_uczace.get(i)[j]/(Math.sqrt(tmp));
		}
	}
}


}
