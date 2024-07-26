package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Center;
import com.example.nodebackend.data.entity.QCenter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CenterRepositoryCustomImpl implements CenterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Center> getCenterList(String region) {
        QCenter qCenter = QCenter.center;
        return jpaQueryFactory.selectFrom(qCenter)
                .where(qCenter.center_address.contains(region))
                .fetch();
    }
}
