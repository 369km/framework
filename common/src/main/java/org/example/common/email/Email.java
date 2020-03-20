package org.example.common.email;

public class Email {
    private String to;
    private String title;
    private String Content;

    public Email(String to, String title, String content) {
        this.to = to;
        this.title = title;
        Content = content;
    }

    public Email() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
