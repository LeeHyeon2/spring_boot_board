package com.its.board.service;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toEntity(boardDTO));
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> entities = boardRepository.findAll();
        List<BoardDTO> boardDTOS = new ArrayList<>();
        for (BoardEntity entity : entities){
            BoardDTO boardDTO = BoardDTO.toDTO(entity);
            boardDTOS.add(boardDTO);
        }

        return boardDTOS;
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        return BoardDTO.toDTO(optionalBoardEntity.get());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toEntity(boardDTO);
        boardEntity.setId(boardDTO.getId());
        boardRepository.save(boardEntity);
    }


    @Transactional // Repository 에 작성한 Query 를 사용 가능하게 해줌
    public void hits(Long id) {
        boardRepository.hits(id);
    }
}
