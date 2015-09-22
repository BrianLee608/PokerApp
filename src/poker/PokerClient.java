package poker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class PokerClient {

    private ObjectInputStream in;
    private PrintWriter out;
    private JFrame frame = new JFrame("PokerApp");
    private JTextField dataField = new JTextField(30);
    private JTextArea messageArea = new JTextArea(6, 30);
    private ArrayList<String> messages;

    public PokerClient() {
        messageArea.setEditable(false);
        //User input box
        frame.getContentPane().add(dataField, "North");
        //Message box
        frame.getContentPane().add(messageArea, "Center");
        //Initial message is loaded when client constructor is called

        dataField.addActionListener(new ActionListener() {
            @Override
            //Only runs when user presses enter
            public void actionPerformed(ActionEvent actionEvent) {

                //Send user textinput to server (essentially our GameDriver class becomes our server)
                out.println(dataField.getText());

                //Clear text field
                dataField.setText("");
            }
        });
    }

    private void receive(){

        try {
            while(true){
                messages = (ArrayList<String>) in.readObject();
                System.out.println(messages);
                //Clear message field of previous entries
                messageArea.setText("");
                for(int i = 0; i < messages.size(); i++){
                    messageArea.append(messages.get(i) + "\n");
                }
            }
        }catch(IOException i){
            i.printStackTrace();
        }catch(ClassNotFoundException j){
            j.printStackTrace();
        }
    }

    private void connectToServer() throws IOException {
        //Connect to socket created in PokerServer class
        Socket socket = new Socket("localhost",9898);
        //Create input/output streams
        in = new ObjectInputStream(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void main(String[] args) throws Exception {
        PokerClient client = new PokerClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
        client.receive();
    }
}
