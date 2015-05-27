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

package georegression.struct.point;

import georegression.geometry.GeometryMath_F64;
import georegression.geometry.UtilVector3D_F64;
import georegression.struct.GeoTuple3D_F64;

/**
 * Spacial vector in 3D
 *
 */
public class Vector3D_F64 extends GeoTuple3D_F64<Vector3D_F64> {

	public Vector3D_F64( GeoTuple3D_F64 orig ) {
		this(orig.x,orig.y,orig.z);
	}

	public Vector3D_F64( double x, double y, double z ) {
		super( x, y, z );
	}

	public Vector3D_F64() {
	}

	public Vector3D_F64( Point3D_F64 a, Point3D_F64 b ) {
		x = b.getX() - a.getX();
		y = b.getY() - a.getY();
		z = b.getZ() - a.getZ();
	}

	public void set( Vector3D_F64 v ) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/**
	 * In-place minus operation. this = a - b.
	 *
	 * @param a Point
	 * @param b Point
	 */
	public void minus( Point3D_F64 a , Point3D_F64 b ) {
		x = a.x - b.x;
		y = a.y - b.y;
		z = a.z - b.z;
	}

	public Vector3D_F64 copy() {
		return new Vector3D_F64( x, y, z );
	}

	/**
	 * In-place cross product operation.  this = a cross b
	 *
	 * @param a Vector
	 * @param b Vector
	 */
	public void cross( Vector3D_F64 a, Vector3D_F64 b ) {
		GeometryMath_F64.cross( a, b, this );
	}

	public Vector3D_F64 cross( Vector3D_F64 b ) {
		Vector3D_F64 c = new Vector3D_F64();
		GeometryMath_F64.cross( this, b, c );

		return c;
	}

	@Override
	public Vector3D_F64 createNewInstance() {
		return new Vector3D_F64();
	}

	@Override
	public String toString() {
		return "V( " + x + " " + y + " " + z + " )";
	}

	public void normalize() {
		double r = norm();
		x /= r;
		y /= r;
		z /= r;
	}

	/**
	 * Dot product between this and 'a' = this.x * a.x + this.y * a.y + this.z * a.z
	 *
	 * @param a A vector
	 * @return dot product.
	 */
	public double dot( Vector3D_F64 a ) {
		return x * a.x + y * a.y + z * a.z;
	}

	/**
	 * Returns the acute angle between the two vectors.  Computed using the dot product.
	 * @param a Vector
	 * @return Acute angle in radians between 'this' and 'a'.
	 */
	public double acute( Vector3D_F64 a ) {
		return UtilVector3D_F64.acute(this,a);
	}
}
