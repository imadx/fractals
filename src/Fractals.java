   /*____________________________________________________________________________________________________________/
  /                                                                                                             /
 /    E/12/206 | Lab 6 (Fractals) submission                                                                   /
/____________________________________________________________________________________________________________*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.Line2D;
import java.util.Random;
import java.awt.event.*;


public class Fractals extends JPanel{

	public static int width = 800;                             // default dimensions
	public static int height = 800;

    public static int settingsWidth = 550;
    public static int settingsHeight = 500;

    private static double realMin= -1, realMax= 1, imagMin= -1, imagMax=1;
    private static double constCReal = 0, constCImag = 0;

	private static int threshold = 1000;
    private static int customMod = 0;                           // set to 1 when used as 'java Fractals iMad! 12',
                                                                // where 12 is the line length 

	private static int colorMap[][] = new int[width][height];  // contains divergence values

    private static int crossLength = 0;
    private static int modIncrement = 1;

    public static int fractaltype_flag = 0;                    // 0-Julia 1-Mandelbrot :: Default-Julia

    private static CalculateColorMap theMap;

    private static boolean displayLoader = false;
    public static boolean allowCreatingThreads = true;

    private static long executionTime;
    public static int debuggerInt = 0;

    private static JFrame frame;
    private static Fractals fractalPanel;
    private static JFrame settingsFrame;
    public static SettingsPanel settingsPanel;

    JButton focusHolder; // tweak to not to display the focus rect on buttons on startup
    JLabel fractalType;
    JLabel titleText;
    JButton closeButton;
    JButton zoomIn;
    JButton zoomOut;
    JButton optionsButton;
    JLabel hint;


    public Fractals(boolean doNothing){
        // to call the Fractal functions from Settings panel
        // do nothing
    }
	public Fractals(){                                 // Creating the JPanel
        setLayout(null);
        closeButton = new JButton("X");
        zoomIn = new JButton("+");
        zoomOut = new JButton("-");
        optionsButton = new JButton("Change Fractals");
        titleText = new JLabel("  Fractals ");
        hint = new JLabel("Use mouse buttons to Zoom-In/Zoom-Out");

        if (customMod==1) fractalType = new JLabel("  Fun with Fractal Patterns");
        else if  (fractaltype_flag==0) fractalType = new JLabel("  Julia ");
        else if(fractaltype_flag==1) fractalType = new JLabel("  Mandelbrot ");

        titleText.setBackground(new Color(15,15,20));
        fractalType.setBackground(new Color(20,20,25));
        hint.setBackground(new Color(15,15,20));
        hint.setForeground(Color.WHITE);
        hint.setFont(new Font("Segoe UI Light", 0, 16));

        titleText.setFont(new Font("Segoe UI Light", 0, 24));
        titleText.setForeground(new Color(180,255,0));
        titleText.setOpaque(true);
        fractalType.setFont(new Font("Segoe UI Light", 0, 24));
        fractalType.setForeground(Color.WHITE);
        fractalType.setOpaque(true);
        hint.setOpaque(true);
        add(titleText);
        titleText.setBounds(0,0,200,50);
        add(fractalType);
        fractalType.setBounds(200,0,width-250,50);
        add(hint);
        hint.setBounds(30,height-30,300,50);

        closeButton.addMouseListener(new MouseAdapter() {               // Button Styles
            public void mouseEntered(MouseEvent evt) {
                closeButton.setBackground(new Color(173,38,60));
                closeButton.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent evt) {
                closeButton.setBackground(new Color(15,15,20));
                closeButton.setForeground(Color.WHITE);
            }
        });
        zoomIn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                zoomIn.setBackground(new Color(180,255,0));
                zoomIn.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                zoomIn.setBackground(new Color(18,18,24));
                zoomIn.setForeground(Color.WHITE);
            }
        });
        zoomOut.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                zoomOut.setBackground(new Color(180,255,0));
                zoomOut.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                zoomOut.setBackground(new Color(15,15,20));
                zoomOut.setForeground(Color.WHITE);
            }
        });
        optionsButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                optionsButton.setBackground(new Color(180,255,0));
                optionsButton.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                optionsButton.setBackground(new Color(15,15,20));
                optionsButton.setForeground(Color.WHITE);
            }
        });
        closeButton.addActionListener(new ActionListener() {            // Button Actions
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                allowCreatingThreads = true;
                displayLoader = true;
                hint.setForeground(new Color(180,255,0));
                hint.setText("Process: Calculation of Pixel values. Please wait....");
                double zoomValue = (imagMax - imagMin)*0.2;
                    realMin += zoomValue;
                    realMax -= zoomValue;
                    imagMin += zoomValue;
                    imagMax -= zoomValue;
                repaint();
            }
        });

        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                allowCreatingThreads = true;
                displayLoader = true;
                hint.setForeground(new Color(180,255,0));
                hint.setText("Process: Calculation of Pixel values. Please wait....");
                double zoomValue = (imagMax - imagMin)*0.2;
                realMin -= zoomValue;
                realMax += zoomValue;
                imagMin -= zoomValue;
                imagMax += zoomValue;
                repaint();
            }
        });
        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                displayOptionsDialog();
            }
        });
        closeButton.setBackground(new Color(15,15,20));
        closeButton.setBorder(null);
        closeButton.setFont(new Font("Segoe UI Light", 0, 24));
        closeButton.setForeground(Color.WHITE);
        zoomIn.setFont(new Font("Segoe UI Light", 0, 24));
        zoomIn.setBackground(new Color(18,18,24));
        zoomIn.setForeground(Color.WHITE);
        zoomIn.setBorder(null);
        zoomOut.setFont(new Font("Segoe UI Light", 0, 24));
        zoomOut.setBackground(new Color(15,15,20));
        zoomOut.setForeground(Color.WHITE);
        zoomOut.setBorder(null);
        optionsButton.setFont(new Font("Segoe UI Light", 0, 24));
        optionsButton.setBackground(new Color(15,15,20));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setBorder(null);

        focusHolder = new JButton(".");
        focusHolder.setBounds(-100,0,10,10);
        add(focusHolder);
        add(closeButton);
        closeButton.setBounds(width-50, 0, 50, 50);
        add(zoomIn);
        zoomIn.setBounds(width-60, height-190, 60, 60);
        add(zoomOut);
        zoomOut.setBounds(width-60, height-130, 60, 60);
        add(optionsButton);
        optionsButton.setBounds(width-60, height-200, 200, 60);
	}

 	static void putPoint(Graphics2D f, int x, int y, int divergence) {            // Assign colors to pixels w.r.t. divergence
    	Color pointColor;
        boolean markedBlack = false;
        if(divergence==threshold+2) markedBlack = true;
        if      (markedBlack)   pointColor = new Color(0,0,0);
    	else if	(divergence < 10) pointColor = new Color(divergence * 10,    divergence * 18,divergence);
        else if (divergence < 50) pointColor = new Color((divergence*5 + 5)*3/10, (divergence*5 + 5) * 7/10, divergence%5  );
        else if (divergence < 60) pointColor = new Color((divergence*4 + 10)*150/255, (divergence*4 + 10), divergence%176  );
    	else   pointColor = new Color(divergence%230, divergence%230 + divergence%25, divergence/1000*30);
    	
    	f.setColor(pointColor);
        crossLength = modIncrement;
    	f.draw(new Line2D.Double(x-crossLength, y-crossLength, x+crossLength, y+crossLength)); 
        f.draw(new Line2D.Double(x-crossLength, y+crossLength, x+crossLength, y-crossLength)); 
    }

    public void updateText(Graphics g){         // paint the 'Loading...' text
        Rectangle clr = hint.getBounds();
        g.setColor(new Color(15,15,20));
        g.fillRect(clr.x-20, clr.y-20, clr.width+40, clr.height+40);
        repaint();
    }
    public void paintComponent(Graphics g) {
		Graphics2D f = (Graphics2D) g; 

        if(displayLoader){
            displayLoader = false;
            updateText(g);
            return;
        }

        super.paintComponent(g);

        if(allowCreatingThreads){           //  prevent Execution of threads when paint is called by Swing Components
                                            // call the CalculateColorMap to implement threads to do the calculations

            System.out.println("--------------------------------------------------------");
            System.out.print("Calculating divergence..");
            long calculationTime = System.currentTimeMillis();
            theMap = new CalculateColorMap(realMin, realMax, imagMin, imagMax, width, height, constCReal, constCImag, threshold);
            System.out.println("Done. ["+ (System.currentTimeMillis()-calculationTime) +"ms]");
            hint.setForeground(Color.WHITE);
            hint.setText("Use mouse buttons to Zoom-In/Zoom-Out");
            long drawingTime = System.currentTimeMillis();
            System.out.print("Rendering to pixels..");
    		drawColorMap(f);
            System.out.println("Done. ["+ (System.currentTimeMillis()-drawingTime) +"ms]");

            allowCreatingThreads = false;
        }

        executionTime = System.currentTimeMillis() - executionTime;

        if(executionTime < 1000000){
            System.out.println("Execution Time: " + executionTime + "ms");
            System.out.println("--------------------------------------------------------");
        }
    }

    public void drawColorMap(Graphics2D f){         // Using calculated data to draw the pixels
        if(customMod==1){
            f.setColor(Color.black);
            f.fill(new Rectangle(0,0,width,height));
        }

        // Can't draw to the jPanel with Threads - will have to synchronize - hence much worse delays
        // Drawing without using any threads

        for(int i=0; i<width ; i+=modIncrement){
            for (int j=0; j<height ; j+=modIncrement ) {
                putPoint(f,i,j,theMap.colorMap[i][j]);   // have stored values in reverse y axis
            }
        }


        // Runnable drawingTask1 = new Runnable(){
 
        //     @Override
        //     public void run(){
        //          for(int i=0 ; i<width/4 ; i+=modIncrement){
        //             for (int j=0; j<height ; j+=modIncrement ) {
        //                 putPoint(f,i,j,theMap.colorMap[i][j]);
        //                 //System.out.println("rendering " + i + ":" + j);
        //             }
        //         }
        //     }
        // };
        // Runnable drawingTask2 = new Runnable(){
 
        //     @Override
        //     public void run(){
        //          for(int i=width/4 ; i<width/2 ; i+=modIncrement){
        //             for (int j=0; j<height ; j+=modIncrement ) {
        //                 putPoint(f,i,j,theMap.colorMap[i][j]);
        //                 //System.out.println("rendering " + i + ":" + j);
        //             }
        //         }
        //     }
        // };

        // Runnable drawingTask3 = new Runnable(){
 
        //     @Override
        //     public void run(){
        //          for(int i=width/2 ; i<width*3/4 ; i+=modIncrement){
        //             for (int j=0; j<height ; j+=modIncrement ) {
        //                 putPoint(f,i,j,theMap.colorMap[i][j]);
        //                 //System.out.println("rendering " + i + ":" + j);
        //             }
        //         }
        //     }
        // };
        // Runnable drawingTask4 = new Runnable(){
 
        //     @Override
        //     public void run(){
        //          for(int i=width*3/4 ; i<width ; i+=modIncrement){
        //             for (int j=0; j<height ; j+=modIncrement ) {
        //                 putPoint(f,i,j,theMap.colorMap[i][j]);
        //                 //System.out.println("rendering " + i + ":" + j);
        //             }
        //         }
        //     }
        // };
        // Thread drawingThread1 = new Thread(drawingTask1);
        // Thread drawingThread2 = new Thread(drawingTask2);
        // Thread drawingThread3 = new Thread(drawingTask3);
        // Thread drawingThread4 = new Thread(drawingTask4);
        // drawingThread1.start();
        // drawingThread2.start();
        // drawingThread3.start();
        // drawingThread4.start();
        // try{
        //     drawingThread1.join();
        //     drawingThread3.join();
        //     drawingThread4.join();
        // } catch(InterruptedException e){
        //     System.out.println(e);
        // }

        // try{
        //     drawingThread2.join();
        // } catch(InterruptedException e){
        //     System.out.println(e);
        // }
        Rectangle clr = hint.getBounds();
        f.setColor(new Color(15,15,20));
        f.fillRect(clr.x-20, clr.y-20, clr.width+40, clr.height+40);
    }
	public static void main(String args[]){

        executionTime = System.currentTimeMillis();

        if(args.length == 0){               // Check arguments
            System.out.println("No Complex value given! using default values.. ");
            System.out.println("Using default threshold: " + threshold);
            constCReal = -0.4;
            constCImag = 0.6;
        }
        else if(args[0].equals("Mandelbrot")){
            fractaltype_flag = 1;
            try{
                realMin=    Double.parseDouble(args[1]);
                realMax=    Double.parseDouble(args[2]);
                imagMin=    Double.parseDouble(args[3]);
                imagMax=    Double.parseDouble(args[4]);
            } catch (Exception e){
                System.out.println("No range for Complex values given! using default values.. ");
            }
            try{
                threshold =     Integer.parseInt(args[5]);
            } catch(Exception e){
                System.out.println("Using default threshold: " + threshold);
            }
        } else if(args[0].equals("Julia")){
            try{
                constCReal = Double.parseDouble(args[1]);
                constCImag = Double.parseDouble(args[2]);
            } catch (Exception e){
                System.out.println("No Complex value given! using default values.. ");
                constCReal = -0.4;
                constCImag = 0.6;
            }
            try{
                threshold =     Integer.parseInt(args[3]);
            } catch(Exception e){
                System.out.println("Using default threshold: " + threshold);
            }
        } else if (args[0].equals("iMad!")){
            fractaltype_flag = 1; // use Mandelbrot
            customMod = 1;
            try{
                modIncrement = Integer.parseInt(args[1]);
            } catch(Exception e){
                modIncrement = 10;
            }
        }

    	frame = new JFrame("Fractals");         // Create the main window
        fractalPanel = new Fractals();

        fractalPanel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                allowCreatingThreads = true;
                executionTime = System.currentTimeMillis();

                displayLoader = true;
                fractalPanel.hint.setForeground(new Color(180,255,0));
                fractalPanel.hint.setText("Process: Calculation of Pixel values. Please wait....");

                double clickedX = (realMin + evt.getX()/(double)width*(realMax - realMin));
                double clickedY = (imagMin + evt.getY()/(double)height*(imagMax - imagMin));
                double zoomValue;
                if(evt.getButton()==3){         // Zoom-out on right-click
                    zoomValue = (realMax - realMin)*0.7;
                    realMin = clickedX - zoomValue;
                    realMax = clickedX + zoomValue;
                    zoomValue = (imagMax - imagMin)*0.7;
                    imagMin = clickedY - zoomValue;
                    imagMax = clickedY + zoomValue;
                } else {                           // Zoom-out on right-click
                    zoomValue = (realMax - realMin)/2.5;
                    realMin = clickedX - zoomValue;
                    realMax = clickedX + zoomValue;
                    zoomValue = (imagMax - imagMin)/2.5;
                    imagMin = clickedY - zoomValue;
                    imagMax = clickedY + zoomValue;
                }
                fractalPanel.repaint();
            }
        });
        
        frame.setExtendedState(6);                  // maximize window - comment this line to get default sized window
        frame.setPreferredSize(new Dimension(width, height));
        frame.setSize(width, height);
        frame.toFront();
        frame.setUndecorated(true);
        frame.setContentPane(fractalPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        width = frame.getWidth();
        height = frame.getHeight();

        fractalPanel.zoomIn.setBounds(width-60, height-190, 60, 60);
        fractalPanel.zoomOut.setBounds(width-60, height-130, 60, 60);
        fractalPanel.optionsButton.setBounds(width-200, height-60, 200, 60);
        
        fractalPanel.fractalType.setBounds(200,0,width-250,50);
        fractalPanel.closeButton.setBounds(width-50, 0, 50, 50);
        fractalPanel.hint.setBounds(20,height-30,330,16);
        width += 60;
        executionTime = System.currentTimeMillis() - executionTime;
        System.out.println("Program Execution Time: " + executionTime + "ms");
        settingsFrame = new JFrame("Change settings");
        settingsPanel = new SettingsPanel(settingsWidth, settingsHeight);
        
        settingsFrame.setPreferredSize(new Dimension(settingsWidth, settingsHeight));
        settingsFrame.setSize(settingsWidth, settingsHeight);
        settingsFrame.setUndecorated(true);
        settingsFrame.setContentPane(settingsPanel);
        settingsFrame.setLocation(width-settingsWidth - 60,height - settingsHeight);
        //displayOptionsDialog();
	}

    private static void displayOptionsDialog(){
        settingsPanel.initializeSettings(   fractaltype_flag,
                                            threshold,
                                            realMin,
                                            realMax,
                                            imagMin,
                                            imagMax,
                                            constCReal,
                                            constCImag);
        settingsFrame.setVisible(true);
        settingsFrame.toFront();
    }
    public static void updateFractalsOnSettings(int a, int b, double c, double d, double e, double f, double g, double h){
        fractaltype_flag = a;
        threshold = b;
        realMin = c;
        realMax = d;
        imagMin = e;
        imagMax = f;
        constCReal = g;
        constCImag = h;

        
        allowCreatingThreads = true;
        executionTime = System.currentTimeMillis();

        displayLoader = true;
        fractalPanel.hint.setForeground(new Color(180,255,0));
        fractalPanel.hint.setText("Process: Calculation of Pixel values. Please wait....");
        if(fractaltype_flag==0) fractalPanel.fractalType.setText("  Julia ");
        else fractalPanel.fractalType.setText("  Mandelbrot ");
        fractalPanel.repaint();
    }
    public static void hideSettingsDialog(){
        frame.toFront();
    }
}

class CalculateColorMap extends Thread{
    private static int totThreads = 8;
    CalculateColorMap threads[] = new CalculateColorMap[totThreads];
    private static int width;
    private static int height;

    public static int colorMap[][];

    private static double realMin= -1, realMax= 1, imagMin= -1, imagMax=1;
    private static double constCReal = 0, constCImag = 0;
    private static int threshold = 1000;

    private static int localMax = 0;
    private static int localMin = Integer.MAX_VALUE;

    private int threadID;

    public CalculateColorMap(double realMin, double realMax, double imagMin, double imagMax, int iwidth, int iheight, double constCReal, double constCImag, int threshold){

        this.realMin = realMin;
        this.realMax = realMax;
        this.imagMin = imagMin;
        this.imagMax = imagMax;
        this.width = iwidth;
        this.height = iheight;
        this.constCReal = constCReal;
        this.constCImag = constCImag;
        this.threshold = threshold;
        this.colorMap = new int[width][height]; // contains calculated

        createThreads();
    }

    public CalculateColorMap(int ithreadID){
        this.threadID = ithreadID;
    }
    public void createThreads(){
        // System.out.print("Working on threads..");
        for(int i=0; i < totThreads; i++) {
            threads[i] = new CalculateColorMap(i+1); 
            threads[i].start();
        }

        for(int i=0; i < totThreads; i++) { 
            try { 
                threads[i].join();
            } catch (InterruptedException e) { 
                System.out.println("Not good");
            }
        }
        // System.out.println("Done.");
    }
    public void run(){

        for(int i=width/totThreads*(threadID-1) ; i<width/totThreads*threadID; i++){
            for (int j=0; j<height ; j++ ) {
                colorMap[i][j] = getFractalValue(i, j);//(int)(((double)i*j)/640000*1000);
            }
        }
    }

    public static int getFractalValue(int i, int j){
        double creal;
        double cimag;
        double zreal;
        double zimag;
        if(Fractals.fractaltype_flag==1){  // Mandelbrot
            creal = realMin + ((realMax-realMin) * (double)i / width);
            cimag = imagMin + ((imagMax-imagMin) * (double)j / height);
            zreal = 0;
            zimag = 0;
        } else {                            // Julia
            creal = constCReal;
            cimag = constCImag;
            zreal = realMin + ((realMax-realMin) * (double)i / width);
            zimag = imagMin + ((imagMax-imagMin) * (double)j / height);
        }
        double nextzReal = zreal;
        double nextzImag = zimag;
        double abs = 0;

        for(int count=0; count < threshold; count++){
            nextzReal = Math.pow(zreal,2) - Math.pow(zimag,2) + creal;
            nextzImag = zreal * zimag * 2 + cimag;
            abs = Math.pow(nextzReal,2) + Math.pow(nextzImag,2);
            if(abs>4) return count;
            zreal = nextzReal;
            zimag = nextzImag;
        }
        return threshold + 2;
    }
}