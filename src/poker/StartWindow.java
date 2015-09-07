package poker;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame{

    public JPanel panel;
    public JLabel title;
    public JLabel footer;

    public StartWindow(){
        //Set frame characteristics
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanels are just containers inside the frame
        panel = new JPanel();
        //Can choose different layout managers
        panel.setLayout(new GridLayout(2,1));

        title = new JLabel("Texas Holdem", JLabel.CENTER);
        title.setAlignmentX(SwingConstants.CENTER);
        title.setFont(new Font("Times", Font.PLAIN, 20));

        footer = new JLabel("Brian Lee & Dennis Deng 2015(c)", JLabel.CENTER);
        footer.setFont(new Font("Times", Font.PLAIN, 14));

        panel.add(title);
        panel.add(footer);
        //Add to frame
        add(panel);
        setVisible(true);
    }

}
