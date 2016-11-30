package com.squares.dto;

import java.util.ArrayList;
import java.util.List;

import com.squares.entity.Point;

/**
 * Points import result DTO.
 * 
 * @author svingilis
 *
 */
public class ImportResult {

    private int importedPoints;
    
    private List<String> messages;
    
    private Iterable<Point> points;
    
    public ImportResult() {
        this.messages = new ArrayList<>();
    }

    public int getImportedPoints() {
        return importedPoints;
    }

    public void setImportedPoints(int importedPoints) {
        this.importedPoints = importedPoints;
    }
    
    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Iterable<Point> getPoints() {
        return points;
    }

    public void setPoints(Iterable<Point> points) {
        this.points = points;
    }

}
