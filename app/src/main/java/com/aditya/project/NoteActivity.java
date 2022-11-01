package com.aditya.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.aditya.project.database.DataManager;
import com.aditya.project.databinding.ActivityNoteBinding;
import com.aditya.project.model.CourseInfo;
import com.aditya.project.model.NoteInfo;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_INFO = "com.aditya.project.NOTE_INFO";

    private ActivityNoteBinding binding;
    private NoteInfo mNote;
    private boolean mIsNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Spinner spinnerCourses = findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();

        ArrayAdapter<CourseInfo> adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCourses.setAdapter(adapterCourses);

        readDisplayStateValue();

        EditText textNoteTitle = findViewById(R.id.text_note_title);
        EditText textNoteText = findViewById(R.id.text_note_text);

        if (!mIsNewNote) {
            displayNote(spinnerCourses, textNoteTitle, textNoteText);
        }
    }

    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }

    private void readDisplayStateValue() {
        Intent intent = getIntent();
        mNote = intent.getParcelableExtra(NOTE_INFO);
        mIsNewNote = mNote == null;
    }
}