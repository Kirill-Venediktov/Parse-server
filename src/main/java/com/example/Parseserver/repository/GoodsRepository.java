package com.example.Parseserver.repository;

import com.example.Parseserver.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Integer> {
    Iterable<Goods> findByTitle(String title);
}
