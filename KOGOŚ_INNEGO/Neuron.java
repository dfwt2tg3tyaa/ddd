import java.util.Random;


public class Neuron {

public double[] wagi;
double wsp_nauki=0.1;
double wsp_nauki_0=0.1;
public double odleglosc=0.0;
public double lambda= 1.0;
public double lambda_0= 1.0;
Neuron(int liczba_wag){
 	
 wagi= new double[liczba_wag];
 Random generator = new Random();
 for(int i=0;i<wagi.length;i++)
	{
	wagi[i]=-1+(generator.nextDouble()*15);
	}

}

public double odleglosc(double[] wzorzec)
{
	double tmp=0.0;
	
	for(int i=0;i<wzorzec.length;i++)
	{
		tmp=tmp+Math.pow(wzorzec[i]-wagi[i],2.0);
	}
	odleglosc=Math.sqrt(tmp);
    return Math.sqrt(tmp);
}

public void aktualizacja_zwyciezcy(double[] wzorzec)
{
	for(int i=0;i<wagi.length;i++)
	{
		wagi[i]=wagi[i]+wsp_nauki*(wzorzec[i]-wagi[i]);
	}	
}

public void aktualizacja_sasiadow(double[] wzorzec,Neuron Zwyciezca)
{
	for(int i=0;i<wagi.length;i++)
	{
		wagi[i]=wagi[i]+(sasiedztwo_gaussa(Zwyciezca)*wsp_nauki*(wzorzec[i]-wagi[i]));
	}	
}

public double sasiedztwo_gaussa(Neuron Zwyciezca)
{
	return Math.exp((-Math.pow(odleglosc(Zwyciezca.wagi),2.0))/(2*lambda*lambda));
}

public void promien(int iteracja,int max_iteracja)
{
	lambda=(lambda_0*(1-(iteracja/max_iteracja)))+1.0;
}

public void wsp_nauka(int iteracja,int max_iteracja)
{
	wsp_nauki= wsp_nauki_0*(1-(iteracja/max_iteracja));
}


}
