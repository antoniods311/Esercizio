import java.awt.Color;
import java.awt.TextArea;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class Downloader implements Runnable{
    private Scanner scan;
    private boolean running;
    
    private JButton connect;
    private JButton disconnect;
    private JButton stop;
    private JButton start;
    private JProgressBar cpu;
    private JProgressBar memory;
    private JProgressBar disk;
    private JProgressBar network;
    
    private JRadioButton Vm1;
    private JRadioButton Vm2;
    private JRadioButton Vm3;
    
    public Downloader(JButton start, JButton stop, JButton disconnect, JButton connect, JProgressBar cpu, JProgressBar memory, JProgressBar disk, JProgressBar network, Scanner scan) throws IOException{
    	this.start = start;
    	this.stop = stop;
    	this.disconnect = disconnect;
        this.connect = connect;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.network = network;
        this.scan = scan;
        running = false;
    }
    @Override
    public void run() {
        if(!running) {
            running = true;
            while(running) {
                String cmd = scan.nextLine();
                System.out.println(cmd);
                if(cmd.equals("*")) {
                	this.stop.setEnabled(false);
                	this.start.setEnabled(true);
                	this.disconnect.setEnabled(true);
                	this.connect.setEnabled(false);
                	running = false;
                }else{ //mod
                    String info[] = cmd.split(":");
                    int CPU = Integer.parseInt(info[0]);
                    int MEMORY = Integer.parseInt(info[1]);
                    int DISK = Integer.parseInt(info[2]);
                    int NETWORK = Integer.parseInt(info[3]);
                    cpu.setValue(CPU);
                    memory.setValue(MEMORY);
                    disk.setValue(DISK);
                    network.setValue(NETWORK);
                }
            }
        }
    }
}
