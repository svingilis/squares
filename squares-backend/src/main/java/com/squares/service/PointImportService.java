package com.squares.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.squares.entity.Point;
import com.squares.entity.PointList;

/**
 * Service for points importing.
 * 
 * @author svingilis
 *
 */
public interface PointImportService {

    public Iterable<Point> importPoints(PointList pointList, Iterable<Point> points);
    
    public ResponseEntity<?> importPoints(MultipartFile file) throws IOException;
    
}
