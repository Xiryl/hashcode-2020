package it.chiarani;

public class Book {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private int id;

    public Book(int id, Integer score) {
        this.id = id;
        this.score = score;
    }

    private Integer score;
}
