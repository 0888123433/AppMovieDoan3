package com.appmovetv.model;

import java.util.List;

public class AllCategory {
    String categoryTitle;
    Integer categoryId;
    private List<Movie> categoryItemList = null;

    public AllCategory(Integer categoryId, String categoryTitle,  List<Movie> categoryItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.categoryItemList = categoryItemList;
    }

    public List<Movie> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<Movie> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
