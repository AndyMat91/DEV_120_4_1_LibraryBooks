package org.example.models;
import java.io.Serializable;
import java.util.Objects;

public class BooksLibraryCode implements Serializable {
    private final String bookCode;
    public BooksLibraryCode(String bookCode) {
        checkArg(bookCode, "book code");
        this.bookCode = bookCode;
    }

    private void checkArg(String value, String field) {
        if(value == null || value.isEmpty())
            throw new IllegalArgumentException(field + " is null or empty.");
        for(int i = 0; i < value.length(); i++) {
            if(!Character.isDigit(value.charAt(i)))
                throw new IllegalArgumentException(field + " must consist of digits only.");
        }
    }

    public String getBookCode() {
        return bookCode;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BooksLibraryCode))
            return false;
        BooksLibraryCode other = (BooksLibraryCode)o;
        return this.bookCode.equals(other.bookCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookCode);
    }

    @Override
    public String toString() {
        return bookCode;
    }
}
