package com.codingrecipe.board2024_boot_jpa.controller;

import com.codingrecipe.board2024_boot_jpa.dto.BoardDTO;
import com.codingrecipe.board2024_boot_jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    // 생성자 주입 방식으로 의존성 주입
    private final BoardService boardService;

    // Mapping 중복 시 모호한 매핑 에러 발생.
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        // form 내의 name에 해당하는 필드에 알아서 값을 담아 객체를 생성해준다.
        // @RequestParam("boardWriter") String boardWriter, @RequestParam("") ...

        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);

        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        // DB -> 전체 게시물 데이터를 가져옴 -> list.html에 넘겨 보여줌

        List<BoardDTO> boardDTOList = boardService.findAll();
        // View에게 전달하기 위해 model에 글 전체 목록을 읽어와 담음.
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }
}
