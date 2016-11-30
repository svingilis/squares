package com.squares.dto;

import com.google.common.base.MoreObjects;
import com.squares.entity.Point;

/**
 * DTO for pair of points, order of points is not important.
 * 
 * @author svingilis
 *
 */
public class PointPair {

    private Point p1;

    private Point p2;

    public PointPair(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Point 1", p1).add("Point 2", p2).toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
        result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PointPair other = (PointPair) obj;
        if (p1 == null) {
            if (other.p1 != null) {
                return false;
            }
        } else if (!p1.equals(other.p1)) {
            return false;
        }
        if (p2 == null) {
            if (other.p2 != null) {
                return false;
            }
        } else if (!p2.equals(other.p2)) {
            return false;
        }
        return true;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

}
