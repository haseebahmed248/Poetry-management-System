package pl.crudframes;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import bll.IFacadeBO;

public class EditBook {
    private JFrame editFrame;
    private JPanel editPanel;
    private JTextField bookTitleField;
    private JTextField authorField;
    private JDateChooser dateChooser;
    private DefaultTableModel bookTableModel;
    private IFacadeBO facadeBO;
    private int selectedRow;

    
    public EditBook(IFacadeBO facadeBO, DefaultTableModel bookTableModel, int selectedRow) {
        this.facadeBO = facadeBO;
        this.bookTableModel = bookTableModel;
        this.selectedRow = selectedRow;

        editFrame = new JFrame("Edit Book");
        editPanel = new JPanel(new GridLayout(4, 2));
        
        
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setSize(500, 450);
        editFrame.setLocationRelativeTo(null);// Center the Frame
        editFrame.setResizable(false);
        editFrame.setVisible(true);

        // Get the data from the selected row
        String bookTitle = (String) bookTableModel.getValueAt(selectedRow, 1);
        String author = (String) bookTableModel.getValueAt(selectedRow, 2);
        String deathDate = (String) bookTableModel.getValueAt(selectedRow, 3);

        bookTitleField = new JTextField(bookTitle);
        authorField = new JTextField(author);
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30));
        if (deathDate != null && !deathDate.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = dateFormat.parse(deathDate);
                dateChooser.setDate(parsedDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        editFrame.setLayout(new GridLayout(4, 2));
        // Add components to the edit panel
        editPanel.add(new JLabel("Book Title:"));
        editPanel.add(bookTitleField);
        editPanel.add(new JLabel("Author:"));
        editPanel.add(authorField);
        editPanel.add(new JLabel("Author Death Date:"));
        editPanel.add(dateChooser);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(ev -> {
            String newBookTitle = bookTitleField.getText();
            String newAuthor = authorField.getText();
            java.util.Date newDeathDate = dateChooser.getDate();
            //Validation
            if (newBookTitle.isEmpty() || newAuthor.isEmpty() || newDeathDate == null) {
                JOptionPane.showMessageDialog(editFrame, "Please fill in all the fields and select a Date.");
            } else if (!isValidInput(newBookTitle) || !isValidInput(newAuthor)) {
                JOptionPane.showMessageDialog(editFrame, "Book title and author must contain only Arabic alphabets, numbers, and spaces.");
            } 
            
            else {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(newDeathDate);

                    // Update data in the database
                    int key = (int) bookTableModel.getValueAt(selectedRow, 0);
                    facadeBO.updateBook(key, newBookTitle, newAuthor, formattedDate);

                    // Update table model with edited values
                    bookTableModel.setValueAt(newBookTitle, selectedRow, 1);
                    bookTableModel.setValueAt(newAuthor, selectedRow, 2);
                    bookTableModel.setValueAt(formattedDate, selectedRow, 3);

                    editFrame.dispose(); // Close the edit frame after editing
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Handle exceptions appropriately
                }
            }
        
        });
       
        editPanel.add(confirmButton);
        editFrame.add(editPanel);
        editFrame.setVisible(true);
    }
    
    private boolean isValidInput(String input) {
    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
    	Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
