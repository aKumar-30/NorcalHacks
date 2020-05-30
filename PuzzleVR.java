
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
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;

public class PuzzleVR {

    private JFrame frame;
    private JLabel[] labels;
    private final int rows = 7; 
    private final int cols = 6;
    private final int chunks = rows * cols;
    private JButton puzzlePieces[][] = new JButton[rows][cols];

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
        int counter= 0;
        //setting the contentpane layout (size, etc) for grid layout 
        frame.getContentPane().setLayout(new GridLayout(rows, cols));

        
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageIcon pic = new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[counter].getSource()));
                puzzlePieces[i][j] = new JButton(pic);
                puzzlePieces[i][j].setActionCommand("(" + i + j + ")");
                //puzzlePieces[i][j].addActionListener(this);
                frame.getContentPane().add(puzzlePieces[i][j]);
                counter++;
            }
        }
    }

    private BufferedImage[] getImages() {
       
        BufferedImage originalImage = null;
        URL url1 = null;
       try
        {
            url1 = new URL("https://kids.nationalgeographic.com/explore/monuments/eiffel-tower/_jcr_content/content/textimage_6.img.jpg/1581608715365.jpg");
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        }
       try {
        originalImage = ImageIO.read(url1);
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
    
    private JButton puzzlePieces2[][];

    private ArrayList<JButton> puzzlePieceShuffle = new ArrayList<JButton>();
    public void shuffle()
    {

        for ( int i = 0; i < puzzlePieces.length; i++ )
        {
            // tiny change 1: proper dimensions
            for ( int j = 0; j < puzzlePieces[i].length; j++ )
            {
                // tiny change 2: actually store the values
                puzzlePieceShuffle.add( puzzlePieces[i][j] );
            }
        }
        Collections.shuffle( puzzlePieceShuffle );
        // now you need to find a mode in the list.

        puzzlePieces2 = new JButton[puzzlePieceShuffle
            .size()][puzzlePieceShuffle.size()];
        for ( int i = 0; i < puzzlePieceShuffle.size(); i++ )
        {
            for ( int j = 0; j < puzzlePieceShuffle.size(); j++ )
            {
                puzzlePieces2[i][j] = puzzlePieceShuffle
                    .get( j + ( puzzlePieceShuffle.size() * i ) );
            }
        }
    }
}
