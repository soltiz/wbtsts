package cvfdevs.transcriber.transcriberapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cvfdevs.common.tests.toolstests.CvfTests;

public class SoundAnalysis {
	protected static final Logger log = LoggerFactory.getLogger(SoundAnalysis.class);

	public static Complex[] bufferFFT(byte[] buffer, long cnt) {

		if (cnt % 2 != 0) {
			throw new RuntimeException("unaligned buffer");
		}

		int bufsz = 1;
		for (int pow = 2; pow < buffer.length / 2; pow = 2 * pow) {
			bufsz = pow;
		}
		;

		double[] fftdata = new double[bufsz];

		int maxit = 0;
		for (int cursor = 0; cursor < cnt; cursor = cursor + 2) {
			int curval = buffer[cursor] + 255 * buffer[cursor + 1];
			if (curval > maxit)
				maxit = curval;
			if (cursor < 2 * bufsz) {
				fftdata[cursor / 2] = curval;
			}
		}

		FastFourierTransformer fft = new FastFourierTransformer(
				DftNormalization.STANDARD);

		Complex[] freqs = fft.transform(fftdata, TransformType.FORWARD);

		return freqs;
	}

	public static Collection<FrequencyData> peakFrequencies(Complex [] freqsIntensities, Double minIntensity, Integer nbPeaks, Integer windowSize)
	  	{
	  		Collection<FrequencyData> foundfreqs = new ArrayList<FrequencyData>();
	  		int nbPeaksFound=0;
	  		FrequencyData minPeak=null;
			Double lowestPeakIntensity=0.0;
	  		FrequencyData nearestPeak = new FrequencyData();
	  		nearestPeak.setPeakIntensity(0.0);
	  		nearestPeak.setFrequency(0);
	  		
	  		long l;
	  		for (int f=0; f<freqsIntensities.length; f++) {
	  			double intensity=freqsIntensities[f].abs();
	  			if ((intensity>lowestPeakIntensity) && (intensity>minIntensity)){
	  				// cas partiulier : nouveau pic dans la fenêtre de proximité à partir du dernier pic
	  				// on garde le plus haut des deux
	  				if ( (f-nearestPeak.getFrequency())<windowSize ) {
	  					if (intensity>nearestPeak.getPeakIntensity()){
	  						nearestPeak.setFrequency(f);
	  						nearestPeak.setPeakIntensity(intensity);
	  					}
	  				}
	  				else // on n 'est plus dans la fenêtre
	  				{
	  					nearestPeak=new FrequencyData();
	  					nearestPeak.setFrequency(f);
	  					nearestPeak.setPeakIntensity(intensity);
	  					foundfreqs.add(nearestPeak);
	  					
	  					// si on   déjà le nombre max de pics, 
  						if (foundfreqs.size()>=nbPeaks) {
  							
  							// on retire le pic le plus bas de la liste
  							foundfreqs.remove(minPeak);
  							
  							// on recalcule le plus bas pic
  	  						
  							minPeak=null;
  							for (FrequencyData lookupFreq : foundfreqs) {
  								if ((minPeak==null) || (minPeak.getPeakIntensity()>lookupFreq.getPeakIntensity())) {
  									minPeak=lookupFreq;
  								}
  							}
  						}
	  				}
	  			}
	  		}
	  	return foundfreqs;
	  }
}
