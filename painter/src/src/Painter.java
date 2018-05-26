
package src;

import java.awt.CheckboxMenuItem;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alberto Fernandez Saucedo
 */
public class Painter extends Frame implements ActionListener, MouseListener,
        MouseMotionListener, ItemListener
{
    BufferedImage bufferedImage;
    Image image,
          tileImage;
    MenuBar menuBar;
    Menu menu1, 
         menu2,
         menu3;
    MenuItem newMenuItem,
             openMenuItem,
             saveMenuItem,
             colorMenuItem,
             exitMenuItem;

    CheckboxMenuItem linesMenuItem,
                     ellipsesMenuItem,
                     rectanglesMenuItem,
                     roundedMenuItem,
                     freehandMenuItem,
                     plainMenuItem,
                     solidMenuItem,
                     gradientMenuItem,
                     textureMenuItem,
                     transparentMenuItem,
                     textMenuItem,
                     thickMenuItem,
                     shadowMenuItem;
    
    FileDialog dialog;
    
    int imageWidth,
        imageHeight,
        offsetX,
        offsetY, 
        dots;
    
    Point [] dot;
    
    Point start,
          end;
    
    Boolean rounded = false,
            line = false,
            ellipse = false,
            rectangle = false,
            draw = false,
            text = false,
            solid = false,
            shade = false,
            texture = false,
            transparent = false,
            shadow = false,
            thick = false,
            mouseUp = false,
            adjusted = false,
            dragging = false;
    
    public Painter()
    {
        setLayout(null);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        menuBar = new MenuBar();
        
        menu1 = new Menu("File");
        menu2 = new Menu("Draw");
        menu3 = new Menu("Effects");
        
        newMenuItem = new MenuItem("New");
        menu1.add(newMenuItem);
        newMenuItem.addActionListener(this);
        
        openMenuItem = new MenuItem("Open...");
        menu1.add(openMenuItem);
        openMenuItem.addActionListener(this);
        
        saveMenuItem = new MenuItem("Save As...");
        menu1.add(saveMenuItem);
        saveMenuItem.addActionListener(this);
        
        colorMenuItem = new MenuItem("Selct color...");
        menu3.add(colorMenuItem);
        colorMenuItem.addActionListener(this);
        
        exitMenuItem = new MenuItem("Exit");
        menu1.add(exitMenuItem);
        exitMenuItem.addActionListener(this);
        
        linesMenuItem = new CheckboxMenuItem("Draw lines");
        menu2.add(linesMenuItem);
        linesMenuItem.addItemListener(this);
        
        ellipsesMenuItem = new CheckboxMenuItem("");
        menu2.add(ellipsesMenuItem);
        ellipsesMenuItem.addItemListener(this);
        
        rectanglesMenuItem = new CheckboxMenuItem("Draw rectangles");
        menu2.add(rectanglesMenuItem);
        rectanglesMenuItem.addItemListener(this);

        roundedMenuItem = new CheckboxMenuItem("Draw rounded rectangles");
        menu2.add(roundedMenuItem);
        roundedMenuItem.addItemListener(this);
        
        freehandMenuItem = new CheckboxMenuItem("Draw freehand");
        menu2.add(freehandMenuItem);
        freehandMenuItem.addItemListener(this);
        
        plainMenuItem = new CheckboxMenuItem("Plain");
        menu3.add(plainMenuItem);
        plainMenuItem.addItemListener(this);
        
        solidMenuItem = new CheckboxMenuItem("Solid fill");
        menu3.add(solidMenuItem);
        solidMenuItem.addItemListener(this);
        
        gradientMenuItem = new CheckboxMenuItem("Gradient fill");
        menu3.add(gradientMenuItem);
        gradientMenuItem.addItemListener(this);
        
        textureMenuItem = new CheckboxMenuItem("Texture fill");
        menu3.add(textureMenuItem);
        textureMenuItem.addItemListener(this);
        
        transparentMenuItem = new CheckboxMenuItem("Transparent");
        menu3.add(transparentMenuItem);
        transparentMenuItem.addItemListener(this);
        
        textMenuItem = new CheckboxMenuItem("Draw text");
        menu2.add(textMenuItem);
        textMenuItem.addItemListener(this);
        
        thickMenuItem = new CheckboxMenuItem("Draw thick lines");
        menu3.add(thickMenuItem);
        thickMenuItem.addItemListener(this);
        
        shadowMenuItem = new CheckboxMenuItem("Drop shadow");
        menu3.add(shadowMenuItem);
        shadowMenuItem.addItemListener(this);
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        
        setMenuBar(menuBar);
        
        dialog = new FileDialog(this, "File Dialog");
        bufferedImage = new BufferedImage(imageWidth, imageHeight,
            BufferedImage.TYPE_INT_BGR);
        
        setSize(400, 400);
        setTitle("Painter");
        setVisible(true);
        
        image = createImage(imageWidth, imageHeight);
        textDialog = new OkCancelDialog(this, "Enter your text", true);
        
        try{
            File inputFile = new File("tile.jpg");
            tileImage = ImageIO.read(inputFile);
        }
        catch(java.io.IOException ioe){
            System.out.println("Need tile.jpg.");
            System.exit(0);
        }
        
        this.addWindowListener(
            new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            });
        
        
    }//end constructor
    
    void setFlagsFalse()
    {
        rounded = false;
        line = false;
        ellipse = false;
        rectangle = false;
        draw = false;
        text = false;
        linesMenuItem.setState(false);
        ellipsesMenuItem.setState(false);
        rectanglesMenuItem.setState(false);
        roundedMenuItem.setState(false);
        freehandMenuItem.setState(false);
        textMenuItem.setState(false);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openMenuItem){
            try{
                dialog.setMode(FileDialog.LOAD);
                
                dialog.setVisible(true);
                
                if(dialog.getFile() != ""){
                    File inputFile = new File(dialog.getDirectory() +
                            dialog.getFile());
                    
                    bufferedImage = ImageIO.read(inputFile);
                    
                    if(bufferedImage != null){
                        image = createImage(bufferedImage.getWidth(),
                                bufferedImage.getHeight());
                        
                        Graphics2D g2d = (Graphics2D) image.getGraphics();
                        
                        g2d.drawImage(bufferedImage, null, 0, 0);
                        imageWidth = bufferedImage.getWidth();
                        imageHeight = bufferedImage.getHeight();
                        
                        setSize(imageWidth + 100, imageHeight + 90);
                        
                        repaint();
                    }
                }         
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }//end if - open menu item
        
        // Save menu Item 
        if(e.getSource() == saveMenuItem){
            
            dialog.setMode(FileDialog.SAVE);
            
            dialog.setVisible(true);
            
            try{
                if(dialog.getFile() != ""){
                    String outfile = dialog.getFile();
                    
                    File outputFile = new File(dialog.getDirectory() + 
                            outfile);
                    
                    bufferedImage.createGraphics().drawImage(image, 0, 0, this);
                    
                    ImageIO.write(
                            bufferedImage, 
                            outfile.substring(outfile.length()-3, outfile.length()), 
                            outputFile);
                }
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }//end if - save menu item
        
        // New menu item
        if(e.getSource() == newMenuItem){
            bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_BGR);
            
            image = createImage(300, 300);
            
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
            
            repaint();
            
        }//end if - new menu item
        
    }//end actionPerformed   

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseUp = false;
        start = new Point(e.getX(), e.getY());
        adjusted = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(rounded || line || ellipse || rectangle || draw || text){
            end = new Point(e.getX(), e.getY());
            mouseUp = true;
            dragging = false;
            adjusted = false;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        
        if(new Rectangle(offsetX, offsetY,
            imageWidth, imageHeight).contains(e.getX(), e.getY())){
            if(draw){
                dot[dots] = new Point(e.getX(), e.getY());
                dots++;
            }
        }
        
        if(new Rectangle(offsetX, offsetY,
            imageWidth, imageHeight).contains(start.x, start.y)){
            end = new Point(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == linesMenuItem){
            setFlagsFalse();
            line = true;
            linesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == ellipsesMenuItem){
            setFlagsFalse();
            ellipse = true;
            ellipsesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == rectanglesMenuItem){
            setFlagsFalse();
            rectangle = true;
            rectanglesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == roundedMenuItem){
            setFlagsFalse();
            rounded = true;
            roundedMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == freehandMenuItem){
            setFlagsFalse();
            draw = true;
            freehandMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == solidMenuItem){
            solid = !solid;
                if(solid){
                    texture = false;
                    shade = false;
        }
                
            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
            
        if(e.getSource() == gradientMenuItem){
            shade = !shade;
            if(shade){
                solid = false;
                texture = false;
        }
            
            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
        
        if(e.getSource() == textureMenuItem){
            texture = !texture;
            if(texture){
                shade = false;
                solid = false;
            }

            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == transparentMenuItem){
            transparent = !transparent;
            transparentMenuItem.setState(transparent);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == textMenuItem){
            textDialog.setVisible(true);
            drawText = textDialog.data;
            setFlagsFalse();
            text = true;
            textMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == thickMenuItem){
            thick = thickMenuItem.getState();
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == plainMenuItem){
            solidMenuItem.setState(false);
            gradientMenuItem.setState(false);
            textureMenuItem.setState(false);
            transparentMenuItem.setState(false);
            plainMenuItem.setState(true);
            shade = false;
            solid = false;
            transparent = false;
            texture = false;
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == shadowMenuItem){
            shadow = shadowMenuItem.getState();
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
    
    }//end itemStateChange
    
    public static void main(String[] args)
    {
        new Painter();
        
    }//end main    
}//end Painter
