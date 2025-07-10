package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.mapper.BoardMapper;
import com.example.YoonONI_BackEnd.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> saveBoard(RequestDataSet requestDataSet) {
        BoardVo boardVo = BoardVo.builder()
                .content(requestDataSet.inGetString("content"))
                .userId(requestDataSet.inGetString("userId"))
                .title(requestDataSet.inGetString("title"))
                .build();

        boardMapper.saveBoard(boardVo);
        return ResponseEntity.ok().body("저장");
    }

    public ResponseEntity<?> selectAllBoard(RequestDataSet requestDataSet) {

        return ResponseEntity.ok().body(boardMapper.selectAllBoard());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateBoard(int id, RequestDataSet requestDataSet) {
        BoardVo boardVo = BoardVo.builder()
                .content(requestDataSet.inGetString("content"))
                .title(requestDataSet.inGetString("title"))
                .updatedAt(LocalDateTime.now())
                .build();

        boardMapper.updateBoard(id, boardVo);

        return ResponseEntity.ok().body("성공");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteBoard(int id, RequestDataSet requestDataSet) {
        boardMapper.deleteBoard(id);

        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }
}
