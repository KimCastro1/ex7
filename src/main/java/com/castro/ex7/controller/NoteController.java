package com.castro.ex7.controller;

import com.castro.ex7.dto.NoteDTO;
import com.castro.ex7.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping(value="/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){
        log.info("read: "+num);
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }

    @PostMapping(value="")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){
        log.info(noteDTO);
        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }
    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){
        log.info("get Note List by writer email, writer: "+email);
        return new ResponseEntity<>(noteService.getAllWithWriter(email),HttpStatus.OK);
    }
    @DeleteMapping(value="/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){
        log.info("delte by note num, num: "+num);
        noteService.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }
    @PutMapping(value="/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO){
        log.info("modify note: "+noteDTO);
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }

}
