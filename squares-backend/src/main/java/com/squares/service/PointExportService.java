package com.squares.service;

import org.springframework.http.ResponseEntity;

import com.squares.entity.Point;

/**
 * Service for points exporting.
 * 
 * @author svingilis
 *
 */
public interface PointExportService {

    public ResponseEntity<?> exportPoints(Iterable<Point> points);

}
