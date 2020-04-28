package com.joshuatheengineer.dodotodo.database;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class NoteEntity {
    private int id;
    private Date date;
    private String text;

    /**
     * For different scenarios
     *
     * For example, adding with all data
     * Or adding when id is auto-generated
     */
    public NoteEntity() { }

    public NoteEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public NoteEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
