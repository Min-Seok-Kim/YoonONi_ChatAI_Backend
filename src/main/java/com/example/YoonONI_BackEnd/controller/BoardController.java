package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<?> save(RequestDataSet requestDataSet) {
        return boardService.saveBoard(requestDataSet);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(RequestDataSet requestDataSet) {
        return boardService.selectAllBoard(requestDataSet);
    }

    @PutMapping("/:{id}")
    public ResponseEntity<?> modify(@PathVariable int id, RequestDataSet requestDataSet) {
        return boardService.updateBoard(id, requestDataSet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, RequestDataSet requestDataSet) {
        return boardService.deleteBoard(id, requestDataSet);
    }
}
