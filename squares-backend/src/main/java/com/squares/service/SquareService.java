package com.squares.service;

import java.util.List;

import com.squares.dto.Square;
import com.squares.entity.Point;

/**
 * Service for squares calculation.
 * 
 * @author svingilis
 *
 */
public interface SquareService {

    /**
     * Retrieves list of all available squares (4 points) in a given points list.
     */
    List<Square> findSquares(List<Point> points);

}