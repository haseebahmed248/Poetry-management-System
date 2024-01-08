package pl.crudframes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import bll.IFacadeBO;
import javax.swing.JTextField;

public class AssignRoot extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_1;
	private DefaultTableModel rootModel;
	private Map<Integer,String> rootMap;
	private JTextField misra_2textField;
	private JTextField misra_1textField;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AssignRoot(IFacadeBO facadeBO,int verseID,String misra_1,String misra_2) throws SQLException {
		rootMap=facadeBO.getRootsOfVerse(verseID);
//		for(Map.Entry<Integer,String> root:rootMap.entrySet()) {
//			System.out.println(root.getKey()+" "+root.getValue());
//		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 705, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);

		JPanel assignRootsPanel = new JPanel();
		assignRootsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Assign Roots", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblNewLabel_1 = new JLabel("Search Roots");
		
		JScrollPane scrollPane = new JScrollPane();
		
		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rootModel= new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		rootModel.addColumn("Sr.");
		rootModel.addColumn("Root");
		
		table_1.setModel(rootModel);
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(table_1);
		
		JButton assignRootsButton = new JButton("Assign Roots");
		
		
		JButton addRootButton = new JButton("Select Root");
		
		JButton removeRootsButton = new JButton("Deselect Root");
		
		
		JComboBox comboBox = new JComboBox();
		AutoCompleteDecorator.decorate(comboBox);
		
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for(Map.Entry<Integer,String> root:rootMap.entrySet()) {
			comboBoxModel.addElement(root.getValue());
		}
		
		comboBox.setModel(comboBoxModel);
		AutoCompleteDecorator.decorate(comboBox);
		GroupLayout gl_assignRootsPanel = new GroupLayout(assignRootsPanel);
		gl_assignRootsPanel.setHorizontalGroup(
			gl_assignRootsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_assignRootsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_assignRootsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_assignRootsPanel.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(239))
						.addGroup(gl_assignRootsPanel.createSequentialGroup()
							.addGroup(gl_assignRootsPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(addRootButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
								.addComponent(comboBox, 0, 285, Short.MAX_VALUE))
							.addGap(18)))
					.addGap(36)
					.addGroup(gl_assignRootsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_assignRootsPanel.createSequentialGroup()
							.addComponent(assignRootsButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(removeRootsButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_assignRootsPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_assignRootsPanel.setVerticalGroup(
			gl_assignRootsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_assignRootsPanel.createSequentialGroup()
					.addContainerGap(20, Short.MAX_VALUE)
					.addGroup(gl_assignRootsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_assignRootsPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_assignRootsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(assignRootsButton)
								.addComponent(removeRootsButton))
							.addGap(111))
						.addGroup(gl_assignRootsPanel.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(18)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(addRootButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
		);
		assignRootsPanel.setLayout(gl_assignRootsPanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Verse: "+verseID, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
						.addComponent(assignRootsPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(20))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addComponent(assignRootsPanel, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		misra_1textField = new JTextField();
		misra_1textField.setText(misra_1);
		misra_1textField.setColumns(28);
		
		misra_2textField = new JTextField();
		misra_2textField.setColumns(28);
		misra_2textField.setText(misra_2);
		
		panel.add(misra_1textField);
		panel.add(misra_2textField);
		
		addRootButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item=comboBox.getSelectedItem().toString();
				int key=getKeyfromValue(item);
				if(key!=-1) {
					
		            String misra1 = misra_1textField.getText();
		            String misra2 = misra_2textField.getText();
		            
		            if (!misra1.isEmpty() && !misra2.isEmpty() && isValidInput(misra1) && isValidInput(misra2)) {
		                rootModel.addRow(new Object[]{key, item});
		                comboBoxModel.removeElement(item);
		            } else {
		            	
		                String errorMessage = "";
		                if (misra1.isEmpty() || misra2.isEmpty()) {
		                    errorMessage = "Misra fields cannot be empty.";
		                } else {
		                    errorMessage = "Misra fields must contain only Arabic alphabets, numbers, and spaces.";
		                }
		                JOptionPane.showMessageDialog(contentPane, errorMessage);
		            }
				}
			}
		});
		
		assignRootsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        // Validate misra_1textField and misra_2textField input before assigning roots
		        String misra1 = misra_1textField.getText();
		        String misra2 = misra_2textField.getText();
		        
		        if (!misra1.isEmpty() && !misra2.isEmpty() && isValidInput(misra1) && isValidInput(misra2)) {
		            // Traverse the DefaultTableModel to get all values and perform actions
		            int rowCount = rootModel.getRowCount();
		            if (rowCount > 0) {
		                for (int row = 0; row < rowCount; row++) {
		                    int rootID = (Integer) rootModel.getValueAt(row, 0);
		                    facadeBO.insertVerseRoot(verseID, rootID);
		                }
		                rootModel.setRowCount(0);
		            } else {
		                // Handle case when no roots are selected
		                JOptionPane.showMessageDialog(contentPane, "No roots selected to assign.");
		            }
		        } else {
		            // Handle invalid or empty input - show a message or perform necessary actions
		            String errorMessage = "";
		            if (misra1.isEmpty() || misra2.isEmpty()) {
		                errorMessage = "Misra fields cannot be empty.";
		            } else {
		                errorMessage = "Misra fields must contain only Arabic alphabets, numbers, and spaces.";
		            }
		            JOptionPane.showMessageDialog(contentPane, errorMessage);
		        }
		    }
		});
		
		removeRootsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=table_1.getSelectedRow();
				if(selectedRow!=-1) {
					comboBoxModel.addElement((String)rootModel.getValueAt(selectedRow, 1));
					rootModel.removeRow(selectedRow);					
				}
			}
		});
		
		contentPane.setLayout(gl_contentPane);
	}
	
	private int getKeyfromValue(String value){
		for(Map.Entry<Integer,String> root:rootMap.entrySet()) {
			if(root.getValue().equals(value))
			return root.getKey();
		}
		return -1;
	}
	
	 private boolean isValidInput(String input) {
	    	Pattern pattern = Pattern.compile("[\\p{InArabic}0-9\\s]+");
	    	Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
}
