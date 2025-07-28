package com.example.myapplication.model;

import java.util.ArrayList;

public class RootKol {
    public ArrayList<FollowerRespon> content;
    public PageableModel pageable;
    public int totalPages;
    public int totalElements;
    public boolean last;
    public boolean first;
    public Sort sort;


    public int numberOfElements;
    public int size;
    public int number;
    public boolean empty;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public ArrayList<FollowerRespon> getContent() {
        return content;
    }

    public void setContent(ArrayList<FollowerRespon> content) {
        this.content = content;
    }

    public PageableModel getPageable() {
        return pageable;
    }

    public void setPageable(PageableModel pageable) {
        this.pageable = pageable;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }


}
