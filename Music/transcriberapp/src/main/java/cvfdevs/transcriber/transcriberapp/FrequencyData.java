package cvfdevs.transcriber.transcriberapp;

public class FrequencyData {
private long frequency;
public long getFrequency() {
	return frequency;
}
public void setFrequency(long frequency) {
	this.frequency = frequency;
}
public double getPeakIntensity() {
	return peakIntensity;
}
public void setPeakIntensity(double peakIntensity) {
	this.peakIntensity = peakIntensity;
}
public double getLocalMean() {
	return localMean;
}
public void setLocalMean(double localMean) {
	this.localMean = localMean;
}
public double getEnvironmentMean() {
	return environmentMean;
}
public void setEnvironmentMean(double environmentMean) {
	this.environmentMean = environmentMean;
}
private double peakIntensity;
private double localMean;
private double environmentMean;
}
