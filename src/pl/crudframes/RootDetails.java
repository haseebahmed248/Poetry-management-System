package pl.crudframes;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import bll.IFacadeBO;
import transferobjects.PoemTO;
import transferobjects.VerseTO;

public class RootDetails extends JFrame {
	private JPanel contentPane;
    private JTable table;
    private JLabel rootWordLbl;
    private JLabel versesCountLbl;
    private JButton btnNewButton;
    private JPanel versesPanel;
    
    private IFacadeBO facadeBO;
    private JTabbedPane tabbedPane;
    private DefaultTableModel poemTableModel;

    
    /**
     * Create the frame.
     */
    public RootDetails(IFacadeBO facadeBO, JTabbedPane tabbedPane, DefaultTableModel poemTableModel) {
        this.facadeBO = facadeBO;
        this.tabbedPane = tabbedPane;
        this.poemTableModel = poemTableModel;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        setBounds(100, 100, 501, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Root", TitledBorder.LEADING, TitledBorder.TOP, null, null));

         versesPanel = new JPanel();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Verses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                                        .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addComponent(versesPanel, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(versesPanel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addGap(4))
        );
        versesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        DefaultTableModel versesTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        btnNewButton = new JButton("Show Poem");
        versesPanel.add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
        gl_buttonPanel.setHorizontalGroup(
                gl_buttonPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_buttonPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_buttonPanel.setVerticalGroup(
                gl_buttonPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_buttonPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(23, Short.MAX_VALUE))
        );

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id", "Misra 1", "Misra 2"}
        ));
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        scrollPane.setViewportView(table);
        buttonPanel.setLayout(gl_buttonPanel);

        rootWordLbl = new JLabel();

        versesCountLbl = new JLabel();
        GroupLayout gl_headerPanel = new GroupLayout(headerPanel);
        gl_headerPanel.setHorizontalGroup(
                gl_headerPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_headerPanel.createSequentialGroup()
                                .addGap(1)
                                .addComponent(rootWordLbl)
                                .addPreferredGap(ComponentPlacement.RELATED, 340, Short.MAX_VALUE)
                                .addComponent(versesCountLbl)
                                .addContainerGap())
        );
        gl_headerPanel.setVerticalGroup(
                gl_headerPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_headerPanel.createSequentialGroup()
                                .addGap(3)
                                .addGroup(gl_headerPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rootWordLbl)
                                        .addComponent(versesCountLbl))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanel.setLayout(gl_headerPanel);
        contentPane.setLayout(gl_contentPane);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	switchToPoemsTab();
                showPoemAction(table);
                dispose();
            }
        });
        setVisible(true);
    }

    public void openRootDetailsScreen(int selectedRootId, String rootWord) throws SQLException {
        rootWordLbl.setText(rootWord);
        fetchAndDisplayVersesForRoot(selectedRootId);
    }

    // Function to Display verses According to Searched Root
    private void fetchAndDisplayVersesForRoot(int selectedRootId) throws SQLException {
        List<VerseTO> verseList = facadeBO.getVersesByRoot(selectedRootId);

        if (verseList != null) {
            DefaultTableModel versesTableModel = (DefaultTableModel) table.getModel();
            versesTableModel.setRowCount(0);

            for (VerseTO verse : verseList) {
                versesTableModel.addRow(new Object[]{verse.getId(), verse.getMisra_1(), verse.getMisra_2()});
            }

            // Display the number of verses
            JTextField verseCountField = new JTextField("Number of Verses: " + verseList.size());
            verseCountField.setEditable(false);
            verseCountField.setFont(new Font("Arial", Font.PLAIN, 16));
            versesCountLbl.setText("Number of Verses: " + verseList.size());

            // Ensure the panel is revalidated and repainted to reflect the changes
            versesPanel.revalidate();
            versesPanel.repaint();
        } else {
            System.err.println("Verse list is null");
        }
    }
    private void switchToPoemsTab() {
		tabbedPane.setEnabledAt(1, true);
		tabbedPane.setSelectedIndex(1);
	}
    private void showPoemAction(JTable versesTable) {
        System.out.println("Show Poem button clicked.");

        // Get the selected verse_id
        int selectedRow = versesTable.getSelectedRow();
        if (selectedRow == -1) {
            System.out.println("No verse selected.");
            return;
        }

        int selectedVerseId = (int) versesTable.getValueAt(selectedRow, 0);
        System.out.println("Selected Verse ID: " + selectedVerseId);

            // Fetch poems related to the selected verse_id
            List<PoemTO> poems = facadeBO.getPoemsByVerseId(selectedVerseId);
            for (PoemTO poem : poems) {
                System.out.println(poem.getId() + " " + poem.getTitle());
            }

            displayPoems(poems);

    }

    private void displayPoems(List<PoemTO> poems) {
        // Clear existing data in the poem table
        poemTableModel.setRowCount(0);

        // Display the fetched poems in the poem table
        for (PoemTO poem : poems) {
            // Add a row for each poem
            poemTableModel.addRow(new Object[]{poem.getId(), poem.getTitle()});
        }
    }

    
}
