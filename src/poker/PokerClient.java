package poker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PokerClient {

    private Scanner in;
    private PrintWriter out;
    private JFrame frame = new JFrame("PokerApp");
    private JTextField dataField = new JTextField(30);
    private JTextArea messageArea = new JTextArea(6, 30);

    public PokerClient() {
        messageArea.setEditable(false);
        //User input box
        frame.getContentPane().add(dataField, "North");
        //Message box
        frame.getContentPane().add(messageArea, "Center");
        //Initial message is loaded when client constructor is called
        messageArea.setText("Load previous game?");

        dataField.addActionListener(new ActionListener() {
            @Override
            //Only runs when user presses enter
            public void actionPerformed(ActionEvent actionEvent) {

                //Clear message field of previous entries
                messageArea.setText("");
                //Send user textinput to server (essentially our GameDriver class becomes our server)
                out.println(dataField.getText());

                String response;
                //Allow for multi-line response from server (in)
                //Use + "\nnewline" to signal where server output should end (server side)
                while(!(response = in.nextLine()).equals("newline")){
                    System.out.println(response);
                    if(response.equals("exit")){
                        System.exit(0);
                    }
                    messageArea.append(response + "\n");
                }

                //Clear text field
                dataField.setText("");
            }
        });
    }

    public void connectToServer() throws IOException {
        //Connect to socket created in PokerServer class
        Socket socket = new Socket("localhost",9898);
        //Create input/output streams
        in = new Scanner(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void main(String[] args) throws Exception {
        PokerClient client = new PokerClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}
