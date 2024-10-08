package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model,
        @RequestParam(name = "file", required = false) MultipartFile file) throws Exception {
        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view")  //localhost:8090/board/view?id=1
    public String boardView(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam(name = "id") Integer id) {
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    //@PathVariable: ()안의 내용이 인식이 되어 파라미터로 사용
    //기존의 view?id=0 대신 view/0와 같은 식으로 표시가 된다.
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    /*
     *   실무에서는 이렇게 덮어 씌우는 방식의 수정은 금지(JPA 사용 시). JPA의 Dirty Checking을 사용하자
     *   JPA의 Dirty Checking 기능은 인해 트랜젝션이 종료되는 시점에 자동으로 변경 사항을 DB에 반영한다.
     */
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model,
        @RequestParam(name = "file", required = false) MultipartFile file) throws Exception {
        //기존 글이 담기는 임시 객체
        Board boardTemp = boardService.boardView(id);

        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
