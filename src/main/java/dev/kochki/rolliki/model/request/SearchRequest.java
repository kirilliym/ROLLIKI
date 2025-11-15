package dev.kochki.rolliki.model.request;

public class SearchRequest {
    private String query;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";

    // Конструкторы
    public SearchRequest() {}

    public SearchRequest(String query) {
        this.query = query;
    }

    // Геттеры и сеттеры
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDirection() { return sortDirection; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}