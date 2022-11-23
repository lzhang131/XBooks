package com.example.books.data;

import java.util.List;

/**
 * category
 */
public class CategoryModel {

    private String category;

    private List<BookModel> books;

    public CategoryModel(String category, List<BookModel> books) {
        this.category = category;
        this.books = books;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }
}
