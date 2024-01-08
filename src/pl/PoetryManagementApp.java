package pl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import bll.IFacadeBO;
import pl.crudframes.AddBook;
import pl.crudframes.AddPoem;
import pl.crudframes.AddRoot;
import pl.crudframes.AddVerse;
import pl.crudframes.AssignRoot;
import pl.crudframes.EditBook;
import pl.crudframes.EditPoem;
import pl.crudframes.EditRoot;
import pl.crudframes.EditVerse;
import pl.crudframes.RootDetails;
import pl.crudframes.TokenizeRoots;
import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

public class PoetryManagementApp {
	private static int BOOK_ID;
	private static int POEM_ID;
	private static int VERSE_ID;
	private static IFacadeBO facadeBO;
	private static DefaultTableModel bookTableModel;
	private static DefaultTableModel poemTableModel;
	private static DefaultTableModel verseTableModel;
	private static DefaultTableModel rootsTableModel;
	private static JTabbedPane tabbedPane;
	private static JPanel toolBar;

	public PoetryManagementApp(IFacadeBO facadeBO) throws SQLException {
		this.facadeBO = facadeBO;

		// Create the table model for Books
		bookTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		// BOOK ROW HEADER
		bookTableModel.addColumn("Book ID");
		bookTableModel.addColumn("Book Title");
		bookTableModel.addColumn("Author");
		bookTableModel.addColumn("Author Death Date");

		poemTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		// POEM ROW HEADER
		poemTableModel.addColumn("Poem ID");
		poemTableModel.addColumn("Poem Title");

		verseTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		// VERSE ROW HEADRE
		verseTableModel.addColumn("Verse ID");
		verseTableModel.addColumn("Misra 1");
		verseTableModel.addColumn("Misra 2");

		rootsTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells non-editable
			}
		};
		// ROOT ROW HEADER
		rootsTableModel.addColumn("Root ID");
		rootsTableModel.addColumn("Root Title");

		JFrame frame = new JFrame("Poetry Management App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		tabbedPane = new JTabbedPane();

		// Create the first panel with tabs
		JPanel tabPanel = new JPanel();
		tabPanel.setLayout(new BorderLayout());

		JPanel bookPanel = createBookPanel(tabbedPane);
		JPanel poemPanel = createPoemPanel(tabbedPane);
		JPanel versePanel = createVersePanel(tabbedPane);
		JPanel rootsPanel = createRootsPanel(tabbedPane);

		// Disable Poem and Verse tabs
		tabbedPane.addTab("Books", bookPanel);
		tabbedPane.addTab("Poems", poemPanel);
		tabbedPane.addTab("Verses", versePanel);
		tabbedPane.addTab("Roots", rootsPanel);

		// Disable Poem and Verse tabs
		tabbedPane.setEnabledAt(1, false); // Index 1 is the "Poems" tab
		tabbedPane.setEnabledAt(2, false); // Index 2 is the "Verses" tab
		tabPanel.add(tabbedPane, BorderLayout.CENTER);
		frame.getContentPane().add(tabPanel);
		// Center the frame on the screen
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JPanel createBookPanel(JTabbedPane tabbedPane) {
		JPanel panel = new JPanel(new BorderLayout());

		// FETCH BOOK DATA FROM DATABASE AND INSERT INTO THE MODEL
		List<BookTO> bookList = new ArrayList<>();
		bookList = facadeBO.displayAllBooks();

		// bookTableModel.addRow(new Object[]{});
		if(bookList != null) {
		for (BookTO book : bookList) {
			bookTableModel.addRow(new Object[] { book.getBookId(), book.getTitle(), book.getAuthorName(),
					book.getAuthorDeathDate() });
		}
		}

		JTable bookTable = new JTable(bookTableModel);

		// Get the column model
		TableColumnModel columnModel = bookTable.getColumnModel();
		// Hide the id column (index 0)
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setPreferredWidth(0);

		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		addContextButtons(panel, "Book", bookTable);

		panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

		// Add a double-click listener to switch to the "Poem" tab
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int selectedRow = bookTable.getSelectedRow();
					BOOK_ID = selectedRow;// GLOBAL
					if (selectedRow >= 0) {
						// Double-clicked a book, switch to the "Poem" tab
						// Enable Poem and Verse tabs
						tabbedPane.setEnabledAt(1, true); // Index 1 is the "Poems" tab

						// Getting Id of the Selected Book
						int key = (int) bookTableModel.getValueAt(selectedRow, 0);
	

							// FETCH POEM DATA FROM DATABASE AND INSERT INTO THE MODEL

							List<PoemTO> poemList = facadeBO.displayPoems(key);
							poemTableModel.setRowCount(0);// Clear all the Previous model Entries
							for (PoemTO poem : poemList) {
								poemTableModel.addRow(new Object[] { poem.getId(), poem.getTitle() });
							}

						// poemTableModel.addRow(null);

						tabbedPane.setSelectedIndex(1); // Index 1 is the "Poems" tab
					}
				}
			}
		});

		return panel;
	}

	private static JPanel createPoemPanel(JTabbedPane tabbedPane) {
		JPanel panel = new JPanel(new BorderLayout());

		JTable poemTable = new JTable(poemTableModel);

		// Get the column model
		TableColumnModel columnModel = poemTable.getColumnModel();
		// Hide the id column (index 0)
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setPreferredWidth(0);

		poemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addContextButtons(panel, "Poem", poemTable);

		panel.add(new JScrollPane(poemTable), BorderLayout.CENTER);

		poemTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {        
				if (e.getClickCount() == 2) {
					int selectedRow = poemTable.getSelectedRow();
					POEM_ID = selectedRow;// GLOBAL
					System.out.println("POEM_ID: " + POEM_ID);
					if (selectedRow >= 0) {
						// Double-clicked a poem, switch to the "Verses" tab
						tabbedPane.setEnabledAt(2, true); // Index 2 is the "Verses" tab
						int poem_id = (int) poemTableModel.getValueAt(selectedRow, 0);
						System.out.println("POEM_ID: " + poem_id);


							List<VerseTO> verseList = facadeBO.displayVerses(poem_id);
							verseTableModel.setRowCount(0);// Clear all the Previous model Entries
							for (VerseTO verse : verseList) {
								verseTableModel.addRow(new Object[] { verse.getId(), verse.getMisra_1(), verse.getMisra_2() });
							}

						tabbedPane.setSelectedIndex(2); // Index 2 is the "Verses" tab
					}
				}
			}
		});

		return panel;
	}

	// Edit in the verse
	private static JPanel createVersePanel(JTabbedPane tabbedPane) {
		JPanel panel = new JPanel(new BorderLayout());

		JTable verseTable = new JTable(verseTableModel);

		// Get the column model
		TableColumnModel columnModel = verseTable.getColumnModel();
		// Hide the id column (index 0)
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setPreferredWidth(0);

		verseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addContextButtons(panel, "Verse", verseTable);
		addRootActionButtons("Root",verseTable);
		
		panel.add(new JScrollPane(verseTable), BorderLayout.CENTER);

		verseTable.addMouseListener(new MouseAdapter() {   //Edit
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selectedRow = verseTable.getSelectedRow();
					VERSE_ID = selectedRow; // Edit
					System.out.println("VERSE_ID: " + VERSE_ID);
					// Assuming selectedRow here refers to the verse row

					if (selectedRow >= 0) {
						// Double-clicked a verse, switch to the "Roots" tab
						tabbedPane.setEnabledAt(3, true); // Index 3 is the "Roots" tab

						int verse_id = (int) verseTableModel.getValueAt(selectedRow, 0);
						System.out.println("VERSE_ID: " + verse_id);
	
							List<RootTO> rootList = facadeBO.Read_Root(verse_id);
							rootsTableModel.setRowCount(0);// Clear all the Previous model Entries
							for (RootTO root : rootList) {
								rootsTableModel.addRow(new Object[] { root.getRoot_id(), root.getRoot_word() });
							}

						// Perform any necessary actions for the transition to the root panel
						// Load data or take actions related to the root panel

						tabbedPane.setSelectedIndex(3); // Index 3 is the "Roots" tab
					}
				}
			}
		});

		return panel;
	}

	private static JPanel createRootsPanel(JTabbedPane tabbedPane) {
		JPanel panel = new JPanel(new BorderLayout());


			List<RootTO> rootlist = facadeBO.Read_Root(VERSE_ID);
			for (RootTO root : rootlist) {
				rootsTableModel.addRow(new Object[] { root.getRoot_id(), root.getRoot_word() });
			}

		JTable rootsTable = new JTable(rootsTableModel);

		// Get the column model
		TableColumnModel columnModel = rootsTable.getColumnModel();
		// Hide the id column (index 0)
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setPreferredWidth(0);

		rootsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addContextButtons(panel, "Root", rootsTable);

		panel.add(new JScrollPane(rootsTable), BorderLayout.CENTER);

		// ITr2
		rootsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selectedRow = rootsTable.getSelectedRow();
					if (selectedRow >= 0) {
						int selectedRootId = (int) rootsTableModel.getValueAt(selectedRow, 0);
						String rootWord=(String) rootsTableModel.getValueAt(selectedRow,1);
						//RootTO selectedRoot = facadeBO.getRootById(selectedRootId);

						// Assuming you have a DefaultTableModel for poems, create an instance of RootDetailsScreen as follows:
						RootDetails rootDetailsScreen = new RootDetails(facadeBO,tabbedPane,poemTableModel);

						try {
							rootDetailsScreen.openRootDetailsScreen(selectedRootId,rootWord);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
	
		return panel;
	}
	private static void addContextButtons(JPanel panel, String itemType, JComponent component) {

        toolBar = new JPanel();
        JButton addButton = new JButton("Add " + itemType);
        JButton editButton = new JButton("Edit " + itemType);
        JButton deleteButton = new JButton("Delete " + itemType);
		JButton uploadButton = new JButton("");
		uploadButton.setHorizontalAlignment(SwingConstants.LEFT);
		uploadButton.setIcon(new ImageIcon("12_4.png"));

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itemType.equals("Book")) {

					AddBook addBook = new AddBook(facadeBO, bookTableModel);

				} else if (itemType.equals("Poem")) {

					int key = (int) bookTableModel.getValueAt(BOOK_ID, 0);
					AddPoem addPoem = new AddPoem(facadeBO, key, poemTableModel, verseTableModel);

				} else if (itemType.equals("Verse")) {

					int key = (int) poemTableModel.getValueAt(POEM_ID, 0);
					AddVerse addVerse = new AddVerse(facadeBO, key, verseTableModel);

				} else if (itemType.equals("Root")) {
					int key = (int) verseTableModel.getValueAt(VERSE_ID, 0);
					AddRoot addRoot = new AddRoot(facadeBO,key,rootsTableModel); // Edit

				}
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = component instanceof JTable ? ((JTable) component).getSelectedRow() : -1;
				if (selectedRow != -1) {
					if (itemType.equals("Book")) {

						EditBook editBook = new EditBook(facadeBO, bookTableModel, selectedRow);

					} else if (itemType.equals("Poem")) {

						EditPoem editPoemFrame = new EditPoem(facadeBO, selectedRow, poemTableModel);

					} else if (itemType.equals("Verse")) {

						EditVerse editVerse = new EditVerse(facadeBO, selectedRow, verseTableModel);

					} else if (itemType.equals("Root")) {

						EditRoot editRoot = new EditRoot(facadeBO, rootsTableModel, selectedRow);
					}
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = component instanceof JTable ? ((JTable) component).getSelectedRow() : -1;
				if (selectedRow != -1) {
					if (itemType.equals("Book")) {
						// DELETE ROW FROM DATABASE
						int key = (int) bookTableModel.getValueAt(selectedRow, 0);
						facadeBO.deleteBook(key);
						bookTableModel.removeRow(selectedRow);
						tabbedPane.setEnabledAt(1, false);
						tabbedPane.setEnabledAt(2, false);
					} else if (itemType.equals("Poem")) {
						// DELETE ROW FROM DATABASE
						int key = (int) poemTableModel.getValueAt(selectedRow, 0);

							facadeBO.deletePoem(key);
							poemTableModel.removeRow(selectedRow);
							tabbedPane.setEnabledAt(2, false);
						
					} else if (itemType.equals("Verse")) {
						// DELETE ROW FROM DATABASE
						int key = (int) verseTableModel.getValueAt(selectedRow, 0);
						
							facadeBO.deleteVerse(key);
							verseTableModel.removeRow(selectedRow);
						

					} else if (itemType.equals("Root")) {
						// DELETE ROW FROM DATABASE
						
							int key = (int) rootsTableModel.getValueAt(selectedRow, 0);
							facadeBO.delete_Root(key);
							rootsTableModel.removeRow(selectedRow);

					}
				}
			}
		});

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// Set the default file filter to display only .txt files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt)", "txt");
				fileChooser.setFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(toolBar);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					facadeBO.uploadFile(selectedFile.getAbsolutePath(),bookTableModel,poemTableModel,verseTableModel);
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					panel.revalidate();
					panel.repaint();
				}
			}
		});

		toolBar.add(uploadButton);
		toolBar.add(addButton);
		toolBar.add(editButton);
		toolBar.add(deleteButton);
		panel.add(toolBar, BorderLayout.PAGE_END);
		
		
	}
	private static void addRootActionButtons(String itemType, JComponent component) {
		JButton assignRootsBtn = new JButton("Assign "+itemType);
		toolBar.add(assignRootsBtn);
		assignRootsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = component instanceof JTable ? ((JTable) component).getSelectedRow() : -1;
                if (selectedRow != -1) { 
                	int key = (int) verseTableModel.getValueAt(selectedRow, 0);
                	try {
                		String misra_1 = (String) verseTableModel.getValueAt(selectedRow, 1);
	                	String misra_2 = (String) verseTableModel.getValueAt(selectedRow, 2);
						AssignRoot assignRoot = new AssignRoot(facadeBO,key,misra_1,misra_2);
						assignRoot.setVisible(true);
						assignRoot.setLocationRelativeTo(null);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		JButton tokenizeRoot = new JButton("Tokenize "+itemType);
		toolBar.add(tokenizeRoot);
		tokenizeRoot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = component instanceof JTable ? ((JTable) component).getSelectedRow() : -1;
                if (selectedRow != -1) { 
                	int key = (int) verseTableModel.getValueAt(selectedRow, 0);
                	String misra1 = (String) verseTableModel.getValueAt(selectedRow, 1);
                	String misra2 = (String) verseTableModel.getValueAt(selectedRow, 2);
					TokenizeRoots tokenRoots = new TokenizeRoots(key,misra1,misra2,facadeBO);
					
				}
			}
		});
	}

}
