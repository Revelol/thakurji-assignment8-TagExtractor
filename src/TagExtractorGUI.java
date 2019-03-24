import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class TagExtractorGUI extends JFrame {
    JPanel main, top, middle, bottom;
    JLabel label, textFileLabel, stopFileLabel;
    JButton tagButton, stopButton, quitBtn, extractButton, saveButton;
    JTextArea tags;
    JScrollPane scrollPane;
    File tagFile, stopFile;
    StringBuffer sb;

    public TagExtractorGUI()
    {
        super("Tag Extractor");
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
        tags = new JTextArea();
        scrollPane = new JScrollPane(tags);
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
           if (tagFile != null && stopFile != null) {
               extractTags();
           } else {
               JOptionPane.showMessageDialog(null, "You must select a text and stop file");
           }
        });
        bottom.add(extractButton);

        saveButton = new JButton("Save File");
        saveButton.addActionListener((ActionEvent ae ) -> {
          if (sb != null ){
              tagsWriter();
              JOptionPane.showMessageDialog(null, "You have successfully saved the file");
          } else {
              JOptionPane.showMessageDialog(null, "No tags available to save!");
          }

        });
        bottom.add(saveButton);


        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });
        quitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        quitBtn.setForeground(Color.red);
        bottom.add(quitBtn);
    }

    private void extractTags() {

        Set<String> stopWords = new TreeSet<>();
        try {
            Scanner stopFileReader = new Scanner(stopFile);
            while (stopFileReader.hasNext()) {
                String word = clean(stopFileReader.next());
                stopWords.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("stop file not found:"+e);
        }

        Map<String, Integer> frequencies = new TreeMap<>();
        try {
            Scanner in = new Scanner(tagFile);

            while (in.hasNext()) {
                String word = clean(in.next());

                if (!stopWords.contains(word)) {

                    // Get the old frequency count

                    Integer count = frequencies.get(word);

                    // If there was none, put 1; otherwise, increment the count
                    if (count == null) {
                        count = 1;
                    } else {
                        count = count + 1;
                    }
                    frequencies.put(word, count);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("text file not found:"+e);
        }

        // Print all words and counts
        sb = new StringBuffer();
        for (String key : frequencies.keySet())
        {
            sb.append(key +":" +frequencies.get(key) + "\n");
        }
        tags.setText(sb.toString());

    }


    /**
     Removes characters from a string that are not letters.
     @param s a string
     @return a string with all the letters from s
     */
    public String clean(String s)
    {
        String r = "";
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (Character.isLetter(c))
            {
                r = r + c;
            }
        }
        return r.toLowerCase();
    }


    public void tagsWriter()
    {
        String fileName = "tags.txt";
        try{
            PrintWriter file = new PrintWriter(fileName);
            file.print(sb.toString());
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("error: file not found: ");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("error: exception not found: ");
            e.printStackTrace();

        }
    }

}
