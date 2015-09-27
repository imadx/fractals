import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.Line2D;
import java.awt.event.*;
import javax.swing.event.*;


public class SettingsPanel extends JPanel{
    private static int p_fractalType = 0; // for Julia, 1 for Mandelbrot
    private static int p_threshold = 1000;
    private static double p_realMin = -1;
    private static double p_realMax = 1;
    private static double p_imagMin = -1;
    private static double p_imagMax = 1;
    private static double p_creal = -0.4;
    private static double p_cimag = 0.6;
    private static boolean p_enableRealtime = false;

    JLabel  s_patternType;
    private static JButton s_ptype_Mandelbrot;
    private static JButton s_ptype_Julia;
    //------------------------
    JLabel  s_threshold;
    private static JSlider s_thresh_slider;
    //------------------------
    JLabel  s_range;
    private static JSpinner s_range_rMin;
    private static JSpinner s_range_rMax;
    private static JSpinner s_range_iMin;
    private static JSpinner s_range_iMax;
    //------------------------
    JLabel   s_cvalue;
    private static JSpinner s_cvalue_real;
    private static JLabel   s_cvalue_plus;
    private static JSpinner s_cvalue_imag;
    private static JLabel   s_cvalue_eye;
    //------------------------
    JCheckBox   s_realtime;
    JButton     s_updateButton;
    //------------------------
    
    private static int width;
    private static int height;
    //------------------------
    private static Fractals inFractal;

