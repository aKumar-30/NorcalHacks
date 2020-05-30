/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlevr;

/**
 *
 * @author angelac
 */
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class PuzzleVR {

    private JFrame frame;
    private JLabel[] labels;
    private final int rows = 3; //You should decide the values for rows and cols variables
    private final int cols = 3;
    private final int chunks = rows * cols;
    private final int SPACING = 10;//spacing between split images

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new PuzzleVR().createGUI();
            }
        });
    }

    private void createGUI() {
        frame = new JFrame("Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        split();
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void split() {

        BufferedImage[] imgs = getImages();

        //setting the contentpane layout (size, etc) for grid layout 
        frame.getContentPane().setLayout(new GridLayout(rows, cols, SPACING, SPACING));

        labels = new JLabel[imgs.length];

        //create JLabels with split image portions; adding to contentPane
        for (int i = 0; i < imgs.length; i++) {
            labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[i].getSource())));
            frame.getContentPane().add(labels[i]);
        }
    }

    private BufferedImage[] getImages() {
       
        BufferedImage originalImage = null;
       try {
        originalImage = ImageIO.read(new File("I:/eiffel.jpeg"));
} catch (IOException e) {
}
        int chunkWidth = originalImage.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = originalImage.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, originalImage.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(originalImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
}