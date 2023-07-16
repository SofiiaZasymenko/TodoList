package com.example.todolist;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {

    private static final Map<Long, Note> NOTES = new HashMap<>();
    private static final AtomicLong ID_COUNTER = new AtomicLong(0);

    public static void main(String[] args) {
        NoteService noteService = new NoteService();
        Note note1 = new Note();
        Note note2 = new Note();
        Note note3 = new Note();

        System.out.println("---Insert new note---");
        note1.setTitle("Title1");
        note1.setContent("Content1");
        noteService.add(note1);

        note2.setTitle("Title2");
        note2.setContent("Content2");
        noteService.add(note2);

        note3.setTitle("Title3");
        note3.setContent("Content3");
        noteService.add(note3);

        try {

            noteService.add(new Note(234L, "Not saved", "Not saved content"));
        } catch (Exception e) {
            assert "Note contains id. Did you mean 'update'?".equals(e.getMessage());
        }
        System.out.println(noteService.listAll());

        System.out.println("---Select note by id---");
        System.out.println(noteService.getById(1L));

        System.out.println("---Update note---");
        note1.setTitle("Title11");
        note1.setContent("Content11");
        noteService.update(note1);
        System.out.println(noteService.getById(1L));

        System.out.println("---Delete note by id---");
        noteService.deleteById(1L);
        System.out.println(noteService.listAll());

        System.out.println("---Select all notes---");
        System.out.println(noteService.listAll());
    }

    public List<Note> listAll() {
        return new ArrayList<>(NOTES.values());
    }

    public Note add(Note note) {
        if (note.getId() != null) {
            throw new RuntimeException("Note contains id. Did you mean 'update'?");
        }
        long id = ID_COUNTER.incrementAndGet();
        note.setId(id);
        NOTES.put(id, note);
        return note;
    }

    public void deleteById(long id) {
        if (NOTES.containsKey(id)) {
            NOTES.remove(id);
        } else {
            throw new RuntimeException("There is no note with id: " + id);
        }
    }

    public void update(Note note) {
        if (note.getId() != null && NOTES.containsKey(note.getId())) {
            NOTES.replace(note.getId(), note);
        } else {
            throw new RuntimeException("There is no note to update!");
        }
    }

    public Note getById(long id) {
        if (NOTES.containsKey(id)) {
            return NOTES.get(id);
        }
        throw new RuntimeException("There is no note with id: " + id);
    }
}