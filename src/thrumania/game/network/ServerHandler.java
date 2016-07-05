package thrumania.game.network;

import thrumania.gui.PlayPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by mohamadamin on 7/5/16.
 */
public class ServerHandler implements Runnable {

    private Socket socket;
    private PlayPanel playPanel;
    private DataInputStream input;
    private DataOutputStream output;

    public ServerHandler(Socket socket, PlayPanel panel) {
        this.socket = socket;
        this.playPanel = panel;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            while (true) {
                Thread.sleep(30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
