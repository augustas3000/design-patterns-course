package com.gustyflows.patterns.creational.factories.factory;


//public class PointFactory {
//
//    public static Point newCartesianPoint(double x, double y) {
//        return new Point(x, y);
//    }
//
//    public static Point newPolarPoint(double rho, double theta) {
//        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
//    }
//}

class Point {
    private double x, y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static class Factory {
        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }
}

class Demo {
    public static void main(String[] args) {
        //two ways since point constructor is visibile...
        Point point = Point.Factory.newCartesianPoint(2, 3);
//        Point point1 = new Point(4, 6);
        //if we want to restrict the client to using factory methods only that utilise private constructor,
        //the factory class has to be moved to where it can access the private constructor -> within the point class...
        Point point1 = Point.Factory.newPolarPoint(547, 6587);
        Point.Factory.newPolarPoint(554, 778);
    }
}
