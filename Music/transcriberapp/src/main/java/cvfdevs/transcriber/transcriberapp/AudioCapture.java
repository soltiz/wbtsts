package cvfdevs.transcriber.transcriberapp;

import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.EnumControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class AudioCapture extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4056053478222132329L;
	boolean stopCapture = false;
	  //ByteArrayOutputStream byteArrayOutputStream;
	  TargetDataLine targetDataLine;
	  AudioInputStream audioInputStream;
	  SourceDataLine sourceDataLine;
	    JList<Mixer.Info> inputMixerList=new JList<Mixer.Info>(getAvailableMixers(TargetDataLine.class));
	    JList<Mixer.Info> outputMixerList=new JList<Mixer.Info>(getAvailableMixers(SourceDataLine.class));
		JList<AudioFormat> inputAudioFormatList=new JList<AudioFormat>() ;
		JList<AudioFormat> outputAudioFormatList=new JList<AudioFormat>() ;
		JProgressBar meter = new JProgressBar(0,100);
		
		JProgressBar freqmeter = new JProgressBar(0,100);
	    JLabel mainfreq = new JLabel("?");
	    JLabel capturedBytesLabel = new JLabel(" ");
	    Long capturedBytes=0L;
	    File soundFile;
	  private Mixer.Info getInputMixerInfo() {
		  return  inputMixerList.getSelectedValue();
	  }
	  
	  private Mixer.Info getOutputMixerInfo() {
		  return  outputMixerList.getSelectedValue();
	  }
	  
	  
	  private TracerCanvas tracerCanvas;
	  private TracerCanvas freqsCanvas;
	  private JLabel mainFreqLabel=new JLabel();
	  
	  private AudioFormat getInputAudioFormat() {
		  return inputAudioFormatList.getSelectedValue();
	  }
	  
	  private AudioFormat getOutputAudioFormat() {
		  return outputAudioFormatList.getSelectedValue();
	  }
	  private void updateAvailableCaptureModes() {
		  inputAudioFormatList.setListData(getSupportedFormats(TargetDataLine.class, getInputMixerInfo()));
		  inputAudioFormatList.setSelectedIndex(0);
		  
	  }
	  
	  private void updateAvailableReplayModes() {
		  outputAudioFormatList.setListData(getSupportedFormats(SourceDataLine.class, getOutputMixerInfo()));
		  outputAudioFormatList.setSelectedIndex(0);
		  
	  }
	  	
	  private 		JSlider threshold = new JSlider(0,100,50);
	  private Container masterPane;
	  private void processBuffer(byte[] buffer, long cnt) {
		  int maxfreq=0;
			 
		  if (cnt % 2 != 0) {
			  throw new RuntimeException("unaligned buffer");
		  }
		 // System.out.println("Buffer processed size : " + buffer.length);
		  
		  int bufsz=1;
		  for (int pow=2 ;  pow< buffer.length/2 ;pow=2*pow) {bufsz=pow;};
		
		  
		  double[] fftdata = new double[bufsz];
		  
		  int maxit=0;
		  for (int cursor=0; cursor<cnt; cursor=cursor+2) {
			  int curval = buffer[cursor]+255*buffer[cursor+1];
			  if (curval>maxit) maxit=curval;
			  if (cursor<2*bufsz) {
				  fftdata[cursor/2]=curval;
			  }
		  }
		 
		 		 
		  FastFourierTransformer fft=new FastFourierTransformer(DftNormalization.STANDARD);
		  
		 Complex [] freqs = fft.transform(fftdata,TransformType.FORWARD);
		 
		 double maxoccur=0;
		 for (int freq=0;freq<bufsz;freq++) {
			 if (freqs[freq].getReal()>maxoccur) {
				 maxfreq=freq;
				 maxoccur=freqs[freq].getReal();
			 }
		 }
		 int intensity=new Double(Math.log10(maxoccur)).intValue();
		 if (intensity>5) {
			 mainfreq.setText("freq="+maxfreq + "   intensity="+ intensity + " value=" + maxit);
		 } 
		  meter.setValue(maxit/600);
		  tracerCanvas.addSample(maxit/600);
		  if (maxit/600 > threshold.getValue()) {
			  warn();
			  mainFreqLabel.setText("Freq=" + (44000/maxfreq) + " Hz");
		  }
		  freqsCanvas.setData(freqs);
		  freqsCanvas.repaint();
		  tracerCanvas.repaint();
	  }
	  private void warn()
	  {
		  System.out.println("ALERTE");
	  }
	  
	  public AudioCapture(){//constructor
	    final JButton captureBtn =
	                          new JButton("Capture");
	    final JButton stopBtn = new JButton("Stop");
	    final JButton playBtn =
	                         new JButton("Playback");
	    
	     masterPane=getContentPane();
	    BoxLayout mainLayout=new BoxLayout(masterPane,BoxLayout.Y_AXIS);
	    masterPane.setLayout(mainLayout);
	    
	    
	    JPanel toolbar = new JPanel();
	    BoxLayout tbLayout=new BoxLayout(toolbar,BoxLayout.X_AXIS);
	    toolbar.setLayout(tbLayout);
	    
	    
	    masterPane.add(toolbar);
	    
	    JPanel replay = new JPanel();
	    BoxLayout repLayout=new BoxLayout(replay,BoxLayout.X_AXIS);
	    replay.setLayout(repLayout);
	    
	    
	    masterPane.add(replay);
	        
		inputMixerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inputMixerList.setEnabled(true);
		inputMixerList.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				updateAvailableCaptureModes();
			}});
		inputMixerList.setSelectedIndex(0);
		
	    //getContentPane().add(inputMixerList);
		toolbar.add(inputMixerList);
	    
		outputMixerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outputMixerList.setEnabled(true);
		outputMixerList.addListSelectionListener(new ListSelectionListener(){
			
			public void valueChanged(ListSelectionEvent arg0) {
				updateAvailableReplayModes();
			}});
		outputMixerList.setSelectedIndex(0);
		
		replay.add(outputMixerList);
		   
		
	    inputAudioFormatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    inputAudioFormatList.setEnabled(true);
	    toolbar.add(inputAudioFormatList);
	    
	    
	    outputAudioFormatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    outputAudioFormatList.setEnabled(true);
	    replay.add(outputAudioFormatList);
	    
	    
	    toolbar.add (mainfreq);
	    toolbar.add (capturedBytesLabel);
	    
	    
	    meter.setValue(78);
	    meter.setVisible(true);
	    masterPane.add(meter);
	    
	    captureBtn.setEnabled(true);
	    stopBtn.setEnabled(false);
	    playBtn.setEnabled(false);
	    
	    
	    
	    GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsDevice[] gs = ge.getScreenDevices(); 
		GraphicsDevice gd = gs[0]; 
		GraphicsConfiguration gc =  gd.getDefaultConfiguration(); 
		JFrame f = new JFrame(gc); 
		tracerCanvas = new TracerCanvas(gc); 
		masterPane.add(tracerCanvas); 
		
		freqsCanvas= new TracerCanvas(gc); 
		masterPane.add(freqsCanvas); 

		 mainFreqLabel = new JLabel();
		masterPane.add(mainFreqLabel);
		
		threshold.setEnabled(true);
		masterPane.add(threshold);
	    

	    //Register anonymous listeners
	    captureBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                                 ActionEvent e){
	          captureBtn.setEnabled(false);
	          stopBtn.setEnabled(true);
	          playBtn.setEnabled(false);
	          //Capture input data from the
	          // microphone until the Stop button is
	          // clicked.
	          captureAudio();
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()
	    toolbar.add(captureBtn);

	    stopBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                                 ActionEvent e){
	          captureBtn.setEnabled(true);
	          stopBtn.setEnabled(false);
	          playBtn.setEnabled(true);
	          //Terminate the capturing of input data
	          // from the microphone.
	          stopCapture = true;
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()
	    toolbar.add(stopBtn);

	    playBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                                 ActionEvent e){
	          //Play back all of the data that was
	          // saved during capture.
	          playAudio();
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()
	    replay.add(playBtn);

	    //getContentPane().setLayout(new FlowLayout());
	    setTitle("Soundlevel analysis");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(250,70);
	    setVisible(true);
	    
	    
	    
	    JPanel filebar = new JPanel();
	    BoxLayout fbLayout=new BoxLayout(filebar,BoxLayout.X_AXIS);
	    filebar.setLayout(fbLayout);
	    
	    
	    masterPane.add(filebar);
	    
	    
	    
	    final JTextField soundFileField = new JTextField(" ");
	    
	    filebar.add(new JLabel("Sound file "));
	    JButton fileSelectButton = new JButton("...");
	    filebar.add(soundFileField);
	    filebar.add(fileSelectButton);
	    
	    
	    fileSelectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
			    JFileChooser fileChooser = new JFileChooser();
			    
			     int returnVal = fileChooser.showOpenDialog(masterPane);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	soundFile=fileChooser.getSelectedFile();
			        	soundFileField.setText(soundFile.getName());
			        	playBtn.setEnabled(soundFile.length()>0);
				          
			        }
			    
		
				
				
			}
	    	
	    });
	    
	    

	  }//end constructor

	  //This method captures audio input from a
	  // microphone and saves it in a
	  // ByteArrayOutputStream object.
	  private void captureAudio(){
	    try{
	    AudioFormat audioFormat=getInputAudioFormat();	  
	  	  
	      DataLine.Info dataLineInfo =
	                            new DataLine.Info(
	                            TargetDataLine.class,
	                            audioFormat);
	  
	      Mixer mixer = AudioSystem.getMixer(getInputMixerInfo());
	      System.out.println(" AKTUALNY => "+ mixer.getMixerInfo().toString());
	      
	      //Get a TargetDataLine on the selected mixer.
	      targetDataLine = (TargetDataLine)mixer.getLine(dataLineInfo);
	      
	      //Prepare the line for use.
	      targetDataLine.open(audioFormat);
	      targetDataLine.start();

	      //Create a thread to capture the microphone
	      // data and start it running.  It will run
	      // until the Stop button is clicked.
	      Thread captureThread = new CaptureThread();
	      captureThread.start();
	    } catch (Exception e) {
	    	
	      System.out.println(e);
	      e.printStackTrace(System.out);
	      
	      System.exit(0);
	    }//end catch
	  }//end captureAudio method

	  //This method plays back the audio data that
	  // has been saved in the ByteArrayOutputStream
	  private void playAudio() {
	    try{
	      //Get everything set up for playback.
	      //Get the previously-saved data into a byte
	      // array object.
	    InputStream inputStream=new FileInputStream(soundFile);
	    
	    Long dataLength=soundFile.length();
	    	
	      System.out.println("Replay Array size = "+ dataLength);
	      //Get an input stream on the byte array
	      // containing the data
	      AudioFormat audioFormat = getOutputAudioFormat();
	      audioInputStream = new AudioInputStream(
	    		  inputStream,
	                    audioFormat,
	                    dataLength/audioFormat.
	                                 getFrameSize());
	      DataLine.Info dataLineInfo =
	                            new DataLine.Info(
	                            SourceDataLine.class,
	                            audioFormat);
	      sourceDataLine = (SourceDataLine)
	               AudioSystem.getLine(dataLineInfo);
	      sourceDataLine.open(audioFormat);
	      sourceDataLine.start();

	      //Create a thread to play back the data and
	      // start it  running.  It will run until
	      // all the data has been played back.
	      Thread playThread = new PlayThread();
	      playThread.start();
	    } catch (Exception e) {
	      System.out.println(e);
	      e.printStackTrace(System.out);
	      
	      System.exit(0);
	    }//end catch
	  }//end playAudio

	  //This method creates and returns an
	  // AudioFormat object for a given set of format
	  // parameters.  If these parameters don't work
	  // well for you, try some of the other
	  // allowable parameter values, which are shown
	  // in comments following the declartions.
	  private AudioFormat getAudioFormat(){
		  
		  
		  
	    float sampleRate = 8000.0F;
	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;
	    //8,16
	    int channels = 1;
	    //1,2
	    boolean signed = true;
	    //true,false
	    boolean bigEndian = false;
	    //true,false
	    
	    
	    //TODO : retrieve actual formats 
	    //Vector<AudioFormat> formats = getSupportedFormats(SourceDataLine.class);
	    
	    return new AudioFormat(
	                      sampleRate,
	                      sampleSizeInBits,
	                      channels,
	                      signed,
	                      bigEndian);
	  }//end getAudioFormat
	//=============================================//

	//Inner class to capture data from microphone
	class CaptureThread extends Thread{
	  //An arbitrary-size temporary holding buffer
	  byte tempBuffer[] = new byte[10000];
	  public void run(){
		  
		  
	    OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(soundFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	           //          new ByteArrayOutputStream();
	    stopCapture = false;
	    try{//Loop until stopCapture is set by
	        // another thread that services the Stop
	        // button.
	      while(!stopCapture){
	        //Read data from the internal buffer of
	        // the data line.
	        int cnt = targetDataLine.read(tempBuffer,
	                              0,
	                              tempBuffer.length);
	        if(cnt > 0){
	        	processBuffer(tempBuffer,cnt);
		        
	          //Save data in output stream object.
	        	outputStream.write(tempBuffer,
	                                      0,
	                                      cnt);
	          incrementCapturedBytes(cnt);
	        }//end if
	      }//end while
	      //byteArrayOutputStream.close();
	    }catch (Exception e) {
	      System.out.println(e);
	      e.printStackTrace(System.out);
	      
	      System.exit(0);
	    }//end catch
	  }//end run
	private void incrementCapturedBytes(int cnt) {
		capturedBytes+=cnt;
		capturedBytesLabel.setText("Captured : " + capturedBytes + " bytes.");
		
	}
	}//end inner class CaptureThread
	//===================================//
	//Inner class to play back the data
	// that was saved.
	class PlayThread extends Thread{
	  byte tempBuffer[] = new byte[10000];

	  public void run(){
	    try{
	      int cnt;
	      //Keep looping until the input read method
	      // returns -1 for empty stream.
	      while((cnt = audioInputStream.read(
	                      tempBuffer, 0,
	                      tempBuffer.length)) != -1){
	        if(cnt > 0){
	        	System.out.println("Read "+cnt+" bytes");
	          //Write data to the internal buffer of
	          // the data line where it will be
	          // delivered to the speaker.
	          sourceDataLine.write(tempBuffer,0,cnt);
	        }//end if
	      }//end while
	      //Block and wait for internal buffer of the
	      // data line to empty.
	      sourceDataLine.drain();
	      sourceDataLine.close();
	    }catch (Exception e) {
	      System.out.println(e);
	      e.printStackTrace(System.out);
	      System.exit(0);
	    }//end catch
	  }//end run
	}//end inner class PlayThread
	//=============================================//
	class ListMixers {
	    PrintWriter out;

	     void listAll(final PrintWriter out) {
	        this.out = out;
	        Mixer.Info[] aInfos = AudioSystem.getMixerInfo();
	        for (int i = 0; i < aInfos.length; i++) {
	            try {
	                Mixer mixer = AudioSystem.getMixer(aInfos[i]);
	                out.println(""+i+": "+aInfos[i].getName()+", "
	                        +aInfos[i].getVendor()+", "
	                        +aInfos[i].getVersion()+", "
	                        +aInfos[i].getDescription());

	                printLines(mixer, mixer.getSourceLineInfo());
	                printLines(mixer, mixer.getTargetLineInfo());
	            } catch (Exception e) {
	                out.println("Exception: "+e);
	            }
	            out.println();
	        }
	        if (aInfos.length == 0) {
	            out.println("[No mixers available]");
	        }
	    }

	    void printLines(Mixer mixer, Line.Info[] infos) {
	        for (int i = 0; i < infos.length; i++) {
	            try {
	                if (infos[i] instanceof Port.Info) {
	                    Port.Info info = (Port.Info) infos[i];

	                    out.println("  Port " + info);
	                }
	                if (infos[i] instanceof DataLine.Info) {
	                    DataLine.Info info = (DataLine.Info) infos[i];

	                    out.println("  Line " + info + " (max. " +
	                                mixer.getMaxLines(info) + " simultaneously): ");
	                    printFormats(info);
	                }
	                Line line = mixer.getLine(infos[i]);

	                if (!(line instanceof Clip)) {
	                    try {
	                        line.open();
	                    }
	                    catch (LineUnavailableException e) {
	                        out.println("LineUnavailableException when trying to open this line");
	                    }
	                }
	                try {
	                    printControls(line.getControls());
	                }
	                finally {
	                    if (!(line instanceof Clip)) {
	                        line.close();
	                    }
	                }
	            }
	            catch (Exception e) {
	                out.println("Exception: " + e);
	            }
	            out.println();
	        }
	    }

	    void printFormats(DataLine.Info info) {
	        AudioFormat[] formats = info.getFormats();
	        for (int i = 0; i < formats.length; i++) {
	            out.println("  "+i+": "+formats[i]
	                    +" ("+formats[i].getChannels()+" channels, "
	                    +"frameSize="+formats[i].getFrameSize()+", "
	                    +(formats[i].isBigEndian()?"big endian":"little endian")
	                    +")");
	        }
	        if (formats.length == 0) {
	            out.println("  [no formats]");
	        }
	        out.println();
	    }

	    void printControls(Control[] controls) {
	        for (int i = 0; i<controls.length; i++) {
	            printControl("    ", "Controls["+i+"]: ", controls[i]);
	        }
	        if (controls.length == 0) {
	            out.println("    [no controls]");
	        }
	        out.println();
	    }

	    void printControl(String indent, String id, Control control) {
	        if (control instanceof BooleanControl) {
	            BooleanControl ctrl = (BooleanControl) control;
	            out.println(indent+id+"BooleanControl: "+ctrl);
	        } else if (control instanceof CompoundControl) {
	            CompoundControl ctrl = (CompoundControl) control;
	            Control[] ctrls = ctrl.getMemberControls();
	            out.println(indent+id+"CompoundControl: "+control);
	            for (int i=0; i<ctrls.length; i++) {
	                printControl(indent+"  ", "MemberControls["+i+"]: ", ctrls[i]);
	            }
	        } else if (control instanceof EnumControl) {
	            EnumControl ctrl = (EnumControl) control;
	            Object[] values = ctrl.getValues();
	            Object value = ctrl.getValue();
	            out.println(indent+id+"EnumControl: "+control);
	            for (int i=0; i<values.length; i++) {
	                if (values[i] instanceof Control) {
	                    printControl(indent+"  ", "Values["+i+"]: "+((values[i]==value)?"*":""), (Control) values[i]);
	                } else {
	                    out.println(indent+"  Values["+i+"]: "+((values[i]==value)?"*":"")+values[i]);
	                }
	            }
	        } else if (control instanceof FloatControl) {
	            FloatControl ctrl = (FloatControl) control;
	            out.println(indent+id+"FloatControl: "+ctrl);
	        } else {
	            out.println(indent+id+"Control: "+control);
	        }
	    }
	}
	
	public Vector<AudioFormat> getSupportedFormats(Class<?> dataLineClass, Mixer.Info mixerInfo) {
	    /*
	     * These define our criteria when searching for formats supported
	     * by Mixers on the system.
	     */
		
		
	    float sampleRates[] = { (float) 8000.0, (float) 16000.0, (float) 44100.0 };
	    int channels[] = { 1, 2 };
	    int bytesPerSample[] = { 1,2,4,8 };

	    AudioFormat format;
	    DataLine.Info lineInfo;

	    
	
	    
	    //SystemAudioProfile profile = new SystemAudioProfile(); // Used for allocating MixerDetails below.
	    Vector<AudioFormat> formats = new Vector<AudioFormat>();
	    if (mixerInfo != null) {
	    	System.out.println("CVF - Looking for supported formats for Mixer '" + mixerInfo.toString() +"' ...");
		    for (int a = 0; a < sampleRates.length; a++) {
		    	for (int b = 0; b < channels.length; b++) {
		    		for (int c = 0; c < bytesPerSample.length; c++) {
		    			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
		    					sampleRates[a], 8 * bytesPerSample[c], channels[b], bytesPerSample[c],
		    					sampleRates[a], false);
		    			lineInfo = new DataLine.Info(dataLineClass, format);
		    			if (AudioSystem.isLineSupported(lineInfo)) {
		    				if (AudioSystem.getMixer(mixerInfo).isLineSupported(lineInfo)) {
		    					System.out.println("CVF -       supported format : " + format.toString());
		    					formats.add(format);
		    				}
		    			}
		    		}
		    	}
		    }
	    }
	    return formats;
	}
	
	public Vector<Mixer.Info> getAvailableMixers(Class<?> dataLineClass) {
		Vector<Mixer.Info> availableMixers = new Vector<Mixer.Info>();
		
		for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
			  if (! getSupportedFormats(dataLineClass,mixerInfo).isEmpty()) {
				  availableMixers.add(mixerInfo);
			  }
		}
		return availableMixers;
	}

}