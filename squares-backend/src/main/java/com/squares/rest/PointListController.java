package com.squares.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.squares.dto.CreateList;
import com.squares.dto.Square;
import com.squares.entity.Point;
import com.squares.entity.PointList;
import com.squares.repository.PointListRepository;
import com.squares.repository.PointRepository;
import com.squares.service.PointImportService;
import com.squares.service.SquareService;

/**
 * List REST controller.
 * 
 * @author svingilis
 *
 */
@RestController
@RequestMapping(path = "/lists")
public class PointListController {

    @Autowired
    private PointListRepository pointListRepository;
    
    @Autowired
    private PointRepository pointRepository;
    
    @Autowired
    private PointImportService pointImportService;
    
    @Autowired
    private SquareService squareService;
    
    @GetMapping
    public Iterable<PointList> findAllLists() {
        return pointListRepository.findAll();
    }
    
    /**
     * Retrieves list by id.
     */
    @GetMapping(path = "/{id}")
    public PointList findListById(@PathVariable("id") Long id) {
        PointList pointList = pointListRepository.findOne(id);
        if (pointList == null) {
            throw new RuntimeException("List does not exist");
        }
        return pointList;
    }
    
    /**
     * Parses points from file, points are not persisted.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> parsePoints(@RequestParam("file") MultipartFile file) throws IOException {
        return pointImportService.importPoints(file);
    }
    
    /**
     * Creates and saves list together with provided points.
     * Overwrites list if list with same name already exists.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public PointList saveList(@RequestBody CreateList createList) {
        String name = createList.getList().getName();
        Assert.notNull(name, "List name is required");
        PointList existingList = pointListRepository.findByName(name);
        if (existingList != null) { // overwrite existing list
            pointRepository.deleteByPointListId(existingList.getId());
            pointImportService.importPoints(existingList, createList.getPoints());
            return existingList;
        }
        PointList list = pointListRepository.save(createList.getList());
        pointImportService.importPoints(list, createList.getPoints());
        return list;
    }

    /**
     * Retrieves available squares in given list
     */
    @GetMapping(path = "/{id}/squares")
    public List<Square> findSquares(@PathVariable("id") Long id) {
        PointList pointList = pointListRepository.findOne(id);
        if (pointList == null) {
            throw new RuntimeException("List does not exist");
        }
        List<Point> points = pointRepository.findByPointListId(id);
        return squareService.findSquares(points);
    }
    
    /**
     * Deletes list together with it's points.
     */
    @DeleteMapping(path = "/{id}")
    @Transactional
    public PointList deleteListById(@PathVariable("id") Long id) {
        PointList pointList = pointListRepository.findOne(id);
        if (pointList == null) {
            throw new RuntimeException("List does not exist");
        }
        pointRepository.deleteByPointListId(id);
        pointListRepository.delete(id);
        return pointList;
    }
    
    public void setPointListRepository(PointListRepository pointListRepository) {
        this.pointListRepository = pointListRepository;
    }

    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void setPointImportService(PointImportService pointImportService) {
        this.pointImportService = pointImportService;
    }

    public void setSquareService(SquareService squareService) {
        this.squareService = squareService;
    }

}