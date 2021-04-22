package cz.uhk.fim.student.schmid.java.model;

import java.util.List;
import cz.uhk.fim.student.schmid.java.util.BookItemUtil;

public abstract class BookItemBase implements BookItemInterface{
    final int id;
    final String title;
    final List<String> authors;
    final String description;
    final String imgUrl;

    public BookItemBase(int id, String title, List<String> authors, String description, String imgUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String authorsToString() {
        return BookItemUtil.authorsToString(authors, true);
    }
}
