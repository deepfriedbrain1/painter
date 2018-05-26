
package src;

import java.awt.CheckboxMenuItem;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Alberto Fernandez Saucedo
 */
public class Painter extends Frame implements ActionListener, MouseListener,
        MouseMotionListener
{
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
                     rectangleMenuItem,
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
        
    }//end constructor
    

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }   

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void main(String[] args)
    {
        new Painter();
        
    }//end main
}
