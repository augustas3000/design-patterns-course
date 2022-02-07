package com.gustyflows.patterns.creational.factories.factoryMethod;

public class FactoryMethodDemo {
    public static void main(String[] args) {
        Point point = PointImproved.newPolarPoint(5, 5);
        Point point1 = PointImproved.newCartesianPoint(2, 5);
    }
}

class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
    ILLEGAL:
    cant have overloading constructors in java, hence the need for more elaborate constructor as below

    public Point(double rho, double theta) {
        this.x = rho * Math.cos(theta);
        this.y = rho * Math.sin(theta);
    }
    */

    //   more elaborate constructor
    private Point(double a, double b, CoordinateSystem coordinateSystem) {
        switch (coordinateSystem) {
            case CARTESIAN:
                this.x = a;
                this.y = b;
            case POLAR:
                this.x = a * Math.cos(b);
                this.y = a * Math.sin(b);
        }
    }

}

enum CoordinateSystem {
    CARTESIAN,
    POLAR;
}

class PointImproved {
    private double x, y;

    private PointImproved(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point newCartesianPoint(double x, double y) {
        return new Point(x, y);
    }

    public static Point newPolarPoint(double rho, double theta) {
        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
    }
}
