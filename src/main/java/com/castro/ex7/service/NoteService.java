package com.castro.ex7.service;

import com.castro.ex7.dto.NoteDTO;
import com.castro.ex7.entity.Note;
import com.castro.ex7.entity.TestMember;

import java.util.List;

public interface NoteService {
    Long register(NoteDTO noteDTO);
    NoteDTO get(Long num);
    void modify(NoteDTO noteDTO);
    void remove(Long num);
    List<NoteDTO> getAllWithWriter(String writerEmail);

    default NoteDTO entityToDTO(Note entity){
        NoteDTO dto = NoteDTO.builder()
                .num(entity.getNum())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerEmail(entity.getWriter().getEmail())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    default Note dtoToEntity(NoteDTO dto){
        Note entity = Note.builder()
                .num(dto.getNum())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(TestMember.builder().email(dto.getWriterEmail()).build())
                .build();

        return entity;
    }

}
