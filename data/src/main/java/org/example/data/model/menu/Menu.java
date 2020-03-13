package org.example.data.model.menu;

import org.example.data.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu extends BaseModel {
    @Column(name = "parent_id")
    private Long parenId;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;

    public Long getParenId() {
        return parenId;
    }

    public void setParenId(Long parenId) {
        this.parenId = parenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
