package com.example.nodebackend.data.dao;

import java.util.List;

public interface BlockDao {
    void saveBlock(Block block);

    List<BlockResultReponseDto> getBlockResultImageList(Long id);

}
