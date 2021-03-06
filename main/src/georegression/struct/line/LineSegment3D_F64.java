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

package georegression.struct.line;

import georegression.struct.point.Point3D_F64;

import java.io.Serializable;


/**
 * Defines a line segment by its two end points.
 *
 * @author Peter Abeles
 */
public class LineSegment3D_F64 implements Serializable {
	public Point3D_F64 a = new Point3D_F64();
	public Point3D_F64 b = new Point3D_F64();

	public LineSegment3D_F64() {
	}

	public LineSegment3D_F64(Point3D_F64 a, Point3D_F64 b) {
		set( a, b );
	}

	public LineSegment3D_F64(double x0, double y0, double z0, double x1, double y1 , double z1) {
		set( x0, y0, z0, x1, y1, z1 );
	}
	
	public static LineSegment3D_F64 wrap( Point3D_F64 a , Point3D_F64 b ) {
		LineSegment3D_F64 ret = new LineSegment3D_F64();
		ret.a = a;
		ret.b = b;
		return ret;
	}

	public void set( LineSegment3D_F64 l ) {
		this.a.set( l.a );
		this.b.set( l.b );
	}

	public void set( Point3D_F64 a, Point3D_F64 b ) {
		this.a.set( a );
		this.b.set( b );
	}

	public void set( double x0, double y0, double z0, double x1, double y1 , double z1 ) {
		a.set( x0, y0, z0 );
		b.set( x1, y1, z1 );
	}

	public Point3D_F64 getA() {
		return a;
	}

	public void setA( Point3D_F64 a ) {
		this.a = a;
	}

	public Point3D_F64 getB() {
		return b;
	}

	public void setB( Point3D_F64 b ) {
		this.b = b;
	}

	public double slopeX() {
		return b.x-a.x;
	}

	public double slopeY() {
		return b.y-a.y;
	}

	public double getLength() {
		return a.distance(b);
	}

	public double getLength2() {
		return a.distance2(b);
	}

	public LineSegment3D_F64 copy() {
		return new LineSegment3D_F64( a, b );
	}
}
