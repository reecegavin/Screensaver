/*
@AUTHOR :  Reece Gavin
@ID: 17197589

Class description : This is a class used to simulate a screensvaer. It features a bouncing ball which bounces diagonally across the screen
                    The ball changes colour as it moves from the white to black gradient.
                    There is thousands of small circles drawn, utilising 7 differnt colours. Their location is random 
                    My ID number is shown to be rotating right in the botton right corner. 
                    The date is displayed in a random position every repaint.   
                    Random lines are drawn spanning from the top of the screen. These are also random colours.
*/
package lab12;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ScreenSaver extends JPanel {

    // Declare instance variables and constants for a PacMan like Object
    private int xPosBall = 0;
    private int yPosBall = 0;
    private final int XballDelta = 75; // Delta x Move for PAC/screen refresh
    private int YballDelta; // Delta x Move for PAC/screen refresh

    private final int idX = 1000;
    private final int idY = 650;
    private final String idNumber = "ID: 17197589 Reece Gavin";
    private int angdeg = 15;

    private static final Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    //GradientPaint gpio = new GradientPaint(50, 30, Color.BLACK, 35, 500, Color.white);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g4d = (Graphics2D) g;
        {
            g4d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(35, 50, Color.white, 35, 500, Color.black);
            g4d.setPaint(gp);
            g4d.fillRect(0, 0, w, h);

        }
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        //Drawing the small circles of differnt colours
        int i = 0;
        int j = 0;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 1250; j++) {

                Random rand2 = new Random();
                Random rand3 = new Random();

                int xPosCirc = rand2.nextInt(screenWidth);
                int yPosCirc = rand2.nextInt(screenHeight);
                g.fillArc(xPosCirc, yPosCirc, 3, 3, 0, 360);
                int xPosRect = rand3.nextInt(screenWidth);
                int yPosRect = rand3.nextInt(screenHeight);
                g.fillRect(xPosRect, yPosRect, 1, 1);

            }
            Color colourChanger;
            colourChanger = colors[i];
            g.setColor(colourChanger);

            if (i == 7) {
                i = 0;
            }
            Graphics2D g6d = (Graphics2D) g;
            Random rand4 = new Random();
            int xPosLine = rand4.nextInt(screenWidth);
            int yPosLine = rand4.nextInt(screenHeight);

            g6d.drawLine(xPosLine, yPosLine, xPosLine, 0);

        }
        //CREATING THE BOUNCING BALL
        Graphics2D g3d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(50, 30, Color.BLACK, 35, 500, Color.white);
        g3d.setPaint(gp);
        g3d.fillArc(xPosBall, yPosBall, 65, 65, 0, 360);
        xPosBall = (xPosBall < screenWidth) ? xPosBall + XballDelta : 0;   // Update Ball Yposition
        {
            if (yPosBall == 0) {
                YballDelta = 200;
            }
            if (yPosBall == 600) {
                YballDelta = -200;
            }
        }
        yPosBall = (yPosBall < screenHeight) ? yPosBall + YballDelta : 0;   // Update Ball Xposition

        //CREATING ROTATING ID NUMBER
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Calibri", Font.ITALIC, 24);
        g2d.setFont(font);  //setting font of surface
        //getting original transform instance 
        AffineTransform saveTransform = g2d.getTransform();
        g2d.setColor(Color.white);
        AffineTransform affineTransform = new AffineTransform();
        /*creating instance set the translation to the mid of the component*/
        affineTransform.setToTranslation(idX, idY);
        //rotate with the anchor point as the mid of the text
        affineTransform.rotate(Math.toRadians(angdeg), 180, 0);
        g2d.setTransform(affineTransform);
        g2d.drawString(idNumber, 10, 10);
        g2d.setTransform(saveTransform); //restoring original transform
        angdeg = (angdeg >= 360) ? 0 : angdeg + 45; //

        //CREATES THE DATE IN A RANDOM LOCATION
        Graphics2D g5d = (Graphics2D) g;
        Font dateFont = new Font("Calibri", Font.ITALIC, 32);
        g5d.setFont(dateFont);
        g5d.setColor(Color.yellow);
        Date date = new Date();
        Random dateRandx = new Random();
        Random dateRandy = new Random();
        int dateRandx1 = dateRandx.nextInt(screenWidth);
        int dateRandy1 = dateRandy.nextInt(screenHeight);
        String dateNow = DateFormat.getDateTimeInstance().format(date);
        g5d.drawString(dateNow, dateRandx1, dateRandy1);
    }

    // Create the panel above, add to a frame, display and refresh
    public static void main(String args[]) {
        JFrame frame = new JFrame("Screensaver");

        ScreenSaver screenSaverPanel = new ScreenSaver();

        frame.add(screenSaverPanel);
        frame.addMouseListener(new ScreenSaverMouseListener());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);    // maximise window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //Creating Key Binding to close when space or enter is pressed
        int condition = JPanel.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = ((JPanel) frame.getContentPane()).getInputMap(condition);
        ActionMap actionMap = ((JPanel) frame.getContentPane()).getActionMap();
        String enter = "enter";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), enter);
        actionMap.put(enter, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Redraw window continuously, with a short delay.
        do {
            screenSaverPanel.setBackground(Color.BLACK);

            frame.repaint();

            try {
                // Delay the given ms
                Thread.sleep(1000);

            } catch (InterruptedException interruptedException) {
            }

        } while (true);

    }

}// end class ScreenSaver

class ScreenSaverMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JFrame) {
            System.exit(0);
        }
    }
}
