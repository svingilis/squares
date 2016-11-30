package com.squares.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.squares.entity.Point;

/**
 * {@inheritDoc}
 * 
 * Points are exported to UTF_8 encoded stream.
 * 
 * @author svingilis
 *
 */
@Service
public class PointExportServiceImpl implements PointExportService {

    private static final Logger LOG = LoggerFactory.getLogger(PointImportServiceImpl.class);
    
    @Override
    public ResponseEntity<?> exportPoints(Iterable<Point> points) {
        ByteArrayOutputStream baos = toOutputStream(points);
        byte[] bytes = baos.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes); // this is not efficient
        InputStreamResource inputStreamResource = new InputStreamResource(is);
            return ResponseEntity
                  .ok()
                  .contentLength(baos.toByteArray().length)
                  .contentType(MediaType.APPLICATION_OCTET_STREAM)
                  .body(inputStreamResource);
    }

    private ByteArrayOutputStream toOutputStream(Iterable<Point> points) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (Point p : points) {
            try {
                baos.write(Joiner.on(" ").join(p.getX(), p.getY()).concat(System.lineSeparator()).toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                LOG.error("Could not write to stream", e);
            }
        }
        return baos;
    }
    
}
