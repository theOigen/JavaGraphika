import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

import javax.swing.*;

public class Drawing extends JPanel implements ActionListener {

    private static int maxWidth;
    private static int maxHeight;

    Timer timer;

    private double scale = 1;
    private double delta = 0.01;
    private double dx = 1;
    private double tx = 0;
    private double dy = 0;
    private double ty = 0;

    public Drawing() {
        timer = new Timer(5,this);
        timer.start();
    }
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.RED);
        g2d.clearRect(0,0,maxWidth,maxHeight);
        //paint here
        double[][] pointsPolygon = {
                {149,94},
                {81,105},
                {68,27},
                {141,13},
                {153,86},
                {172,26},
                {248,59},
                {214,121},
                {157,95},
                {192,147},
                {136,181},
                {106,121}
        };

        GeneralPath polygon = new GeneralPath();

        polygon.moveTo(pointsPolygon[0][0],pointsPolygon[0][1]);
        for (int i = 1; i < pointsPolygon.length; i++){
            polygon.lineTo(pointsPolygon[i][0],pointsPolygon[i][1]);
        }
        polygon.closePath();
        GradientPaint gp = new GradientPaint(15,20, new Color(150,200,10), 20,2, new Color(0,0,0), true);
        g2d.setPaint(gp);
        g2d.fill(polygon);

        double[][] pointsPolyline = {
                {111,56},
                {194,76},
                {149,133},
                {111,56}
        };

        GeneralPath polyline = new GeneralPath();
        polyline.moveTo(pointsPolyline[0][0],pointsPolyline[0][1]);
        for (int i = 1; i < pointsPolyline.length; i++){
            polyline.lineTo(pointsPolyline[i][0],pointsPolyline[i][1]);
        }
        polyline.closePath();
        g2d.setColor(Color.MAGENTA);
        BasicStroke bs1 = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        g2d.setStroke(bs1);
        g2d.draw(polyline);

        g2d.setStroke(new BasicStroke(10,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g2d.setColor(Color.GREEN);
        g2d.drawRect(250,250, 100,100);

        g2d.translate(tx,ty);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)scale));
        g2d.setColor(Color.BLUE);
        g2d.fillOval(240,240, 20,20);

    }

    public void actionPerformed(ActionEvent e) {
        if (scale < 0.01) {
            delta = -delta;
        } else if (scale > 0.99){
            delta = -delta;
        }

        if (tx == 0 && ty == 0) {
            dx = 1;
            dy = 0;
        } else if (tx == 100 && ty == 0) {
            dx = 0;
            dy = 1;
        } else if (tx == 100 && ty == 100) {
            dx = -1;
            dy = 0;
        } else if (tx == 0 && ty == 100) {
            dx = 0;
            dy = -1;
        }

        scale += delta;
        tx += dx;
        ty += dy;

        repaint();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Hello");
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(new Drawing());
        frame.setVisible(true);
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }
}
