package org.example.ui;

import org.example.lists.BooksList;
import org.example.models.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.IOException;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final BooksListTableModel booksListTableModel = new BooksListTableModel();
    private final JTable booksTable = new JTable();
    private final BooksDialog booksDialog = new BooksDialog(this);

    public MainFrame() {
        super("AvalonTelecom Ltd. books list");

        initMenu();
        initLayout();
        BooksList.load();


        setBounds(300, 200, 600, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add book", 'A', KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK), e -> addBook());

        addMenuItemTo(operations, "Change book", 'C', KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK), e -> changeBook());

        addMenuItemTo(operations, "Delete book", 'D', KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK), e -> delBook());

        setJMenuBar(menuBar);
    }

    private void addMenuItemTo(JMenu parent, String text, char mnemonic, KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

    private void initLayout() {
        booksTable.setModel(booksListTableModel);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(booksTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(booksTable), BorderLayout.CENTER);


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        booksListTableModel.saveBooks();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(0);
                }
            }
        });
    }


    private void addBook() {
        booksDialog.prepareForAdd();
        while (booksDialog.showModal()) {
            try {
                BooksLibraryCode pn = new BooksLibraryCode(booksDialog.getBookCode());
                booksListTableModel.addBook(pn, booksDialog.getIsbn(), booksDialog.getTitleBook(), booksDialog.getAuthor(), Integer.parseInt(booksDialog.getYear()));
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Book registration error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeBook() {
        int seldRow = booksTable.getSelectedRow();
        if (seldRow == -1) return;
        BooksInfo ci = booksListTableModel.getBook(seldRow);
        booksDialog.prepareForChange(ci);
        if (booksDialog.showModal()) {
            ci.setIsbn(booksDialog.getIsbn());
            ci.setTitleBook(booksDialog.getTitle());
            ci.setAuthor(booksDialog.getAuthor());
            ci.setYear(Integer.parseInt(booksDialog.getYear()));
            booksListTableModel.clientChanged(seldRow);
        }
    }

    private void delBook() {
        int seldRow = booksTable.getSelectedRow();
        if (seldRow == -1) return;
        BooksInfo ci = booksListTableModel.getBook(seldRow);
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete book\n" + "with book code " + ci.getBookCode() + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            booksListTableModel.dropClient(seldRow);
        }
    }
}