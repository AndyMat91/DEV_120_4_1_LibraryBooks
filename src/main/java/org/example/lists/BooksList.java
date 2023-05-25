package org.example.lists;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.models.*;

public class BooksList {

    private static final BooksList instance = new BooksList();
    private static List<BooksInfo> books;
    private static Set<BooksLibraryCode> codes;
    private BooksList() {
    }

    public void addBook(BooksLibraryCode code, String isbn, String title, String author, int year) {
        if (codes.contains(code))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        books.add(new BooksInfo(code, isbn, title, author, year));
        codes.add(code);
    }


    public void removeBook(int index) {
        BooksInfo booksInfo = books.get(index);
        codes.remove(booksInfo.getBookCode());
        books.remove(index);
    }

    public int getBooksCount() {
        return books.size();
    }

    public BooksInfo getBookInfo(int index) {
        return books.get(index);
    }


    public static BooksList getInstance() {
        return instance;
    }

    public void save() throws IOException {
        File b = new File("Books.dat");
        FileOutputStream fos = new FileOutputStream(b);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(books);
        oos.close();
    }


    public static void load() {
        File c = new File("Books.dat");
        try {
            if (c.exists()) {
                FileInputStream fis = new FileInputStream(c);
                ObjectInputStream ois = new ObjectInputStream(fis);
                books = (List<BooksInfo>) ois.readObject();
                codes = new HashSet<>();
                for (BooksInfo x : books) {
                    codes.add(x.getBookCode());
                }
                ois.close();
            } else {
                books = new ArrayList<>();
                codes = new HashSet<>();
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
