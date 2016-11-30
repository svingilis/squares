package com.squares.repository;

import org.springframework.data.repository.CrudRepository;

import com.squares.entity.PointList;

/**
 * CRUD repository for points lists, has generated implementation.
 * 
 * @author svingilis
 *
 */
public interface PointListRepository extends CrudRepository<PointList, Long> {

    PointList findByName(String name);
    
}
