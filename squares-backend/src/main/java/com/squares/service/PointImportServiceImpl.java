package com.squares.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.squares.dto.ImportResult;
import com.squares.entity.Point;
import com.squares.entity.PointList;

/**
 * {@inheritDoc}
 * 
 * Points are imported from UTF_8 encoded stream.
 * 
 * @author svingilis
 *
 */
@Service
public class PointImportServiceImpl implements PointImportService {

    private static final int BATCH_SIZE = 20;

    private static final Logger LOG = LoggerFactory.getLogger(PointImportServiceImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public ResponseEntity<?> importPoints(MultipartFile file) throws IOException {
        ImportResult result = new ImportResult();
        Set<Point> points = parsePoints(null, file.getInputStream(), result);
        result.setImportedPoints(points.size());
        result.setPoints(points);
        return ResponseEntity.ok(result);
    }
    
    @Transactional
    @Override
    public Iterable<Point> importPoints(PointList pointList, Iterable<Point> points) {
        for (Point p : points) {
            p.setPointList(pointList);
        }
        persist(points);
        return points;
    }
    
    private Set<Point> parsePoints(PointList pointList, InputStream is, ImportResult result) {
        Set<Point> points = new LinkedHashSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                Point point = parsePoint(line, result);
                if (point == null) {
                    continue;
                }
                point.setPointList(pointList);
                LOG.debug("Created point: {}", point);
                if (points.contains(point)) {
                    String message = MessageFormat.format("Cannot create point from given line: ''{0}'', such point already exists", line);
                    LOG.debug(message);
                    result.getMessages().add(message);
                } else {
                    points.add(point);
                }
            }
            br.close();
        } catch (IOException e) {
            LOG.debug("Could not import points", e);
        }
        return points;
    }
    
    private Point parsePoint(String line, ImportResult result) {
        Scanner scanner = new Scanner(line);
        Point point = null;
        try {
            point = new Point(scanner.nextInt(), scanner.nextInt());
        } catch (InputMismatchException | IllegalArgumentException e) {
            String message = MessageFormat.format("Cannot create point from given line: ''{0}''", line);
            LOG.debug(message, e);
            result.getMessages().add(message);
            return null;
        } finally {
            scanner.close();
        }
        return point;
    }
    
    /**
     * Performs batch insert
     */
    private void persist(Iterable<Point> points) {
        int i = 0;
        for (Point p : points) {
            i++;
            entityManager.persist(p);
            if (i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
