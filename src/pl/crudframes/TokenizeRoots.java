package pl.crudframes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import bll.IFacadeBO;

public class TokenizeRoots {
	private IFacadeBO facadeBO;
	private JFrame frame;
	private JTextField misra1TxtField;
	private JTextField misra2TxtField;
	private JPanel tokenFieldPanel;
	private String[] tokens;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	public TokenizeRoots(int verseId,String misra1,String misra2, IFacadeBO facadeBO) {
		this.facadeBO = facadeBO;
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(500, 20));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setText(misra1);

		misra1TxtField = new JTextField();
		misra1TxtField.setColumns(10);
		misra1TxtField.setText(misra1);

		misra2TxtField = new JTextField();
		misra2TxtField.setColumns(10);
		misra2TxtField.setText(misra2);

		misra1TxtField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

					facadeBO.updateVerse(verseId, misra1TxtField.getText(), misra2TxtField.getText());

			}

		});
		misra2TxtField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

					facadeBO.updateVerse(verseId, misra1TxtField.getText(), misra2TxtField.getText());

			}

		});

		JButton sliptButton = new JButton("Split");
		sliptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tokenFieldPanel.removeAll();
				tokenFieldPanel.revalidate();
				tokenFieldPanel.repaint();
				String misraOne = misra1TxtField.getText();
				String misraTwo = misra2TxtField.getText();
				int size = 0;
				String[] sizeArray = null;
				String[] numbersArray = null;
				Boolean misraCheck2 = true;
				Boolean misraCheck1 = true;

				if (!misraOne.isEmpty()) {
					numbersArray = misraOne.split("\\s+");
				} else {
					misraCheck1 = false;
				}
				if (!misraTwo.isEmpty()) {
					sizeArray = misraTwo.split("\\s+");
				} else {
					misraCheck2 = false;
				}

				// Get the count of elements in the array
				if (misraCheck1)
					size = numbersArray.length;
				if (misraCheck2)
					size += sizeArray.length;

				// Create an array of JTextField dynamically
				tokens = new String[size];
				JTextField dynamicField[] = new JTextField[size];
				for (int i = 0, k = 0, j = 0; i < dynamicField.length; i++) {
					dynamicField[i] = new JTextField(10);
					if (misraCheck1 && i < numbersArray.length) {
						if(numbersArray[j] != "") {
						dynamicField[i].setText(numbersArray[j]);
						tokens[i] = dynamicField[i].getText();
						}
						else {
							j++;
							continue;
						}
						j++;
					} else {
						if (misraCheck2) {
							if(sizeArray[k] != "") {
							dynamicField[i].setText(sizeArray[k]);
							tokens[i] = dynamicField[i].getText();
							}
							else {
								k++;
								continue;
							}
						}
						k++;
					}

					dynamicField[i].setEditable(false);
					//dynamicField[i].setEnabled(false);

					tokenFieldPanel.add(dynamicField[i]);

				}

				// Update the UI to reflect the changes
				frame.revalidate();
				frame.repaint();
			}
		});

		sliptButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		tokenFieldPanel = new JPanel();
		tokenFieldPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		JButton btnNewButton = new JButton("Save words");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				HashMap<String, String[]> rootsMap = facadeBO.getRoots(tokens);
				for (HashMap.Entry<String, String[]> set : rootsMap.entrySet()) {
					String roots[] = set.getValue();
					String token = set.getKey();
					String pos  = facadeBO.getPOS(token);
					int rootId[] = new int[roots.length];
					for (int i = 0; i < roots.length; i++) {
							rootId[i] = facadeBO.insert_Root(roots[i]);
					}
					int tokenId = -1;
					int posID = facadeBO.insertPOS(pos);
					if (pos.isEmpty()) {
						tokenId = facadeBO.insertToken("");
					} else {
						tokenId = facadeBO.insertToken(token);
					}
					facadeBO.insertPOSToken(posID, tokenId);
					for (int i = 0; i < rootId.length; i++) {
						facadeBO.insertRootToken(tokenId, rootId[i]);
					}

					facadeBO.assignTokenVerse(verseId, tokenId);

				}
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(sliptButton)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(misra1TxtField, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(misra2TxtField, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, 0, 0, Short.MAX_VALUE))
					.addContainerGap(325, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(26, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(tokenFieldPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(misra1TxtField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(misra2TxtField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(sliptButton)
					.addGap(18)
					.addComponent(tokenFieldPanel, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		tokenFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
