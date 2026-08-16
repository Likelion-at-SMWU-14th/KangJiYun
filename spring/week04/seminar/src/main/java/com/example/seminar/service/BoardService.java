package com.example.seminar.service;

import com.example.seminar.dto.BoardDTO;
import com.example.seminar.entity.Board;
import com.example.seminar.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void createBoard(BoardDTO boardDTO) {
        Board board = new Board(
                boardDTO.getName()
        );
        boardRepository.save(board);
    }

    //전체 조회
    public List<BoardDTO> getBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : boards) {
            boardDTOList.add(
                    new BoardDTO(
                            board.getId(),
                            board.getName()
                    )
            );
        }
        return boardDTOList;
    }

    public BoardDTO getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다."));
        return new BoardDTO(
                board.getId(),
                board.getName()
        );
    }

    @Transactional
    public void updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다."));
        board.setName(boardDTO.getName());
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다."));
        boardRepository.delete(board);
    }
}
