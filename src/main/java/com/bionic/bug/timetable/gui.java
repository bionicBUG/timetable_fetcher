package com.bionic.bug.timetable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class gui implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userText;
    private JPasswordField passText;
    private JButton button;
    private JLabel output;

    public gui() throws FileNotFoundException{
        frame = new JFrame("Hail Hitler");
        panel = new JPanel();

        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("Username");
        userLabel.setBounds(150,30,80,25);
        panel.add(userLabel);

        passLabel = new JLabel("Password");
        passLabel.setBounds(150,60,80,25);
        panel.add(passLabel);

        userText = new JTextField("");
        userText.setBounds(250,30,80,25);
        panel.add(userText);

        passText = new JPasswordField();
        passText.setBounds(250,60,80,25);
        panel.add(passText);

        if (new File("saved.txt").exists()) {
            Scanner reader = new Scanner(new File("saved.txt"));
            userText.setText(reader.next());
            passText.setText(reader.next());
        }

        button = new JButton("get timetable uwu");
        button.setBounds(160,85,140,25);
        button.addActionListener(this);
        panel.add(button);

        output = new JLabel("");
        output.setBounds(40,125,370,175);
        panel.add(output);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (!new File("saved.txt").exists()) {
            try (FileWriter writer = new FileWriter("saved.txt")) {
                writer.write(userText.getText() + " " + passText.getText());
            } catch (Exception flieexcep) {
                flieexcep.printStackTrace();
            }
        }

        try {
            output.setText(new driver().webdriver(userText.getText(), passText.getText()));
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}