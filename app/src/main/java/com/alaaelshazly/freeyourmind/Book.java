
package com.alaaelshazly.freeyourmind;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Book implements Serializable {
    private String poster;
    private String photo;
    private String authur;
    private String genre;
    private String summary;
    private Date updated;
    private String name;
    private String ownerId;
    private String objectId;
    private String pdf;
    private Date created;

    public static Book findById(String id) {
        return Backendless.Data.of(Book.class).findById(id);
    }

    public static void findByIdAsync(String id, AsyncCallback<Book> callback) {
        Backendless.Data.of(Book.class).findById(id, callback);
    }

    public static Book findFirst() {
        return Backendless.Data.of(Book.class).findFirst();
    }

    public static void findFirstAsync(AsyncCallback<Book> callback) {
        Backendless.Data.of(Book.class).findFirst(callback);
    }

    public static Book findLast() {
        return Backendless.Data.of(Book.class).findLast();
    }

    public static void findLastAsync(AsyncCallback<Book> callback) {
        Backendless.Data.of(Book.class).findLast(callback);
    }

    public static List<Book> find(DataQueryBuilder queryBuilder) {
        return Backendless.Data.of(Book.class).find(queryBuilder);
    }

    public static void findAsync(DataQueryBuilder queryBuilder, AsyncCallback<List<Book>> callback) {
        Backendless.Data.of(Book.class).find(queryBuilder, callback);
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthur() {
        return authur;
    }

    public void setAuthur(String authur) {
        this.authur = authur;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public Date getCreated() {
        return created;
    }

    public Book save() {
        return Backendless.Data.of(Book.class).save(this);
    }

    public void saveAsync(AsyncCallback<Book> callback) {
        Backendless.Data.of(Book.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(Book.class).remove(this);
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(Book.class).remove(this, callback);
    }
}