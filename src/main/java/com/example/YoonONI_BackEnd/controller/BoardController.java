package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board/save")
    public ResponseEntity<?> save(RequestDataSet requestDataSet) {
        return boardService.saveBoard(requestDataSet);
    }

    @GetMapping("/board/search")
    public ResponseEntity<?> search(RequestDataSet requestDataSet) {
        return boardService.selectAllBoard(requestDataSet);
    }
}
