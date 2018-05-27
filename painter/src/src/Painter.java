
package src;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Paint;
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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;

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
    OkCancelDialog textDialog;
    String drawText;
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
    
    Composite composite;
    
    Color color;
    Paint paint;
    
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
          
    }//end setFlagsFalse
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d;
        
        if(!dragging && !adjusted){
            if(image == null){
                image = createImage(imageWidth, imageHeight);
            }
            g2d = (Graphics2D)image.getGraphics();
            
        }else{
            g2d = (Graphics2D) g;
            g.drawImage(image, offsetX, offsetY, this);
            g.drawRect(offsetX, offsetY, imageWidth, imageHeight);
            g2d.clip(new Rectangle2D.Double(offsetX, offsetY,
                imageWidth, imageHeight));
        }
        composite = g2d.getComposite();
        
        if(color != null){
            g2d.setColor(color);
        }else{
            g2d.setColor(new Color(0, 0, 0));
        }
        
        if(thick){
            g2d.setStroke(new BasicStroke(10));
        }else{
            g2d.setStroke(new BasicStroke(1));
        }
        
        if(mouseUp || dragging){
            Point tempStart, tempEnd;
            
            tempStart = new Point(Math.min(end.x, start.x),
                Math.min(end.y, start.y));
            
            tempEnd = new Point(Math.max(end.x, start.x),
                Math.max(end.y, start.y));
            
            tempStart = new Point(Math.max(tempStart.x, offsetX),
                Math.max(tempStart.y, offsetY));
            
            tempEnd = new Point(Math.max(tempEnd.x, offsetX),
                Math.max(tempEnd.y, offsetY));

            tempStart = new Point(Math.min(tempStart.x,
                bufferedImage.getWidth() + offsetX),
                Math.min(tempStart.y, bufferedImage.getHeight() +
                offsetY));

            tempEnd = new Point(Math.min(tempEnd.x,
                bufferedImage.getWidth() + offsetX),
                Math.min(tempEnd.y, bufferedImage.getHeight() +
                offsetY));

            if(!adjusted){
                tempEnd.x -= offsetX;
                tempEnd.y -= offsetY;
                tempStart.x -= offsetX;
                tempStart.y -= offsetY;
                end.x -= offsetX;
                end.y -= offsetY;
                start.x -= offsetX;
                start.y -= offsetY;
                adjusted=true;
            }
            
            int width = tempEnd.x - tempStart.x;
            int height = tempEnd.y - tempStart.y;
            
            // Draw a Line
            if(line){
                Line2D.Double drawLine = new Line2D.Double(
                    start.x, start.y, end.x, end.y);
                
                if(shadow){
                    paint = g2d.getPaint();
                    composite = g2d.getComposite();
                    
                    g2d.setPaint(Color.black);
                    g2d.setComposite(
                            AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, 0.3f));
                    Line2D.Double line2 = new Line2D.Double(start.x + 9, start.y + 9,
                        end.x + 9, end.y + 9);
                    
                    g2d.draw(line2);
                    
                    g2d.setPaint(paint);
                    g2d.setComposite(composite);
                }
                g2d.draw(drawLine);
            }// end Drawing a Line
            
            // Drawing Ellipses
            if(ellipse && width != 0 && height != 0){
                Ellipse2D.Double ellipse = new Ellipse2D.Double(
                    tempStart.x, tempStart.y, width, height);
                
                if(shadow){
                    paint = g2d.getPaint();
                    composite = g2d.getComposite();
                    
                    g2d.setPaint(Color.black);
                    g2d.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.3f));
                    
                    Ellipse2D.Double ellipse2 = new Ellipse2D.Double(
                        tempStart.x + 9, tempStart.y + 9 , width, height);
                    
                    if(solid || shade || transparent || texture){
                        g2d.fill(ellipse2);
                    }else{
                        g2d.draw(ellipse2);
                    }
                    
                    g2d.setPaint(paint);
                    g2d.setComposite(composite);
                }
                
                if(solid){
                    g2d.setPaint(color);
                }
                
                if(shade){
                    g2d.setPaint(new GradientPaint(
                        tempStart.x, tempStart.y, color,
                        tempEnd.x, tempEnd.y, Color.black));
                }
                
                if(texture){
                    Rectangle2D.Double anchor = new Rectangle2D.Double(
                        0, 0, titeImage.getWidth(), tileImage.getHeight());
                    
                    TexturePaint texturePaint = new TexturePaint(
                        tileImage, anchor);
                    
                    g2d.setPaint(texturePaint);
                }
                
                if(transparent){
                    g2d.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.1f));
                }
                
                if(solid || shade || transparent || texture){
                    g2d.fill(ellipse);
                }else{
                    g2d.draw(ellipse);
                }
                
                if(transparent){
                    g2d.setComposite(composite);
                }
            }//end Drawing Ellipses
            
            // Drawing Rectangles
            if(rectangle && width != 0 && height != 0){
                Rectangle2D.Double rectangle = new Rectangle2D.Double(
                    tempStart.x, tempStart.y, width, height);
                
                if(shadow){
                    paint = g2d.getPaint();
                    composite = g2d.getComposite();
                    
                    g2d.setPaint(Color.black);
                    g2d.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.3f));
                }
                
                if(texture){
                    Rectangle2D.Double anchor = new Rectangle2D.Double(
                    0, 0, tileImage.getWidth(), tileImage.getHeight());
                    TexturePaint texturePaint = new TexturePaint(tileImage, anchor);
                    
                    g2d.setPaint(texturePaint);
                }
                
                if(solid || shade || texture || transparent){
                    g2d.fill(rectangle);
                }else{
                    g2d.draw(rectangle);
                }
                
                if(transparent){
                    g2d.setComposite(composite);
                }
            }//end Drawing Rectangles
            
        }
        
        if(!dragging){
            g2d.drawImage(image, offsetX, offsetY, this);
        }
        g.setColor(Color.black);
        g.drawRect(offsetX, offsetY, imageWidth, imageHeight);
    }//end paint
    
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
        
        // Select Color
        if(e.getSource() == colorMenuItem){
            color = JColorChooser.showDialog(this, "Select your color",
                    Color.black);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }//end if - select color
        
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
