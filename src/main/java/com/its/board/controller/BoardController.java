package com.its.board.controller;

import com.its.board.common.PagingConst;
import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/save-form")
    public String saveForm(){
        return "/boardPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Model model){
        boardService.save(boardDTO);
        List<BoardDTO> boardDTOS = boardService.findAll();
        model.addAttribute("boardDTOList",boardDTOS);
        return "/boardPages/list";
    }

    @GetMapping("/")
    public String list(Model model){
        List<BoardDTO> boardDTOS = boardService.findAll();
        model.addAttribute("boardDTOList",boardDTOS);
        return "/boardPages/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        boardService.hits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO",boardDTO);
        return "/boardPages/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id , Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO",boardDTO);
        return "/boardPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO){
        boardService.update(boardDTO);
        return "redirect:/board/" + boardDTO.getId();
    }


    // /board?page=1
    // /board/3/1
    // rest api : 주소값만으로 자원을 식별 /board/10
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/paging";
    }
}
