package pl.crudframes;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;

public class EditRoot {
	private JTextField existingRootField;
	private JTextField editedRootField;

	public EditRoot(IFacadeBO facadeBO, DefaultTableModel rootsTableModel, int selectedRow) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	
		JFrame editRootFrame = new JFrame("Edit Root");
		editRootFrame.setSize(400, 150);
		editRootFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Main panel with BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Center panel with GridLayout
		JPanel centerPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel existingLabel = new JLabel("Existing Root:");
		existingLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		existingRootField = new JTextField(rootsTableModel.getValueAt(selectedRow, 1).toString());
		existingRootField.setEditable(false);
		existingRootField.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel editedLabel = new JLabel("Edited Root:");
		editedLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		editedRootField = new JTextField();
		editedRootField.setFont(new Font("Arial", Font.PLAIN, 14));

		JButton editButton = new JButton("Edit");
		editButton.setFont(new Font("Arial", Font.BOLD, 14));
		editButton.setBackground(new Color(255, 165, 0));
		editButton.setForeground(Color.BLACK);

		centerPanel.add(existingLabel);
		centerPanel.add(existingRootField);
		centerPanel.add(editedLabel);
		centerPanel.add(editedRootField);
		centerPanel.add(new JLabel());
		centerPanel.add(editButton);

		mainPanel.add(centerPanel, BorderLayout.CENTER);

		editRootFrame.add(mainPanel);
		editRootFrame.setVisible(true);

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String rootName = editedRootField.getText();		
				if (rootName.isEmpty()) {
				    JOptionPane.showMessageDialog(editRootFrame, "Please enter the root name.");
				} else if (!isValidInput(rootName)) {
				    JOptionPane.showMessageDialog(editRootFrame, "Root name must contain only Arabic alphabets, numbers, and spaces.");
				} else {
				try {
					editRoot(facadeBO, rootsTableModel, selectedRow);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				editRootFrame.dispose();
			}
			}
		});

		editRootFrame.setLocationRelativeTo(null);
	}

	private void editRoot(IFacadeBO facadeBO, DefaultTableModel rootsTableModel, int selectedRow) throws SQLException {
		String editedItem = editedRootField.getText();
		if (editedItem != null && !editedItem.trim().isEmpty()) {
			// PASS EDITED DATA TO DATABASE HERE
			int key = (int) rootsTableModel.getValueAt(selectedRow, 0);
			facadeBO.update_Root(key, editedItem);
			rootsTableModel.setValueAt(editedItem, selectedRow, 1);
		}
	}

	 private boolean isValidInput(String input) {
	    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
	    	Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
}
