
package src;

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
    
    public Painter()
    {
        setLayout(null);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        menuBar = new MenuBar();
        
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
