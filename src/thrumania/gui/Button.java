package thrumania.gui;

import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by sina on 5/18/16.
 */
public class Button extends JButton {

    private String str;
    private String pictureName;
    private int xCord;
    private int yCord;
    private Dimension d;

    public Button(String str, String pictureName, int xCrod , int yCord , Dimension d) {
        this.pictureName = pictureName;
        this.str = str;
        this.xCord = xCrod;
        this.yCord = yCord;
        this.d = d;
        this.setLayout(null);
        this.setLocation(this.xCord , this.yCord);
        this.setSize(d);
     //   this.setBackground(Color.CYAN.brighter());
        this.setOpaque(true);

//      /  this.setForeground(Color.BLACK);
//        this.setContentAreaFilled(false);
//        this.setOpaque(true);
//        JLabel label = new JLabel();
//        label.setBounds(0,0,50 , 50);
//        label.setIcon(new ImageIcon(ImageUtils.getImage(this.pictureName)));
//        this.add(label);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(ImageUtils.getImage(this.pictureName), 0,0,d.width,d.height,null);
    }
    class myListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
