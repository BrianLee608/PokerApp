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
    private boolean turn;

    public PokerClient() {
        messageArea.setEditable(false);
        //User input box
        frame.getContentPane().add(dataField, "North");
        //Message box
        frame.getContentPane().add(messageArea, "Center");

        dataField.addActionListener(new ActionListener() {
            @Override
            //Only runs when user presses enter
            public void actionPerformed(ActionEvent actionEvent) {
                if(turn){
                    //When user presses enter textfield is send to server
                    out.println(dataField.getText());
                    out.flush();
                    //Clear text field
                    dataField.setText("");
                }
                else{
                    //Clear text field
                    dataField.setText("");
                }
            }
        });
    }

    private void receive(){

        try {
            //While client is active constantly listen for messages from the server
            while(true){
                //Cast message from server as an arraylist of strings
                messages = (ArrayList<String>) in.readObject();
                //Clear message field of previous entries (no redundant appends)
                messageArea.setText("");
                //If first message received is waiting for.. then set actionListener to output nothing
                if(messages.get(0).equals("Waiting for other player to act") || 
                		messages.get(0).equals("Waiting for other player to input name")){
                    turn = false;
                }
                else{
                    turn = true;
                }
                for(int i = 0; i < messages.size(); i++){
                    //Add all messages to messageArea
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
