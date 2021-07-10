import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class Frame extends JFrame{
    private JPanel central;
    private JPanel nord;
    private JPanel sud;
    private JPanel pannelloSud;
    private JPanel pannelloCentro;
    private JPanel statCpu;
    private JPanel statMem;
    private JPanel statDisk;
    private JPanel statNet;
    
    private JPanel address;
    private JPanel port;
    
    private JPanel status;
    
    private JPanel bottoniSud;
    private JPanel bottoniNord;
    
    private JProgressBar cpu;
    private JProgressBar memory;
    private JProgressBar disk;
    private JProgressBar network;
    
    private JRadioButton Vm1;
    private JRadioButton Vm2;
    private JRadioButton Vm3;

    private ButtonGroup bg;

    private JTextField ip;
    private JTextField porta;

    private JButton connect;
    private JButton disconnect;
    private JButton start;
    private JButton stop;

    public Frame() {

        setTitle("Capone Franco 1884168");
        ip = new JTextField(20);
        ip.setText("localhost");
        porta = new JTextField(20);
        porta.setText("4400");

        connect = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        start = new JButton("Start");
        stop = new JButton("Stop");

        address = new JPanel(new FlowLayout());
        address.add(new JLabel("Server Address"));
        address.add(ip);

        port = new JPanel(new FlowLayout());
        port.add(new JLabel("Porta"));
        port.add(porta);

        // Connessione -
        nord = new JPanel(new FlowLayout());
        nord.add(address);
        nord.add(port);
        nord.add(connect);
        nord.add(disconnect);

        // VM -
        pannelloSud = new JPanel(new BorderLayout());
        pannelloSud.add(new JLabel("VM"), BorderLayout.NORTH);
        Vm1 = new JRadioButton("vm1");
        Vm1.setActionCommand("vm1");
        Vm2 = new JRadioButton("vm2");
        Vm2.setActionCommand("vm2");
        Vm3 = new JRadioButton("vm3");
        Vm3.setActionCommand("vm3");

        bg = new ButtonGroup();
        bg.add(Vm1);
        bg.add(Vm2);
        bg.add(Vm3);
        Vm1.setSelected(true);

        bottoniNord = new JPanel(new FlowLayout());
        bottoniNord.add(Vm1);
        bottoniNord.add(Vm2);
        bottoniNord.add(Vm3);
        pannelloSud.add(bottoniNord, BorderLayout.SOUTH);
        sud = new JPanel(new BorderLayout());
        bottoniSud = new JPanel(new FlowLayout());
        bottoniSud.add(start);
        bottoniSud.add(stop);
        sud.add(pannelloSud, BorderLayout.NORTH);
        sud.add(bottoniSud, BorderLayout.SOUTH);

        statCpu = new JPanel(new GridLayout(1,2));
        cpu = new JProgressBar(0, 100);
        statCpu.add(new JLabel("CPU"));
        statCpu.add(cpu);
        
        statMem = new JPanel(new GridLayout(1,2));
        memory = new JProgressBar(0, 100);
        statMem.add(new JLabel("MEMORY"));
        statMem.add(memory);
        
        statDisk = new JPanel(new GridLayout(1,2));
        disk = new JProgressBar(0, 100);
        statDisk.add(new JLabel("DISK"));
        statDisk.add(disk);
        
        statNet = new JPanel(new GridLayout(1,2));
        network = new JProgressBar(0, 100);
        statNet.add(new JLabel("NETWORK"));
        statNet.add(network);

        // CENTRO
        central = new JPanel(new BorderLayout());
        central.add(new JLabel("VM Status"), BorderLayout.NORTH);
        pannelloCentro = new JPanel(new GridLayout (4, 1));
        pannelloCentro.add(statCpu);
        pannelloCentro.add(statMem);
        pannelloCentro.add(statDisk);
        pannelloCentro.add(statNet);
        
        cpu.setStringPainted(true);
        memory.setStringPainted(true);
        disk.setStringPainted(true);
        network.setStringPainted(true);
        central.add(pannelloCentro, BorderLayout.CENTER);

        this.getContentPane().add(central, BorderLayout.CENTER);
        this.getContentPane().add(sud, BorderLayout.SOUTH);
        this.getContentPane().add(nord, BorderLayout.NORTH);

        ActionListener listener = new Listener(this, start, stop, disconnect, connect, cpu, memory, disk, network, ip, porta, bg);
        connect.setActionCommand(Listener.CONNECT);
        connect.addActionListener(listener);
        disconnect.setActionCommand(Listener.DISCONNECT);
        disconnect.addActionListener(listener);
        stop.setActionCommand(Listener.STOP);
        stop.addActionListener(listener);
        start.setActionCommand(Listener.START);
        start.addActionListener(listener);

        setLocation(200,100);
        setButtons(false,false);
        this.setVisible(true);
    }
    public void setButtons(boolean connected, boolean transmitting) {
        if(connected) {
            connect.setEnabled(false);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            if(transmitting) {
                 connect.setEnabled(false);
                 disconnect.setEnabled(false);
                 stop.setEnabled(true);
                 start.setEnabled(false);
             }
             else {
                 connect.setEnabled(false);
                 stop.setEnabled(false);
                 disconnect.setEnabled(true);
                 start.setEnabled(true);
             }
        }
        else {
        	setDefaultCloseOperation(EXIT_ON_CLOSE);
            connect.setEnabled(true);
            disconnect.setEnabled(false);
            stop.setEnabled(false);
            start.setEnabled(false);
        }
    }
}
