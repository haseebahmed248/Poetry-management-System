package pl.crudframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;

public class AddRoot {
    private JTextField rootNameField;

	public AddRoot(IFacadeBO facadeBO,int verseID, DefaultTableModel rootsTableModel) {
		JFrame addRootFrame = new JFrame("Add Root");
		addRootFrame.setSize(300, 150);
        addRootFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Center panel with GridLayout
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Root Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        rootNameField = new JTextField();
        rootNameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(34, 139, 34)); 
        addButton.setForeground(Color.BLACK); 
 
        centerPanel.add(nameLabel);
        centerPanel.add(rootNameField);
        centerPanel.add(new JLabel());
        centerPanel.add(addButton);
        
        // Bottom panel for additional options
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Add components to the main panel
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        addRootFrame.add(mainPanel);
      
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rootName = rootNameField.getText();
                
                if (rootName.isEmpty()) {
                    JOptionPane.showMessageDialog(addRootFrame, "Please enter the root name.");
                } else if (!isValidInput(rootName)) {
                    JOptionPane.showMessageDialog(addRootFrame, "Root name must contain only Arabic alphabets, numbers, and spaces.");
                } else {
                    int key = facadeBO.insert_Root(rootName);
					facadeBO.insertVerseRoot(verseID, key);
					rootsTableModel.addRow(new Object[]{key, rootName});
					addRootFrame.dispose(); // Close the JFrame after adding
                }
            }
        });
       
        addRootFrame.setVisible(true);
        addRootFrame.setLocationRelativeTo(null);
    }
	 private boolean isValidInput(String input) {
	    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
	    	Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
}