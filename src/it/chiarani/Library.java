package it.chiarani;

import java.util.List;

public class Library {
    public Library(int id, int qtabooks, int signupdays, int booksperday, int scoreofbookforlibrary,  List<Book> bookList, Integer peso) {
        this.id = id;
        this.qtabooks = qtabooks;
        this.signupdays = signupdays;
        this.booksperday = booksperday;
        this.bookList = bookList;
        this.scoreofbookforlibrary = scoreofbookforlibrary;
        this.peso = peso;
    }

    private int id;
    private int qtabooks;
    private Integer signupdays;
    private int booksperday;

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    private Integer peso;

    public int getScoreofbookforlibrary() {
        return scoreofbookforlibrary;
    }

    public void setScoreofbookforlibrary(int scoreofbookforlibrary) {
        this.scoreofbookforlibrary = scoreofbookforlibrary;
    }

    private int scoreofbookforlibrary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtabooks() {
        return qtabooks;
    }

    public void setQtabooks(int qtabooks) {
        this.qtabooks = qtabooks;
    }

    public Integer getSignupdays() {
        return signupdays;
    }

    public void setSignupdays(Integer signupdays) {
        this.signupdays = signupdays;
    }

    public int getBooksperday() {
        return booksperday;
    }

    public void setBooksperday(int booksperday) {
        this.booksperday = booksperday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    private List<Book> bookList;
}
