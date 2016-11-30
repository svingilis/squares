package com.squares.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.squares.dto.Square;
import com.squares.entity.Point;

public class SquareServiceImplTest {

    private SquareService service;
    
    @Before
    public void setup() {
        service = new SquareServiceImpl();
    }
    
    @Test
    public void shouldFindTwoSquares() {
        // given
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(4, 1));
        points.add(new Point(4, 4));
        points.add(new Point(1, 4));
        
        points.add(new Point(-1, -1));
        points.add(new Point(-4, -1));
        points.add(new Point(7, 4));
        points.add(new Point(-4, -4));
        points.add(new Point(-1, -4));
        
        // when
        List<Square> squares = service.findSquares(points);
        
        assertThat("Incorrect amount of squares found", squares, hasSize(2));
    }
    
    @Test
    public void shouldNotFindSquaresWithLessThanFourPoints() {
        // given
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(4, 1));
        points.add(new Point(4, 4));

     // when
        List<Square> squares = service.findSquares(points);
        
        assertThat("Incorrect amount of squares found", squares, hasSize(0));
    }
    
}
