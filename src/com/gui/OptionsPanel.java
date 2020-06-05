package com.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OptionsPanel extends JPanel {

    public JPanel leftPanel;
    public JTextArea textArea;
    public JScrollPane rightPanel;

    public OptionsPanel() {
        super();
        this.setLayout(null);
        leftPanel = new JPanel();
        leftPanel.setBounds(10, 10, 150, 500);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        this.add(leftPanel);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        rightPanel = new JScrollPane(textArea);
        rightPanel.setBounds(170, 10, 600, 500);
        this.add(rightPanel);
    }

    public void addButton(OptionsButton button) {
        leftPanel.add(button);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }
}
