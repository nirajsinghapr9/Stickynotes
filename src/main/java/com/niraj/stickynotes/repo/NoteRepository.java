package com.niraj.stickynotes.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.niraj.stickynotes.model.Note;

import java.util.Optional;

@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, String> {


    Optional<Note> findById(String id);

    void deleteById(String id);
}

