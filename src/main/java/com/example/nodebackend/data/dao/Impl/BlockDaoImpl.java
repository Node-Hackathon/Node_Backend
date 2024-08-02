package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.BlockDao;
import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.data.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional  // Add this annotation to manage transactions
public class BlockDaoImpl implements BlockDao {
    private final BlockRepository blockRepository;

    @Override
    public void saveBlock(Block block) {
        blockRepository.save(block);
    }

    @Override
    public List<BlockResultReponseDto> getBlockResultImageList(Long id) {
        List<Block> blocks = blockRepository.getByUserId(id);
        List<BlockResultReponseDto> blockResultReponseDtos = blocks.stream().map(block -> {
            BlockResultReponseDto blockResultReponseDto = new BlockResultReponseDto();
            blockResultReponseDto.setId(block.getId());
            blockResultReponseDto.setImageUrl(block.getImage_Url());
            blockResultReponseDto.setCreatedAt(block.getCreatedAt());
            return blockResultReponseDto;
        }).collect(Collectors.toList());
    return blockResultReponseDtos;
    }
}
