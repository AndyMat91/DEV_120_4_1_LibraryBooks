package org.example.ui;

import org.example.lists.BooksList;
import org.example.models.BooksInfo;
import org.example.models.BooksLibraryCode;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class BooksListTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "Book code",
			"ISBN",
            "Title",
            "Author",
			"Year of publication"
        };

    private final Set<TableModelListener> modelListeners = new HashSet<>();
    
	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case 0: 
				return BooksLibraryCode.class;
			case 1: 
			case 2:
			case 3:
				return String.class;
			case 4:
				return Integer.class;
		}
		throw new IllegalArgumentException("unknown columnIndex");
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_HEADERS[columnIndex];
	}

	@Override
	public int getRowCount() {
		return BooksList.getInstance().getBooksCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BooksInfo ci = BooksList.getInstance().getBookInfo(rowIndex);
		switch(columnIndex) {
			case 0: return ci.getBookCode();
			case 1: return ci.getIsbn();
			case 2: return ci.getTitleBook();
			case 3: return ci.getAuthor();
			case 4: return ci.getYear();
		}
		throw new IllegalArgumentException("unknown columnIndex");
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /* Nothing to do, since isCellEditable is always false. */
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
	}
	
	public BooksInfo getBook(int rowNdx) {
		return BooksList.getInstance().getBookInfo(rowNdx);
	} 

	public void addBook(BooksLibraryCode number, String isbn, String title, String author, int year) {
		BooksList.getInstance().addBook(number, isbn, title, author, year);
        int rowNdx = BooksList.getInstance().getBooksCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
	}

	public void clientChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
	}

	public void dropClient(int index) {
		BooksList.getInstance().removeBook(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
	}

    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }

	public void saveBooks() throws IOException {
		BooksList.getInstance().save();
	}
}
