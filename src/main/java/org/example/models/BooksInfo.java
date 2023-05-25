package org.example.models;

import java.io.Serializable;

public class BooksInfo implements Serializable {
    private final BooksLibraryCode booksLibraryCode;
    private String isbn;
    private String title;
    private String author;
    private int year;

    public BooksInfo(BooksLibraryCode booksLibraryCode, String isbn, String title, String author, int year) {
        if (booksLibraryCode == null)
            throw new IllegalArgumentException("library code can't be null.");
        this.booksLibraryCode = booksLibraryCode;
        setIsbn(isbn);
        setTitleBook(title);
        setAuthor(author);
        setYear(year);
    }

    public String getTitleBook() {
        return title;
    }

    public void setTitleBook(String title) {
        if (title == null)
            throw new IllegalArgumentException("title can't be null.");
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.replace(' ', ',');
    }

    public BooksLibraryCode getBookCode() {
        return booksLibraryCode;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null)
            throw new IllegalArgumentException("ISBN can't be null.");
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}