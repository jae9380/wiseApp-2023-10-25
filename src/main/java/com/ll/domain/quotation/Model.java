package com.ll.domain.quotation;

public class Model {

    public  String saying,author;

    public Model(String saying, String author) {
        this.saying = saying;
        this.author = author;
    }
    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
