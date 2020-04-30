package com.joshuatheengineer.dodotodo.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Added some more types for database
 */
@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;                // name of task

    @Nullable
    @ColumnInfo(name = "status")
    private Integer status;             // status. Complete or To Do.
                                        // SQLite doesn't support boolean types, so use int
                                        // https://www.sqlite.org/datatype3.html
    @Nullable
    @ColumnInfo(name = "numofunits")
    private Integer numofunits;             // number of units

    @Nullable
    @ColumnInfo(name = "goalofunits")
    private Integer goalofunits;            // goal number of units

    @Nullable
    @ColumnInfo(name = "typeofunits")
    private String typeofunits;         // type of units: hours, min, seconds, quantity,

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "text")
    private String text;

    @Ignore
    public NoteEntity() { }

    /**
     * had to modify constructors
     */
    @Ignore
    public NoteEntity(Date date, String text, String name, Integer status, Integer numofunits, Integer goalofunits, String typeofunits) {
        this.date = date;
        this.text = text;
        this.name = name;
        this.status = status;
        this.numofunits = numofunits;
        this.goalofunits = goalofunits;
        this.typeofunits = typeofunits;
    }

    public NoteEntity(int id, Date date, String text, String name, Integer status, Integer numofunits, Integer goalofunits, String typeofunits) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.name = name;
        this.status = status;
        this.numofunits = numofunits;
        this.goalofunits = goalofunits;
        this.typeofunits = typeofunits;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNumofunits() {
        return numofunits;
    }

    public void setNumofunits(Integer numofunits) {
        this.numofunits = numofunits;
    }

    public Integer getGoalofunits() {
        return goalofunits;
    }

    public void setGoalofunits(Integer goalofunits) {
        this.goalofunits = goalofunits;
    }

    public String getTypeofunits() {
        return typeofunits;
    }

    public void setTypeofunits(String typeofunits) {
        this.typeofunits = typeofunits;
    }

    @NotNull
    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", numofunits=" + numofunits +
                ", goalofunits=" + goalofunits +
                ", typeofunits=" + typeofunits +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
