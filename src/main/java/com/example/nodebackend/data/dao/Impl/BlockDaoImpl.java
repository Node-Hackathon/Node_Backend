package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.BlockDao;
import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.data.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional  // Add this annotation to manage transactions
public class BlockDaoImpl implements BlockDao {
    private final BlockRepository blockRepository;

    @Override
    public void saveBlock(Block block) {
        blockRepository.save(block);
    }
}
