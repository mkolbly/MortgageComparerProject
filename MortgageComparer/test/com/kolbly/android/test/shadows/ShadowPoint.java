package com.kolbly.android.test.shadows;
//package com.kolbly.android.test;
//
//import org.robolectric.annotation.Implementation;
//import org.robolectric.annotation.Implements;
//import org.robolectric.annotation.RealObject;
//
//import android.graphics.Point;
//
//@Implements(Point.class)
//public class ShadowPoint
//{
//	 @RealObject private Point realPoint;
//	 
//    public void __constructor__(int x, int y) {
//        realPoint.x = x;
//        realPoint.y = y;
//    }
// 
//    public void __constructor__(Point src) {
//        realPoint.x = src.x;
//        realPoint.y = src.y;
//    }
// 
//    @Implementation
//    public void set(int x, int y) {
//        realPoint.x = x;
//        realPoint.y = y;
//    }
// 
//    @Implementation
//    public final void offset(int dx, int dy) {
//        realPoint.x += dx;
//        realPoint.y += dy;
//    }
// 
//    @Override @Implementation
//    public String toString() {
//        return "Point(" + realPoint.x + ", " + realPoint.y + ")";
//    }
//}
