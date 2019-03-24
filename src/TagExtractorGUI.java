import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class TagExtractorGUI extends JFrame {
    JPanel main, top, middle, bottom;
    JLabel label, textFileLabel, stopFileLabel;
    JButton tagButton, stopButton, quitBtn, extractButton;
    JTextArea fortune;
    JScrollPane scrollPane;
    File tagFile, stopFile;

    public TagExtractorGUI()
    {
        super("Tag Extractorr");
        // configure the GUI
        main = new JPanel();

        createTopPanel();
        createMiddlePanel();
        createBottomPanel();


        main.setLayout(new BorderLayout());
        main.add(top,BorderLayout.NORTH);
        main.add(scrollPane,BorderLayout.CENTER);
        main.add(bottom,BorderLayout.SOUTH);

        // And add Main to the JFrame
        add(main);

        setSize(600, 600);
        //frame.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }
    private void createTopPanel()
    {
        // Top panel

        top = new JPanel();
        top.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();



        label = new JLabel();
        label.setText("Tag Extractor");
        label.setFont(new Font("Helvetica", Font.PLAIN, 36));
        label.setForeground(Color.orange);
        p1.add(label);
        top.add(p1, BorderLayout.NORTH);

        JPanel p2 = new JPanel();

        JLabel j1 = new JLabel("Text File: ");
        p2.add(j1);

        textFileLabel= new JLabel();
        p2.add(textFileLabel);
        top.add(p2, BorderLayout.CENTER);



        JPanel p3 = new JPanel();
        JLabel j2 = new JLabel("Stop File: ");
        p3.add(j2);


        stopFileLabel= new JLabel();
        p3.add(stopFileLabel);
        top.add(p3, BorderLayout.SOUTH);

    }
    private void createMiddlePanel()
    {
        this.fortune = new JTextArea();
        this.scrollPane = new JScrollPane(this.fortune);
    }
    private void createBottomPanel()
    {
        //Control Panel
        bottom = new JPanel();
        tagButton = new JButton("Select Text File");
        // clicker = new ClickListener();

        tagButton.addActionListener((ActionEvent ae) -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                 tagFile = jfc.getSelectedFile();
                textFileLabel.setText(tagFile.getName());
            }
        });
        bottom.add(tagButton);

        stopButton = new JButton("Select Stop File");
        // clicker = new ClickListener();

        stopButton.addActionListener((ActionEvent ae) -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                stopFile = jfc.getSelectedFile();
                stopFileLabel.setText(stopFile.getName());
            }
        });
        bottom.add(stopButton);


        extractButton = new JButton("Extract Tag");

        extractButton.addActionListener((ActionEvent ae) -> {

        });
        bottom.add(stopButton);

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });
        quitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        quitBtn.setForeground(Color.red);
        bottom.add(quitBtn);
    }



}
