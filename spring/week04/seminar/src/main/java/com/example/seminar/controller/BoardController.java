package com.example.seminar.controller;

import com.example.seminar.dto.BoardDTO;
import com.example.seminar.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardDTO boardDTO){
        boardService.createBoard(boardDTO);
    }

    @GetMapping
    public List<BoardDTO> getBoards(){
        return boardService.getBoards();
    }

    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
        boardService.updateBoard(id, boardDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
    }
}
