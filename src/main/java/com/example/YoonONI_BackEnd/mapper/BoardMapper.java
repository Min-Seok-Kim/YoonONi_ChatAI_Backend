package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void saveBoard(BoardVo boardVo);

    String selectAllBoard();
}
