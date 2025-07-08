package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    void saveBoard(BoardVo boardVo);

    List<BoardVo> selectAllBoard();

    void updateBoard(@Param("id") int id, @Param("boardVo") BoardVo boardVo);
}
