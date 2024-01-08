package pl.crudframes;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;

public class EditPoem {

	JTextField poemTitle;
	JButton okBtn;
	JButton cancelBtn;

	public EditPoem(IFacadeBO facadeBO,int selectedRow,DefaultTableModel poemTableModel){
		JFrame frame = new JFrame("Edit Poem Title");
		JLabel label=new JLabel("Title:");
		
	
		JPanel mainPanel= new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		poemTitle= new JTextField(20);
		
	
		JPanel buttonPanel= new JPanel(new FlowLayout());
		okBtn=new JButton("Ok");
		cancelBtn=new JButton("Cancel");
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.add(label);
		inputPanel.add(poemTitle);
		
		mainPanel.add(inputPanel);
		mainPanel.add(buttonPanel);
		frame.add(mainPanel);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 120);
		frame.setLocationRelativeTo(null);// Center the Frame
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		String oldTitle=(String)poemTableModel.getValueAt(selectedRow, 1);
		displayOldPoemTitle(oldTitle);
		int key = (int) poemTableModel.getValueAt(selectedRow, 0);
		
		  this.setActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String operation = e.getActionCommand();
	                switch (operation) {
	                    case "Ok":
	                        String poemTitleText = getPoemTitle();
	                        if (poemTitleText.isEmpty() || !isValidInput(poemTitleText)) {
	                            JOptionPane.showMessageDialog(frame, "Poem title must contain only Arabic alphabets, numbers, and spaces.");
	                        } else {
	                            poemTableModel.setValueAt(poemTitleText, selectedRow, 1);
	                            facadeBO.updatePoem(key, poemTitleText);
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
	    }

	private void setActionListener(ActionListener al) {
		okBtn.addActionListener(al);
		cancelBtn.addActionListener(al);
	}

	private void displayOldPoemTitle(String oldtitle) {
		poemTitle.setText(oldtitle);
	}
	
	public String getPoemTitle() {
		return poemTitle.getText();
	}
	 private boolean isValidInput(String input) {
	    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
	    	Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
	
}
