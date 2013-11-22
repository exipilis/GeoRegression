/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Geometric Regression Library (GeoRegression).
 *
 * GeoRegression is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * GeoRegression is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with GeoRegression.  If not, see <http://www.gnu.org/licenses/>.
 */

package georegression.struct.shapes;

/**
 * <p>
 * An axis aligned rectangle in 2D that is specified by its top-left (tl_x,tl_y) corner, width, and height.  The three
 * other corners are (tl_x + width,tl_y), (tl_x,tl_y + height), (tl_x + width,tl_y + height).
 * </p>
 *
 * @author Peter Abeles
 */
public class Rectangle2D_F32 {

	/**
	 * Top-left corner x-axis
	 */
	public float tl_x;
	/**
	 * Top-left corner y-axis
	 */
	public float tl_y;
	/**
	 * Rectangle's width
	 */
	public float width;
	/**
	 * Rectangle's height
	 */
	public float height;

	public Rectangle2D_F32() {
	}

	public Rectangle2D_F32( float tl_x, float tl_y, float width, float height ) {
		this.width = width;
		this.height = height;
		this.tl_x = tl_x;
		this.tl_y = tl_y;
	}

	/**
	 * Sets the top left point
	 *
	 * @param x
	 * @param y
	 */
	public void setPoint( float x, float y ) {
		this.tl_x = x;
		this.tl_y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth( float width ) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight( float height ) {
		this.height = height;
	}

	/**
	 * @return top left x-coordinate
	 */
	public float getX() {
		return tl_x;
	}

	/**
	 * Sets the top left x-coordinate
	 *
	 * @param x top left x-coordinate
	 */
	public void setX( float x ) {
		this.tl_x = x;
	}

	/**
	 * @return top left y-coordinate
	 */
	public float getY() {
		return tl_y;
	}

	/**
	 * Sets the top left y-coordinate
	 *
	 * @param y top left y-coordinate
	 */
	public void setY( float y ) {
		this.tl_y = y;
	}

	/**
	 * Sets this rectangle to be equal to the passed in rectangle.
	 * @param r Rectangle which this is to be set equal to
	 */
	public void set(Rectangle2D_I32 r) {
		this.tl_x = r.tl_x;
		this.tl_y = r.tl_y;
		this.width = r.width;
		this.height = r.height;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"{" +
				"tl=[ " + tl_x + " , " + tl_y +
				"], width=" + width + ", height=" + height + '}';
	}
}