package com.squares.service.dev;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squares.entity.Point;
import com.squares.entity.PointList;
import com.squares.repository.PointListRepository;
import com.squares.repository.PointRepository;

/**
 * Called on application startup to populate test data to speed up development
 * 
 * @author svingilis
 *
 */
@Service
public class PopulateDataServiceImpl {

    @Autowired
    private PointListRepository pointListRepository;
    
    @Autowired
    private PointRepository pointRepository;
    
    @PostConstruct
    public void populate() {
        PointList emptyList = new PointList("Empty List");
        emptyList = pointListRepository.save(emptyList);
        
        PointList shortList = new PointList("Short List");
        shortList = pointListRepository.save(shortList);
        List<Point> points = createPoints(shortList, 5);
        pointRepository.save(points);
        
        PointList longList = new PointList("Long List");
        longList = pointListRepository.save(longList);
        points = createPoints(longList, 100);
        pointRepository.save(points);
        
        PointList listWithSquares = new PointList("List with Squares");
        listWithSquares = pointListRepository.save(listWithSquares);
        points = new ArrayList<>();
        points.add(new Point(1, 1, listWithSquares));
        points.add(new Point(4, 1, listWithSquares));
        points.add(new Point(4, 4, listWithSquares));
        points.add(new Point(1, 4, listWithSquares));
        
        points.add(new Point(-1, -1, listWithSquares));
        points.add(new Point(-4, -1, listWithSquares));
        points.add(new Point(-4, -4, listWithSquares));
        points.add(new Point(-1, -4, listWithSquares));
        pointRepository.save(points);
    }

    private List<Point> createPoints(PointList list, int numberOfPoint) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < numberOfPoint; i++) {
            Point point = new Point(i, i);
            point.setPointList(list);
            points.add(point);
        }
        return points;
    }

    public void setPointListRepository(PointListRepository pointListRepository) {
        this.pointListRepository = pointListRepository;
    }

    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }
    
}
