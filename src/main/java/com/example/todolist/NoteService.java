package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        validateNote(note);
        if (note.getId() != null) {
            throw new NoteException("Note contains id. Did you mean 'update'?");
        }
        return noteRepository.save(note);
    }

    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        validateNote(note);
        noteRepository.save(note);
    }

    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteException("There is no note with id: " + id));
    }

    private void validateNote(Note note) {
        // Check if note is not null
        if (note == null) {
            throw new NoteException("Note is null!");
        }

        // Check if title is not null and title length is: 3 <= title <= 250
        Optional.ofNullable(note.getTitle())
                .map(String::strip)
                .filter(title -> title.length() >= 3 && title.length() <= 250)
                .orElseThrow(() -> new NoteException("Title is not valid!"));

        // Check if content is not null and not blank
        if (note.getContent() == null || note.getContent().isBlank()) {
            throw new NoteException("Content is not valid!");
        }
    }
}