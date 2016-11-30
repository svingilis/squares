package com.squares.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squares.entity.Point;
import com.squares.entity.PointList;
import com.squares.repository.PointListRepository;
import com.squares.repository.PointRepository;
import com.squares.service.PointExportService;

/**
 * Point REST controller.
 * 
 * @author svingilis
 *
 */
@RestController
@RequestMapping("/lists/{id}")
public class PointController {

    @Autowired
    private PointListRepository pointListRepository;
    
    @Autowired
    private PointRepository pointRepository;
    
    @Autowired
    private PointExportService pointExportService;
    
    /**
     * Retrieves pageable points of a list.
     */
    @GetMapping(path = "/points")
    public Iterable<Point> findListPoints(@PathVariable("id") Long id, Pageable pageable) {
        PointList pointList = pointListRepository.findOne(id);
        if (pointList == null) {
            throw new RuntimeException("List does not exist");
        }
        return pointRepository.findByPointListId(id, pageable);
    }
    
    /**
     * Retrieves points of a list as a text file.
     */
    @GetMapping(path = "/points", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> exportPoints(@PathVariable("id") Long id) {
        PointList pointList = pointListRepository.findOne(id);
        if (pointList == null) {
            throw new RuntimeException("List does not exist");
        }
        return pointExportService.exportPoints(pointRepository.findByPointListId(id));
    }

    public void setPointListRepository(PointListRepository pointListRepository) {
        this.pointListRepository = pointListRepository;
    }

    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void setPointExportService(PointExportService pointExportService) {
        this.pointExportService = pointExportService;
    }
    
}
