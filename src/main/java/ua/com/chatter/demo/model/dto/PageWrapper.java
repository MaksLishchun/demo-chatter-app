package ua.com.chatter.demo.model.dto;

import java.util.List;

public class PageWrapper<T> {

    private List<T> items;
    private int page;
    private int total;

    public PageWrapper() {
    }

    public PageWrapper(List<T> items, int page, int total) {
        this.items = items;
        this.page = page;
        this.total = total;
    }


    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