    public SettingsPanel(int width, int height){
        boolean justWantToReferanFractalObject = true;
        inFractal = new Fractals(justWantToReferanFractalObject);
        this.width = width;
        this.height = height;
        s_patternType       = new JLabel("Fractal Pattern");
        s_patternType.setFont(new Font("Segoe UI Light", 0, 16));
        s_patternType.setForeground(Color.WHITE);

        s_ptype_Mandelbrot  = new JButton("Mandelbrot");
        s_ptype_Mandelbrot.setBackground(new Color(34,118,30));
        s_ptype_Mandelbrot.setFont(new Font("Segoe UI Light", 0, 16));
        s_ptype_Mandelbrot.setForeground(Color.WHITE);
        s_ptype_Mandelbrot.setBorder(null);

        s_ptype_Julia       = new JButton("Julia");
        s_ptype_Julia.setBackground(new Color(8,8,9));
        s_ptype_Julia.setFont(new Font("Segoe UI Light", 0, 16));
        s_ptype_Julia.setForeground(Color.WHITE);
        s_ptype_Julia.setBorder(null);

        //------------------------
        s_threshold         = new JLabel("Threshold");
        s_threshold.setFont(new Font("Segoe UI Light", 0, 16));
        s_threshold.setForeground(Color.WHITE);
        s_threshold.setBorder(null);

        s_thresh_slider     = new JSlider();
        s_thresh_slider.setForeground(Color.WHITE);
        // s_thresh_slider.setBackground(new Color(15,15,20));
        s_thresh_slider.setBorder(null);
        s_thresh_slider.setOpaque(false);
        s_thresh_slider.setMaximum(7000);
        s_thresh_slider.setMinimum(10);
        s_thresh_slider.setValue(1000);
        //------------------------
        s_range             = new JLabel("Range");
        s_range.setFont(new Font("Segoe UI Light", 0, 16));
        s_range.setForeground(Color.WHITE);
        s_range.setBorder(null);
        s_range_rMin        = new JSpinner();
        s_range_rMin.setFont(new Font("Segoe UI", 0, 16));
        s_range_rMin.setModel(new SpinnerNumberModel(-1.0, -2.0, 2.0, 0.02));
        s_range_rMin.setBorder(BorderFactory.createLineBorder(new Color(34,118,30), 1));
        s_range_rMin.setBackground(new Color(15,15,20));
        s_range_rMax        = new JSpinner();
        s_range_rMax.setFont(new Font("Segoe UI", 0, 16));
        s_range_rMax.setModel(new SpinnerNumberModel(1.0, -2.0, 2.0, 0.02));
        s_range_rMax.setBorder(BorderFactory.createLineBorder(new Color(34,118,30), 1));
        s_range_rMax.setBackground(new Color(15,15,20));
        s_range_iMin        = new JSpinner();
        s_range_iMin.setFont(new Font("Segoe UI", 0, 16));
        s_range_iMin.setModel(new SpinnerNumberModel(-1.0, -2.0, 2.0, 0.02));
        s_range_iMin.setBorder(BorderFactory.createLineBorder(new Color(34,118,30), 1));
        s_range_iMin.setBackground(new Color(15,15,20));
        s_range_iMax        = new JSpinner();
        s_range_iMax.setFont(new Font("Segoe UI", 0, 16));
        s_range_iMax.setModel(new SpinnerNumberModel(1.0, -2.0, 2.0, 0.02));
        s_range_iMax.setBorder(BorderFactory.createLineBorder(new Color(34,118,30), 1));
        s_range_iMax.setBackground(new Color(15,15,20));
        //------------------------
        s_cvalue            = new JLabel("C Value (for Julia)");
        s_cvalue.setFont(new Font("Segoe UI Light", 0, 16));
        s_cvalue.setForeground(Color.WHITE);
        s_cvalue.setBorder(null);
        s_cvalue_real       = new JSpinner();
        s_cvalue_real.setFont(new Font("Segoe UI", 0, 16));
        s_cvalue_real.setModel(new SpinnerNumberModel(-0.4, -2.0, 2.0, 0.001));
        s_cvalue_plus       = new JLabel("+");
        s_cvalue_plus.setFont(new Font("Segoe UI Light", 0, 24));
        s_cvalue_plus.setForeground(Color.WHITE);
        s_cvalue_plus.setBorder(null);
        s_cvalue_imag       = new JSpinner();
        s_cvalue_imag.setFont(new Font("Segoe UI", 0, 16));
        s_cvalue_imag.setModel(new SpinnerNumberModel(0.6, -2.0, 2.0, 0.001));
        s_cvalue_eye        = new JLabel("i");
        s_cvalue_eye.setFont(new Font("Segoe UI Light", 0, 24));
        s_cvalue_eye.setForeground(Color.WHITE);
        s_cvalue_eye.setBorder(null);
        //------------------------
        s_realtime          = new JCheckBox("Enable Real-time Rendering");
        s_realtime.setForeground(Color.WHITE);
        s_realtime.setFont(new Font("Segoe UI Light", 0, 16));
        s_realtime.setBorder(null);
        s_realtime.setOpaque(false);
        s_updateButton      = new JButton("Update Fractal");
        s_updateButton.setFont(new Font("Segoe UI Light", 0, 20));
        s_updateButton.setBackground(new Color(10,10,15));
        s_updateButton.setForeground(Color.WHITE);
        s_updateButton.setBorder(null);

        //------------------------

        ///-------------------------------------------------------------------------------------------------- Start  of Layout
        setBackground(new Color(15,15,20));
        setLayout(null);

        add(s_patternType);
        add(s_ptype_Mandelbrot);
        add(s_ptype_Julia);

        add(s_threshold);
        add(s_thresh_slider);

        add(s_range);
        add(s_range_rMin);
        add(s_range_rMax);
        add(s_range_iMin);
        add(s_range_iMax);

        add(s_cvalue);
        add(s_cvalue_real);
        add(s_cvalue_plus);
        add(s_cvalue_imag);
        add(s_cvalue_eye);

        int y_pattern = 20;
        int y_threshold = 80;
        int y_range = 155;
        int y_cvalue = 360;
        int y_realtime = 440;

        add(s_realtime);
        add(s_updateButton);
                                                                    //20
        s_patternType.setBounds(20,y_pattern,150,40);                      //40
        s_ptype_Mandelbrot.setBounds(180,y_pattern,130,40);
        s_ptype_Julia.setBounds(310,y_pattern,130,40);
                                                                    //20 80
        s_threshold.setBounds(20,y_threshold,150,40);                        //40 
        s_thresh_slider.setBounds(180,y_threshold,260,40);
                                                                    //20 140
        s_range.setBounds(20,y_range,150,40);                           //40 
        s_range_rMin.setBounds(180,y_range + 60,80,40);                                                 // 40, 30, 
        s_range_rMax.setBounds(380,y_range + 60,80,40);
        s_range_iMin.setBounds(280,y_range + 120,80,40);
        s_range_iMax.setBounds(280,y_range,80,40);
                                                                    //20 300
        s_cvalue.setBounds(20,y_cvalue,150,40);                          //40
        s_cvalue_real.setBounds(180,y_cvalue,80,40);
        s_cvalue_plus.setBounds(270,y_cvalue,20,40);
        s_cvalue_imag.setBounds(300,y_cvalue,80,40);
        s_cvalue_eye.setBounds(390,y_cvalue,20,40);
                                                                    //20 360
        s_realtime.setBounds(40,y_realtime,250,40);                        //40 
        s_updateButton.setBounds(width-200, height-60, 200, 60);
        ///-------------------------------------------------------------------------------------------------- End of Layout

        ///-------------------------------------------------------------------------------------------------- Start  of Actions
        s_updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(p_enableRealtime) inFractal.hideSettingsDialog();
                callToUpdateFractals();
            }
        });
        s_ptype_Mandelbrot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                set_p_fractalType(1);
            }
        });
        s_ptype_Julia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                set_p_fractalType(0);
            }
        });
        // s_realtime.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent evt) {
        //         p_enableRealtime = s_realtime.isSelected();
        //         if(p_enableRealtime) callToUpdateFractals();
        //     }
        // });
        s_realtime.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                p_enableRealtime = s_realtime.isSelected();
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
               
        s_range_rMin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                 if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_range_rMax.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_range_iMax.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_range_iMin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_cvalue_imag.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_cvalue_real.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        s_thresh_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                if(s_thresh_slider.getValue()<100) s_realtime.setSelected(true);
                if(s_thresh_slider.getValue()>4000) s_realtime.setSelected(false);
                if(p_enableRealtime) callToUpdateFractals();
            }
        });
        ///-------------------------------------------------------------------------------------------------- End  of Actions

        ///-------------------------------------------------------------------------------------------------- Start of  of ButtonStyleEvents
        s_updateButton.addMouseListener(new MouseAdapter() { 
            public void mouseEntered(MouseEvent evt) {
                s_updateButton.setBackground(new Color(180,255,0));
                s_updateButton.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                s_updateButton.setBackground(new Color(10,10,15));
                s_updateButton.setForeground(Color.WHITE);
            }
        });
        s_ptype_Mandelbrot.addMouseListener(new MouseAdapter() { 
            public void mouseEntered(MouseEvent evt) {
                s_ptype_Mandelbrot.setBackground(new Color(180,255,0));
                s_ptype_Mandelbrot.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                set_p_fractalType(p_fractalType);
                s_ptype_Mandelbrot.setForeground(Color.WHITE);
            }
        });
        s_ptype_Julia.addMouseListener(new MouseAdapter() { 
            public void mouseEntered(MouseEvent evt) {
                s_ptype_Julia.setBackground(new Color(180,255,0));
                s_ptype_Julia.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                set_p_fractalType(p_fractalType);
                s_ptype_Julia.setForeground(Color.WHITE);
            }
        });
        ///-------------------------------------------------------------------------------------------------- End  of ButtonStyleEvents
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D f = (Graphics2D) g; 
        f.setColor(new Color(34,118,30));
        // separators
        f.drawLine(20, 130, width-20, 130);
        f.drawLine(20, 340, width-20, 340);
        f.drawLine(20, 420, width-20, 420);
        //cross
        f.drawLine(270, 235, 370, 235);
        f.drawLine(270, 236, 370, 236);
        f.drawLine(319, 204, 319, 268);
        f.drawLine(320, 204, 320, 268);
    }

    public static void initializeSettings(int a, int b, double c, double d, double e, double f, double g, double h){
        p_fractalType = a;
        p_threshold = b;
        p_realMin = c;
        p_realMax = d;
        p_imagMin = e;
        p_imagMax = f;
        p_creal = g;
        p_cimag = h;

        if(p_fractalType==0){   // Julia
            s_ptype_Julia.setBackground(new Color(34,118,30));
            s_ptype_Mandelbrot.setBackground(new Color(8,8,9));
            p_fractalType = 0;
        } else {
            s_ptype_Mandelbrot.setBackground(new Color(34,118,30));
            s_ptype_Julia.setBackground(new Color(8,8,9));
            p_fractalType = 1;
        }
        s_thresh_slider.setValue(p_threshold);
        s_range_rMin.setValue(c);
        s_range_rMax.setValue(d);
        s_range_iMin.setValue(e);
        s_range_iMax.setValue(f);
        s_cvalue_real.setValue(p_creal);
        s_cvalue_imag.setValue(p_cimag);
    }
    private static void callToUpdateFractals(){
        p_threshold = s_thresh_slider.getValue();
        p_realMin   = (Double) s_range_rMin.getValue();
        p_realMax   = (Double) s_range_rMax.getValue();
        p_imagMin   = (Double) s_range_iMin.getValue();
        p_imagMax   = (Double) s_range_iMax.getValue();
        p_creal     = (Double) s_cvalue_real.getValue();
        p_cimag     = (Double) s_cvalue_imag.getValue();
        inFractal.updateFractalsOnSettings(p_fractalType, p_threshold, p_realMin, p_realMax, p_imagMin, p_imagMax, p_creal, p_cimag);
        if(!p_enableRealtime) inFractal.hideSettingsDialog();
    }
    private static void set_p_fractalType(int type){
        if(type==0){
            s_ptype_Julia.setBackground(new Color(34,118,30));
            s_ptype_Mandelbrot.setBackground(new Color(8,8,9));
            p_fractalType = 0;
        } else {
            s_ptype_Mandelbrot.setBackground(new Color(34,118,30));
            s_ptype_Julia.setBackground(new Color(8,8,9));
            p_fractalType = 1;
        }
        if(p_enableRealtime) callToUpdateFractals();
    }
}