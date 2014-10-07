
package main.gui;

import entities.Environment;
import exceptions.GraphicIndexOutOfBoundsException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class Window extends javax.swing.JFrame {

    class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.draw(new Rectangle((int) (scaleFactor * environment.getWidth()) - 1, (int) (scaleFactor * environment.getHeight()) - 1));

            double[][] coords = new double[0][0];
            try {
                coords = frameCollection.getFrame(curFrameIndex);
            } catch (GraphicIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }

            for (int i=0; i<coords.length; i++) {
                for (int j=0; j<3; j++) {
                    coords[i][j] = scaleFactor * coords[i][j];
                }
            }

            for (int i=0; i<coords.length; i++) {
                g2.draw(new Ellipse2D.Double(coords[i][0] - coords[i][2],
                                             coords[i][1] - coords[i][2],
                                             2 * coords[i][2],
                                             2 * coords[i][2]));
            }

        }
    }

    private MyPanel imagePanel;

    private Environment environment;
    private PositionColletion frameCollection;

    private int curFrameIndex;
    private double dtLength;

    private double scaleFactor;
    
    public Window(Environment newEnvironment, int width, int height) {
        initComponents();
        imagePanel = new MyPanel();
        imagePanel.setBounds(10, 10, width, height);
        this.setSize(width + 35, height + 55);
        imagePanel.setBackground(Color.WHITE);
        add(imagePanel);

        environment = newEnvironment;
        frameCollection = new PositionColletion();
        dtLength = .1;

        if (environment.getWidth() >= environment.getHeight()) {
            scaleFactor = imagePanel.getWidth() / environment.getWidth();
        } else {
            scaleFactor = imagePanel.getHeight() / environment.getHeight();
        }

        curFrameIndex = 0;
        frameCollection.addFrame(environment.getCoordinates());
        imagePanel.repaint();
        setVisible(true);

        //main.Main.printVitalStatistics(environment);
        //main.Main.printParticle0Statistics(environment);
    }

    public void setDtLenght(double newDt) {
        dtLength = newDt;
    }
    private void frameForward() {
        curFrameIndex++;
        if (curFrameIndex == frameCollection.getNumFrames()) {
            environment.update(dtLength);
            frameCollection.addFrame(environment.getCoordinates());
        }
        imagePanel.repaint();
    }
    private void frameBackward() {
        if (curFrameIndex > 0) {
            curFrameIndex--;
            imagePanel.repaint();
        }
    }
    private void scatter() {
        frameCollection = new PositionColletion();
        environment.scatter();
        frameCollection.addFrame(environment.getCoordinates());
        imagePanel.repaint();
    }
    private void heat(double newKelvin) {
        frameCollection = new PositionColletion();
        environment.heat(newKelvin);
        frameCollection.addFrame(environment.getCoordinates());
        imagePanel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == 39) {
            frameForward();
        } else if (evt.getKeyCode() == 37) {
            frameBackward();
        }
    }//GEN-LAST:event_formKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
