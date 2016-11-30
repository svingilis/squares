package com.squares.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.squares.entity.Point;

/**
 * CRUD repository for points, has generated implementation.
 * 
 * @author svingilis
 *
 */
public interface PointRepository extends CrudRepository<Point, Long> {

    Page<Point> findByPointListId(Long id, Pageable pageable);
    
    List<Point> findByPointListId(Long id);
    
    List<Point> deleteByPointListId(Long id);

}
