package com.joshuatheengineer.dodotodo.utilities;

import com.joshuatheengineer.dodotodo.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TEXT_1 = "Dodos are amazing birds";
    private static final String SAMPLE_TEXT_2 = "Dodos are extinct\n except in the movie Ice Age";
    private static final String SAMPLE_TEXT_3 =
            "The DoDo is an extinct flightless bird that was endemic to the island of Mauritius, east of Madagascar in the Indian Ocean.\n\n" +
                    "The dodo's closest genetic relative was the also-extinct Rodrigues solitaire, the two forming the subfamily Raphinae of the family of pigeons and doves.";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<NoteEntity> getNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        // since Primary IDs are auto-generated, it's removed
        notes.add(new NoteEntity( getDate(0), SAMPLE_TEXT_1));
        notes.add(new NoteEntity( getDate(-1), SAMPLE_TEXT_2));
        notes.add(new NoteEntity( getDate(-2), SAMPLE_TEXT_3));
        return notes;
    }
}
