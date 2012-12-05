package org.omg.org.omg.dds.shapes.gen;

/**
 * This class should be generated from the file src/resources/shapes.idl
 */
public class ShapeType {
    private String color;
    private long x;
    private long y;
    private long shapesize;

    public ShapeType() { }

    public ShapeType(String color, long x, long y, long shapesize) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.shapesize = shapesize;
    }

    public String getColor() { return this.color; }
    public ShapeType setColor(String c) { this.color = c; return this; }

    public long getX() { return this.x; }
    public ShapeType setX(long x) { this.x = x; return this; }

    public long getY() { return this.y; }
    public ShapeType setY(long y) { this.y = y; return this; }

    public long getShapesize() { return this.shapesize; }
    public ShapeType setShapesize(long s) { this.shapesize = s; return this; }

}
