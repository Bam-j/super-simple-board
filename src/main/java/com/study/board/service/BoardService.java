package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성, 파일 업로드 처리
    public void write(Board board, MultipartFile file) throws Exception {
        //프로젝트의 경로를 담음
        String projectPath =
            System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        //projectPath 경로에 name으로 파일 저장
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
