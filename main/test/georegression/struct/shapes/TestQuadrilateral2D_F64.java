/*
 * Copyright (C) 2011-2018, Peter Abeles. All Rights Reserved.
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

import georegression.struct.point.Point2D_F64;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestQuadrilateral2D_F64 {
	@Test
	public void convert() {
		Quadrilateral_F64 polygon = new Quadrilateral_F64();
		polygon.a.set(1,2);
		polygon.b.set(2,3);
		polygon.c.set(3,4);
		polygon.c.set(4,5);

		List<Point2D_F64> list = polygon.convert(null,false);
		assertEquals(4,list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) == polygon.get(i));
		}

		list = polygon.convert(null,true);
		assertEquals(4,list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) != polygon.get(i));
			assertTrue(list.get(i).equals(polygon.get(i)));
		}
	}

	@Test
	public void set_list() {
		List<Point2D_F64> list = new ArrayList<>();

		list.add( new Point2D_F64(2,3));
		list.add( new Point2D_F64(3,4));
		list.add( new Point2D_F64(4,5));
		list.add( new Point2D_F64(5,6));

		Quadrilateral_F64 polygon = new Quadrilateral_F64();
		polygon.set(list);
		assertEquals(4,list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) != polygon.get(i));
			assertTrue(list.get(i).equals(polygon.get(i)));
		}
	}
}