package com.niraj.stickynotes.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.niraj.stickynotes.model.Note;
import com.niraj.stickynotes.service.*;

@Controller
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes/page/{page}")
    public String getAllNotes(Model model, @PathVariable int page ) {
        PageRequest pageable = PageRequest.of(page - 1, 5, Sort.by("modifiedAt").descending());
        Page<Note> notes = null;
            notes = noteService.getAllNotes(pageable);
        int totalPages = notes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("notes", notes.getContent());
        model.addAttribute("note", new Note());
        return "notes";
    }


    @GetMapping("/notes/create")
    public String showCreateNoteForm(Model model) {
        Note newNote = new Note();
        model.addAttribute("note", newNote);
        return "createNote";
    }

    @PostMapping("/notes/create")
    public String createNote(Note note, RedirectAttributes redirectAttributes) {
        noteService.createNote(note);
        redirectAttributes.addFlashAttribute("message", "The note with title '" + note.getTitle() + "' has been created");
        return "redirect:/notes/page/1";
    }

    @GetMapping("/notes/{id}/update")
    public String showUpdateNoteForm(Model model, @PathVariable String id) {
        Optional<Note> optionalNote = noteService.getNoteById(id);
        optionalNote.ifPresent(note -> model.addAttribute("note", note));
        return "updateNote";
    }

    @PutMapping("/notes/{id}/update")
    public String updateNote(Note note, @PathVariable String id,RedirectAttributes redirectAttributes) {
        noteService.updateNote(note, id);
        redirectAttributes.addFlashAttribute("message", "The note with title '" + note.getTitle() + "' has been updated");
        return "redirect:/notes/page/1";
    }

    @DeleteMapping("notes/{id}/delete")
    public String deleteNote(@PathVariable String id, RedirectAttributes redirectAttributes) {
        noteService.deleteNoteById(id);
        redirectAttributes.addFlashAttribute("message", "The note has been deleted");
        return "redirect:/notes/page/1";
    }


}
