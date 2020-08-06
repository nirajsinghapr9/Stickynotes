package com.niraj.stickynotes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.niraj.stickynotes.model.Note;
import com.niraj.stickynotes.repo.NoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Page<Note> getAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }


    public Optional<Note> getNoteById(String id) {
        return noteRepository.findById(id);
    }

    public void createNote(Note note) {
        noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        if (noteRepository.findById(id).isPresent())
            noteRepository.deleteById(id);
    }

    public void updateNote(Note note, String id
    ) {
        Optional<Note> noteToUpdate = noteRepository.findById(id);
        if (noteToUpdate.isPresent()) {
            noteToUpdate.get().setTitle(note.getTitle());
            noteToUpdate.get().setNote(note.getNote());
            noteRepository.save(noteToUpdate.get());
        }
    }

}

