package com.squares.dto;

import java.util.List;

import com.squares.entity.Point;
import com.squares.entity.PointList;

/**
 * DTO for list creation request.
 * 
 * @author svingilis
 *
 */
public class CreateList {

    private PointList list;
    
    private List<Point> points;

    public PointList getList() {
        return list;
    }

    public void setList(PointList list) {
        this.list = list;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    
}
