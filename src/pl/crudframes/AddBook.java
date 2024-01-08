package pl.crudframes;

import java.awt.Dimension;
import java.awt.GridLayout;
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

public class AddBook {
    private IFacadeBO facadeBO;
    private DefaultTableModel bookTableModel;
    private JFrame addFrame;
    private JTextField bookTitleField;
    private JTextField authorField;
    private JDateChooser dateChooser;

    public AddBook(IFacadeBO facadeBO, DefaultTableModel bookTableModel) {
        this.facadeBO = facadeBO;
        this.bookTableModel = bookTableModel;

        addFrame = new JFrame("Add Book");
        
        
        JPanel addPanel = new JPanel(new GridLayout(4, 2));
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setSize(500, 450);
        addFrame.setLocationRelativeTo(null);// Center the Frame
        addFrame.setResizable(false);
        addFrame.setVisible(true);

        bookTitleField = new JTextField();
        authorField = new JTextField();
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30));

        addFrame.setLayout(new GridLayout(4, 2));
        addPanel.add(new JLabel("Book Title:"));
        addPanel.add(bookTitleField);
        addPanel.add(new JLabel("Author:"));
        addPanel.add(authorField);
        addPanel.add(new JLabel("Author Death Date:"));
        addPanel.add(dateChooser);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(ev -> {
            String bookTitle = bookTitleField.getText();
            String author = authorField.getText();
            java.util.Date deathDate = dateChooser.getDate();
            //Validation
            if (bookTitle.isEmpty() || author.isEmpty() || deathDate == null) {
                JOptionPane.showMessageDialog(addFrame, "Please fill in all the fields and Correct Date.");
            } else if (!isValidInput(bookTitle) || !isValidInput(author)) {
                JOptionPane.showMessageDialog(addFrame, "Book title and author must contain only Arabic alphabets, numbers, and spaces.");
            } 
            	else {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(deathDate);

                    // Insert data into the database
                    int book_id = facadeBO.insertBook(bookTitle, author, formattedDate);
                    System.out.print("book_id_new" + book_id);

                    // Update table model with new entry
                    bookTableModel.addRow(new Object[]{book_id, bookTitle, author, formattedDate});

                    addFrame.dispose(); // Close the add frame after adding
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Handle exceptions appropriately
                }
                }
            });

        addPanel.add(confirmButton);
        addFrame.add(addPanel);
        addFrame.setVisible(true);
    }
    
    private boolean isValidInput(String input) {
    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
    	Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
}
