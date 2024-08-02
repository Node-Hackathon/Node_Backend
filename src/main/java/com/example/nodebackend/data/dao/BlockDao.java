package com.example.nodebackend.data.dao;

import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
import com.example.nodebackend.data.entity.Block;

import java.util.List;

public interface BlockDao {
    void saveBlock(Block block);

    List<BlockResultReponseDto> getBlockResultImageList(Long id);

}
