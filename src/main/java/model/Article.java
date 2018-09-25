package model;

public class Article {
    private Long id;
    private String body;
    private String tags;

    public Article() {

    }

    public Article(Long id, String body, String tags) {
        this.id = id;
        this.body = body;
        this.tags = tags;
    }

    public Article(String body, String tags) {
        this.body = body;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
