package com.castro.ex7.service;

import com.castro.ex7.dto.NoteDTO;
import com.castro.ex7.entity.Note;
import com.castro.ex7.repositroy.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository repository;

    @Override
    public Long register(NoteDTO noteDTO) {
        Note note = dtoToEntity(noteDTO);
        log.info("insert note: "+note);
        repository.save(note);
        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = repository.getWithWriter(num);
        if(result.isPresent()){
            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();
        Optional<Note> result = repository.findById(num);
        if(result.isPresent()){
            Note note = result.get();
            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            repository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        repository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> noteDTOList = repository.getList(writerEmail);
        return noteDTOList.stream().map(note->entityToDTO(note)).collect(Collectors.toList());
    }
}
