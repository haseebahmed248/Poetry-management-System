package pl.crudframes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;

public class EditVerse {
	private JTextField misra_1Text;
	private JTextField misra_2Text;
	private JButton okBtn;
	private JButton cancelBtn;
	public EditVerse(IFacadeBO facadeBO,int selectedRow,DefaultTableModel verseTableModel){
		JFrame frame = new JFrame("Edit Verse");
		misra_1Text=new JTextField(20);
		misra_2Text=new JTextField(20);
		okBtn =new JButton("Ok");
		cancelBtn=new JButton("Cancel");
		
		JPanel versePanel= new JPanel(new FlowLayout());
		JLabel misra_1Lbl=new JLabel("Misra 1:");
		JLabel misra_2Lbl=new JLabel("Misra 2:");
		
		versePanel.add(misra_1Lbl);
		versePanel.add(misra_1Text);
		versePanel.add(misra_2Lbl);
		versePanel.add(misra_2Text);
		
		
		
		JPanel buttonPanel= new JPanel(new FlowLayout());
	
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		JPanel mainPanel= new JPanel(new BorderLayout());
		mainPanel.add(versePanel,BorderLayout.CENTER);
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		String misra_1=(String)verseTableModel.getValueAt(selectedRow, 1);
		String misra_2=(String)verseTableModel.getValueAt(selectedRow, 2);
		displayOldVerse(misra_1,misra_2);
		int key = (int) verseTableModel.getValueAt(selectedRow, 0);
		this.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String operation = e.getActionCommand();
                switch (operation) {
                    case "Ok":
                        String misra_1 = misra_1Text.getText();
                        String misra_2 = misra_2Text.getText();

                        // Validation checks for empty fields and valid input
                        if (misra_1.isEmpty() || misra_2.isEmpty()) {
                            JOptionPane.showMessageDialog(mainPanel, "Please fill in all the fields.");
                        } else if (!isValidInput(misra_1) || !isValidInput(misra_2)) {
                            JOptionPane.showMessageDialog(mainPanel, "Misras must contain only Arabic alphabets, numbers, and spaces.");
                        } else {
                            // Process validated input
                            String[] verse = getVerse();
                            verseTableModel.setValueAt(verse[0], selectedRow, 1);
                            verseTableModel.setValueAt(verse[1], selectedRow, 2);
                            facadeBO.updateVerse(key, verse[0], verse[1]);
                            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                        }
                        break;
                    case "Cancel":
                        SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                        break;
                    default:
                        break;
                }
            }
        });

		
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 120);
		frame.setLocationRelativeTo(null);// Center the Frame
		frame.setResizable(false);
		frame.setVisible(true);
	}
	private void setActionListener(ActionListener al) {
		okBtn.addActionListener(al);
		cancelBtn.addActionListener(al);
	}

	private void displayOldVerse(String misra_1,String misra_2) {
		this.misra_1Text.setText(misra_1);
		this.misra_2Text.setText(misra_2);
	}
	
	public String[] getVerse() {
		String[] verse=new String[2];
		verse[0]=misra_1Text.getText();
		verse[1]=misra_2Text.getText();
		return verse;
	}
	 private boolean isValidInput(String input) {
	    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
	    	Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
	
}
