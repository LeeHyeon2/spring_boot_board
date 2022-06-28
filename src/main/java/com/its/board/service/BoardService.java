package com.its.board.service;

import com.its.board.common.PagingConst;
import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.MemberEntity;
import com.its.board.repository.BoardRepository;
import com.its.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    public void save(BoardDTO boardDTO) throws IOException {
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "C:\\spring_img\\" + boardFileName;
        if(!boardFile.isEmpty()){
            boardFile.transferTo(new File(savePath));
        }
        boardDTO.setBoardFileName(boardFileName);

        // toEntity 메서드에 회원 엔티티를 같이 전달해야 함. (로그인 이메일이 작성자와 동일하다는 전제조건)
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail(boardDTO.getBoardWriter());
        if (optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            boardRepository.save(BoardEntity.toEntity(boardDTO,memberEntity));
        }
    }

    @Transactional
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
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardEntity.setId(boardDTO.getId());
        boardRepository.save(boardEntity);
    }


    @Transactional // Repository 에 작성한 Query 를 사용 가능하게 해줌
    public void hits(Long id) {
        boardRepository.hits(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // 요청 페이지값 가져옴.
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1; // 삼항연산자
        page = (page == 1)? 0: (page-1);
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page<BoardEntity> => Page<BoardDTO>
        // board : BoardEntity 객체
        // new BoardDTO() 생성자
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));
        return boardList;
    }

    public List<BoardDTO> search(String q) {
        List<BoardEntity> byBoardTitleContaining = boardRepository.findByBoardTitleContaining(q);
        List<BoardDTO> boardDTOS = new ArrayList<>();
        for (BoardEntity boardEntity: byBoardTitleContaining){
            boardDTOS.add(BoardDTO.toDTO(boardEntity));
        }
        return boardDTOS;
    }
}
