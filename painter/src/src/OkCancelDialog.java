
package src;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Alberto Fernandez Saucedo
 */
class OkCancelDialog extends Dialog implements ActionListener 
{
    Button ok, 
           cancel;
    TextField textField;
    public String data;
    
    OkCancelDialog(Frame hostFrame, String title, boolean dModal)
    {
        super(hostFrame, title, dModal);
        setSize(280, 200);
        setLayout(new FlowLayout());
        textField = new TextField(30);
        add(textField);
        ok = new Button("OK");
        add(ok);
        ok.addActionListener((ActionListener)this);
        cancel = new Button("Cancel");
        add(cancel);
        cancel.addActionListener(this);
        data = new String("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ok){
            data = textField.getText();
        }else{
            data = "";
        }
        setVisible(true);
    }
    
}
