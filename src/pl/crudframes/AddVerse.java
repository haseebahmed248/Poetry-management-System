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

public class AddVerse {
	private JButton addMoreBtn;
	private JButton saveBtn;
	private List<JTextField> versesList;
	private JPanel versesPanel;
	private int VERSE_NUM;
	
	public AddVerse(IFacadeBO facadeBO,int poemID,DefaultTableModel verseTableModel){
		JFrame frame = new JFrame("Add Verses");
		
		versesList = new ArrayList<>();
		addMoreBtn=new JButton("Add More");
		saveBtn=new JButton("Save");
		VERSE_NUM=1;
		
		//Labels
		JLabel misra_1Lbl=new JLabel("Misra 1:");
		JLabel misra_2Lbl=new JLabel("Misra 2:");
		//TextFields
		JTextField misra_1Text=new JTextField(20);
		JTextField misra_2Text=new JTextField(20);
		versesList.add(misra_1Text);
		versesList.add(misra_2Text);
		//PANELS
		JPanel mainPanel=new JPanel(new BorderLayout());
		versesPanel=new JPanel();
		versesPanel.setLayout(new BoxLayout(versesPanel, BoxLayout.Y_AXIS));
		JPanel versePanel=new JPanel(new FlowLayout());
		versePanel.setBorder(BorderFactory.createTitledBorder("Verse "+VERSE_NUM));
		JPanel buttonPanel=new JPanel(new FlowLayout());
		
		//
		versePanel.add(misra_1Lbl);
		versePanel.add(misra_1Text);
		versePanel.add(misra_2Lbl);
		versePanel.add(misra_2Text);
		versesPanel.add(versePanel);
		buttonPanel.add(addMoreBtn);
		buttonPanel.add(saveBtn);
		JScrollPane scrollPane=new JScrollPane(versesPanel);
		mainPanel.add(scrollPane,BorderLayout.CENTER);
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		
		//ActionListener
		this.setActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String operation = e.getActionCommand();
				switch(operation) {
				case "Add More":
					addVerses();
					break;
				case "Save":
				    boolean misraValidationFailed = false;
				    boolean emptyMisra1 = false;
				    boolean emptyMisra2 = false;

				    for (int i = 0; i < versesList.size(); i += 2) {
				        String verse1 = versesList.get(i).getText();
				        String verse2 = versesList.get(i + 1).getText();

				        if (verse1.isEmpty()) {
				            emptyMisra1 = true;
				        }
				        if (verse2.isEmpty()) {
				            emptyMisra2 = true;
				        }

				        if (verse1.isEmpty() || verse2.isEmpty() || !isValidInput(verse1) || !isValidInput(verse2)) {
				            misraValidationFailed = true;
				            break;
				        }
				    }

				    if (misraValidationFailed) {
				        if (emptyMisra1 && emptyMisra2) {
				            JOptionPane.showMessageDialog(frame, "Misra 1 and Misra 2 fields cannot be empty.\nThey must contain only Arabic alphabets, numbers, and spaces.");
				        } else if (emptyMisra1) {
				            JOptionPane.showMessageDialog(frame, "Misra 1 field cannot be empty.\nIt must contain only Arabic alphabets, numbers, and spaces.");
				        } else if (emptyMisra2) {
				            JOptionPane.showMessageDialog(frame, "Misra 2 field cannot be empty.\nIt must contain only Arabic alphabets, numbers, and spaces.");
				        } else {
				            JOptionPane.showMessageDialog(frame, "Misra fields must contain only Arabic alphabets, numbers, and spaces.");
				        }
				    } else {
	                    try {
	                        List<Integer> keys = facadeBO.addVerses(poemID, getVerses());
	                        List<String> verses = getVerses();
	                        
	                        // Perform necessary operations after validation
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                        // Handle exceptions appropriately
	                    }
	                }
	                break;
	            default:
	                break;
	        }
	    }
	});
		        frame.add(mainPanel);
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        frame.setSize(600, 300);
		        frame.setLocationRelativeTo(null);
		        frame.setResizable(false);
		        frame.setVisible(true);
		    }
		
	private void addVerses() {
		VERSE_NUM+=1;
		JPanel innerPanel = new JPanel(new FlowLayout());
		innerPanel.setBorder(BorderFactory.createTitledBorder("Verse "+VERSE_NUM));
		//Labels
		JLabel misra_1Lbl=new JLabel("Misra 1:");
		JLabel misra_2Lbl=new JLabel("Misra 2:");
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
		addMoreBtn.addActionListener(al);
		saveBtn.addActionListener(al);
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
