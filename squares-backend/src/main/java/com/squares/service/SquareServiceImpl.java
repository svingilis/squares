package com.squares.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.squares.dto.PointPair;
import com.squares.dto.Square;
import com.squares.entity.Point;

/**
 * Works with list containing unique points only!
 * 
 * @author svingilis
 *
 */
@Service
public class SquareServiceImpl implements SquareService {

    private static final Logger LOG = LoggerFactory.getLogger(SquareServiceImpl.class);
    
    /**
     * {@inheritDoc}
     * Iterates though point pairs and finds other pairs having same distance between points.
     * Instead of actual distance, distance square value is used.
     * Verifies if found pairs can form a square.
     * There can be at most two different distances between found pairs' points: sides or diagonals.
     */
    @Override
    public List<Square> findSquares(List<Point> points) {
        if (points.size() < 4) {
            return new ArrayList<>();
        }
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (i + 1 >= points.size()) {
                break;
            }
            PointPair pair = new PointPair(points.get(i), points.get(i + 1));
            LOG.debug("Point pair: {}", pair);
            int distance = distance(pair);
            List<PointPair> pairs = findPointsWithSameDistance(points, i + 2, distance);
            squares.addAll(findSquares(pair, pairs));
        }
        LOG.debug("Found number of squares: {}", squares.size());
        return squares;
    }
    
    private List<Square> findSquares(PointPair pair, List<PointPair> pairs) {
        List<Square> result = new ArrayList<>();
        for (PointPair pp : pairs) {
            if (isSquare(pair, pp)) {
                result.add(new Square(pair.getP1(), pair.getP2(), pp.getP1(), pp.getP2()));
            }
        }
        return result;
    }

    private boolean isSquare(PointPair pair1, PointPair pair2) {
        int distance1 = distance(pair1.getP1(), pair2.getP1());
        int distance2 = distance(pair1.getP1(), pair2.getP2());
        int distance3 = distance(pair1.getP2(), pair2.getP1());
        int distance4 = distance(pair1.getP2(), pair2.getP2());
        Set<Integer> distances = new HashSet<>();
        distances.add(distance1);
        distances.add(distance2);
        distances.add(distance3);
        distances.add(distance4);
        return distances.size() == 2;
    }

    // returns square of distance
    private int distance(PointPair pair) {
        return distance(pair.getP1(), pair.getP2());
    }
    
    private int distance(Point p1, Point p2) {
        return square((p2.getX() - p1.getX())) + square((p2.getY() - p1.getY()));
    }
    
    private  List<PointPair> findPointsWithSameDistance(List<Point> points, int startIndex, int distance) {
        List<PointPair> result = new ArrayList<>();
        for (int i = startIndex; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                PointPair pair = new PointPair(points.get(i), points.get(j));
                if (distance == distance(pair)) {
                    result.add(pair);
                }
            }
        }
        return result;
    }
    
    private int square(int x) {
        return x * x;
    }
}
