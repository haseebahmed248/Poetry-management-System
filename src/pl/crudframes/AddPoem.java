package pl.crudframes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;

public class AddPoem {
    private List<JTextField> versesList;
    private JTextField poemText;
    private JPanel versesPanel;
    private JButton addVersesBtn;
    private JButton saveBtn;
    private JButton closeBtn;
    private int VERSE_NUM;

    public AddPoem(IFacadeBO facadeBO, int bookID, DefaultTableModel poemTableModel,
                   DefaultTableModel verseTableModel) {
        JFrame frame = new JFrame("Add Poem");

        versesList = new ArrayList<>();
        VERSE_NUM = 1;

        // Labels
        JLabel misra_1Lbl = new JLabel("Misra 1:");
        JLabel misra_2Lbl = new JLabel("Misra 2:");
        // TextFields
        JTextField misra_1Text = new JTextField(20);
        JTextField misra_2Text = new JTextField(20);
        versesList.add(misra_1Text);
        versesList.add(misra_2Text);

        // North
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel poemTitle = new JLabel("Title");
        poemText = new JTextField(25);
        titlePanel.add(poemTitle);
        titlePanel.add(poemText);
        // Center
        versesPanel = new JPanel();
        versesPanel.setLayout(new BoxLayout(versesPanel, BoxLayout.Y_AXIS));
        JPanel versePanel = new JPanel(new FlowLayout());
        versePanel.setBorder(BorderFactory.createTitledBorder("Verse " + VERSE_NUM));
        versePanel.add(misra_1Lbl);
        versePanel.add(misra_1Text);
        versePanel.add(misra_2Lbl);
        versePanel.add(misra_2Text);
        versesPanel.add(versePanel);
        JScrollPane versesScrollPane = new JScrollPane(versesPanel);
        // South
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addVersesBtn = new JButton("Add Verses");
        saveBtn = new JButton("Save");
        closeBtn = new JButton("Close");
        buttonPanel.add(addVersesBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(closeBtn);

        // North,Center,South
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);// North
        mainPanel.add(versesScrollPane, BorderLayout.CENTER);// Center
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);// South

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);// Center the Frame
        frame.setResizable(false);
        frame.setVisible(true);

        this.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String operation = e.getActionCommand();
                switch (operation) {
                    case "Add Verses":
                        addVerses();
                        break;
                    case "Save":
                        // Validation
                        String poemTitle = poemText.getText();
                        boolean misraValidationFailed = false;
                        
                        // Validating poem title
                        if (poemTitle.isEmpty() || !isValidInput(poemTitle)) {
                            JOptionPane.showMessageDialog(frame, "Poem title must contain only Arabic alphabets, numbers, and spaces.");
                        } else {
                            // Validating Misra fields
                            for (JTextField verseField : versesList) {
                                String verseText = verseField.getText();
                                if (verseText.isEmpty() || !isValidInput(verseText)) {
                                    misraValidationFailed = true;
                                    break;
                                }
                            }
                            if (misraValidationFailed) {
                                JOptionPane.showMessageDialog(frame, "Misra fields must contain only Arabic alphabets, numbers, and spaces.");
                            } else {
                                int key = facadeBO.addPoem(bookID, poemText.getText());
								poemTableModel.addRow(new Object[]{key, poemText.getText()});
								List<Integer> keys = facadeBO.addVerses(key, getVerses());
								List<String> verses = getVerses();
								for (int i = 0; i < verses.size(); i += 2) {
								    verseTableModel.addRow(new Object[]{keys.get(i / 2), verses.get(i), verses.get(i + 1)});
								}
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                            }
                        }
                        break;
                    case "Close":
                        SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void addVerses() {
        VERSE_NUM += 1;
        JPanel innerPanel = new JPanel(new FlowLayout());
        innerPanel.setBorder(BorderFactory.createTitledBorder("Verse " + VERSE_NUM));

        // Labels
        JLabel misra_1Lbl = new JLabel("Misra 1:");
        JLabel misra_2Lbl = new JLabel("Misra 2:");
        //
        JTextField misra_1Text = new JTextField(20);// 0
        JTextField misra_2Text = new JTextField(20);// 1

        innerPanel.add(misra_1Lbl);
        innerPanel.add(misra_1Text);
        innerPanel.add(misra_2Lbl);
        innerPanel.add(misra_2Text);

        versesList.add(misra_1Text);
        versesList.add(misra_2Text);

        versesPanel.add(innerPanel);
        versesPanel.revalidate();// Refresh
        versesPanel.repaint();
    }

    private void setActionListener(ActionListener al) {
        addVersesBtn.addActionListener(al);
        saveBtn.addActionListener(al);
        closeBtn.addActionListener(al);
    }

    public List<String> getVerses() {
        List<String> verses = new ArrayList<>();
        for (JTextField verse : versesList) {
            verses.add(verse.getText());
        }
        return verses;
    }
    
    private boolean isValidInput(String input) {
        Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
