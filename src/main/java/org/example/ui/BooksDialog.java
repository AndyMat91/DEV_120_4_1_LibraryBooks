package org.example.ui;

import org.example.models.BooksInfo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


import javax.swing.*;

public class BooksDialog extends JDialog {
	private final JTextField bookCodeField;
	private final JTextField isbnField;
	private final JTextField titleField;
	private final JTextField authorField;
	private final JTextField yearField;

	private boolean okPressed;

	public BooksDialog(JFrame owner) {
		super(owner, true);

		bookCodeField = new JTextField(20);
		isbnField = new JTextField(20);
		titleField = new JTextField(30);
		authorField = new JTextField(30);
		yearField  = new JTextField(5);

		initLayout();

		setResizable(false);
	}
	private void initLayout() {
		initControls();
		initOkCancelButtons();
	}

	private void initControls() {
		JPanel controlsPane = new JPanel(null);
		controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbl = new JLabel("Book code");
		lbl.setLabelFor(bookCodeField);
		p.add(lbl);
		p.add(bookCodeField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("ISBN");
		lbl.setLabelFor(isbnField);
		p.add(lbl);
		p.add(isbnField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("Book title");
		lbl.setLabelFor(titleField);
		p.add(lbl);
		p.add(titleField);
		controlsPane.add(p);
		
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("Author");
		lbl.setLabelFor(authorField);
		p.add(lbl);
		p.add(authorField);
		controlsPane.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbl = new JLabel("Year of publication");
		lbl.setLabelFor(yearField);
		p.add(lbl);
		p.add(yearField);
		controlsPane.add(p);
		
		add(controlsPane, BorderLayout.CENTER);
	}

	private void initOkCancelButtons() {
		JPanel btnsPane = new JPanel();
	
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(e -> {
			okPressed = true;
			setVisible(false);
		});
		okBtn.setDefaultCapable(true);
		btnsPane.add(okBtn);
		
		Action cancelDialogAction = new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		}; 
		
		JButton cancelBtn = new JButton(cancelDialogAction);
		btnsPane.add(cancelBtn);
		
		add(btnsPane, BorderLayout.SOUTH);
		
		final String esc = "escape";
		getRootPane()
			.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
			.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
		getRootPane()
			.getActionMap()
			.put(esc, cancelDialogAction);
	}

	public boolean showModal() {
		pack();
		setLocationRelativeTo(getOwner());
		if(bookCodeField.isEnabled())
			bookCodeField.requestFocusInWindow();
		else
			isbnField.requestFocusInWindow();
		okPressed = false;
		setVisible(true);
		return okPressed;
	}

	public void prepareForAdd() {
		setTitle("New book registration");
		bookCodeField.setText("");
		isbnField.setText("");
		titleField.setText("");
		authorField.setText("");
		yearField.setText("");
		

		bookCodeField.setEnabled(true);

	}

	public void prepareForChange(BooksInfo ci) {
		setTitle("Book properties change");
		

		bookCodeField.setText(ci.getBookCode().getBookCode());
		isbnField.setText(ci.getIsbn());
		titleField.setText(ci.getTitleBook());
		authorField.setText(ci.getAuthor());
		yearField.setText(String.valueOf(ci.getYear()));


		bookCodeField.setEnabled(false);
	}



	public String getBookCode() {
		return bookCodeField.getText();
	}
	public String getIsbn() {
		return isbnField.getText();
	}
	public String getTitleBook() {
		return titleField.getText();
	}
	public String getAuthor() {
		return authorField.getText();
	}
	public String getYear() {
		return yearField.getText();
	}

}
