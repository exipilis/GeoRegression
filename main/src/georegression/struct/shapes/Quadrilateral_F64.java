/*
 * Copyright (C) 2011-2015, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Geometric Regression Library (GeoRegression).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package georegression.struct.shapes;

import georegression.metric.Area2D_F64;
import georegression.struct.line.LineSegment2D_F64;
import georegression.struct.point.Point2D_F64;

import java.io.Serializable;

/**
 * A polygon with 4 vertices, a,b,c, and d.  The vertices are in order sequential order of a,b,c,d.
 *
 */
public class Quadrilateral_F64 implements Serializable {
	public Point2D_F64 a;
	public Point2D_F64 b;
	public Point2D_F64 c;
	public Point2D_F64 d;

	public Quadrilateral_F64() {
		a = new Point2D_F64();
		b = new Point2D_F64();
		c = new Point2D_F64();
		d = new Point2D_F64();
	}

	public Quadrilateral_F64( Quadrilateral_F64 quad ) {
		this();
		a.set(quad.a);
		b.set(quad.b);
		c.set(quad.c);
		d.set(quad.d);
	}

	public Quadrilateral_F64( double x0, double y0 , double x1, double y1 ,
							  double x2, double y2 , double x3, double y3 ) {
		a = new Point2D_F64(x0,y0);
		b = new Point2D_F64(x1,y1);
		c = new Point2D_F64(x2,y2);
		d = new Point2D_F64(x3,y3);
	}

	public Quadrilateral_F64(Point2D_F64 a, Point2D_F64 b, Point2D_F64 c, Point2D_F64 d ) {
		this(a,b,c,d,true);
	}

	public Quadrilateral_F64(Point2D_F64 a, Point2D_F64 b, Point2D_F64 c, Point2D_F64 d, boolean copy ) {
		if( copy ) {
			this.a = new Point2D_F64(a);
			this.b = new Point2D_F64(b);
			this.c = new Point2D_F64(c);
			this.d = new Point2D_F64(d);
		} else {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}
	}

	/**
	 * Returns the area of this quadrilateral
	 * @return area
	 */
	public double area() {
		return Area2D_F64.quadrilateral(this);
	}

	public Point2D_F64 get( int index ) {
		switch( index ) {
			case 0: return a;
			case 1: return b;
			case 2: return c;
			case 3: return d;
		}
		throw new IllegalArgumentException("Requested index out of range. "+index);
	}

	public Point2D_F64 getA() {
		return a;
	}

	public void setA(Point2D_F64 a) {
		this.a = a;
	}

	public Point2D_F64 getB() {
		return b;
	}

	public void setB(Point2D_F64 b) {
		this.b = b;
	}

	public Point2D_F64 getC() {
		return c;
	}

	public void setC(Point2D_F64 c) {
		this.c = c;
	}

	public Point2D_F64 getD() {
		return d;
	}

	public void setD(Point2D_F64 d) {
		this.d = d;
	}

	public LineSegment2D_F64 getLine( int which , LineSegment2D_F64 storage ) {
		if( storage == null )
			storage = new LineSegment2D_F64();

		switch( which ) {
			case 0: storage.a.set(a);storage.b.set(b);break;
			case 1: storage.a.set(b);storage.b.set(c);break;
			case 2: storage.a.set(c);storage.b.set(d);break;
			case 3: storage.a.set(d);storage.b.set(a);break;
			default:
				throw new IllegalArgumentException("Requested index out of range. "+which);
		}
		return storage;
	}

	public double getSideLength( int which ) {
		return Math.sqrt(getSideLength2(which));
	}

	public double getSideLength2( int which ) {
		switch( which ) {
			case 0: return a.distance2(b);
			case 1: return b.distance2(c);
			case 2: return c.distance2(d);
			case 3: return d.distance2(a);
			default:
				throw new IllegalArgumentException("Requested index out of range. "+which);
		}
	}

	public void set(Quadrilateral_F64 quad) {
		this.a.set(quad.a);
		this.b.set(quad.b);
		this.c.set(quad.c);
		this.d.set(quad.d);
	}

	public Quadrilateral_F64 copy() {
		return new Quadrilateral_F64(this);
	}

	/**
	 * Returns true if the two quadrilaterals are equal to each other to within tolerance.  Equality is defined
	 * by seeing if the distance between two equivalent vertexes is within tolerance.
	 *
	 * @param quad The second quadrilateral
	 * @param tol Maximum allowed distance between vertexes.
	 * @return true if equals or false if not
	 */
	public boolean isEquals( Quadrilateral_F64 quad , double tol ) {
		tol *= tol;

		if( a.distance2(quad.a) > tol )
			return false;
		if( b.distance2(quad.b) > tol )
			return false;
		if( c.distance2(quad.c) > tol )
			return false;
		return d.distance2(quad.d) <= tol;
	}

	public String toString() {
		return getClass().getSimpleName()+"{ a("+a.x+" "+a.y+") b("+b.x+" "+b.y+") c("+c.x+" "+c.y+") d("+d.x+" "+d.y+") }";
	}
}
