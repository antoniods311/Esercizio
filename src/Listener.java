import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Listener implements ActionListener{

    public static final String CONNECT = "connect", DISCONNECT = "disconnect", START = "start", STOP = "stop";

    private JTextField ip;
    private JTextField porta;
    
    private JProgressBar cpu;
    private JProgressBar memory;
    private JProgressBar disk;
    private JProgressBar network;
    
    private boolean connected = false, transmitting = false;
    
    private Downloader downloader = null;
    private PrintWriter netPw;
    private Scanner scan;
    private Socket sock;
    private Frame frame;

    public Listener(Frame frame, JProgressBar cpu, JProgressBar memory, JProgressBar disk, JProgressBar network, JTextField ip, JTextField porta) {
        this.frame = frame;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.network = network;
        this.ip = ip;
        this.porta = porta;
    }
    private void setupConnection()throws UnknownHostException, IOException{
        sock = new Socket(ip.getText(), Integer.parseInt(porta.getText()));
        OutputStream os = sock.getOutputStream();
        netPw = new PrintWriter(new OutputStreamWriter(os));
        scan = new Scanner(sock.getInputStream());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals(Listener.CONNECT)) {
            try {
                setupConnection();
                connected = true;
                System.out.println("Connected");
            }catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Impossiblie connettersi al server: \n" + e1.getMessage());
                e1.printStackTrace();
                return;
            }
            JOptionPane.showMessageDialog(null, "Connessione stabilita");
        }
        else if(cmd.equals(Listener.START)) {
            try {
                downloader = new Downloader(cpu, memory, disk, network, scan);
            }catch(IOException e1) {
                JOptionPane.showMessageDialog(null, "Impossiblie scaricare: \n" + e1.getMessage());
                e1.printStackTrace();
            }
            transmitting = true;
            netPw.println(cmd);
            netPw.flush();
            Thread t = new Thread(downloader);
            t.start();
            JOptionPane.showMessageDialog(null, "Download avviato");
        }
        else if(cmd.equals(Listener.STOP)) {
            netPw.println(cmd);
            netPw.flush();
            transmitting = false;
            JOptionPane.showMessageDialog(null, "Download fermato");
        }
        else if(cmd.equals(Listener.DISCONNECT)) {
            netPw.println(cmd);
            netPw.flush();
            netPw.close();
            scan.close();
            connected = false;
            try {
                sock.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Connessione chiusa");
        }
        frame.setButtons(connected, transmitting);
    }
}
