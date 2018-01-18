/*
 * Copyright (C) 2011-2017, Peter Abeles. All Rights Reserved.
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

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Peter Abeles
 */
public class TestPoint2D_F32 extends GenericGeoTupleTests_F32<Point2D_F32> {

	public TestPoint2D_F32() {
		super( new Point2D_F32() );
	}

	@Test
	public void generic() {
		checkAll( 2 );
	}

	@Test
	public void equals_vector() {
		Point2D_F32 a = new Point2D_F32();
		Vector2D_F32 b = new Vector2D_F32();

		// numerically they are identical, but not the same type so this should fail
		assertFalse(a.equals(b));
	}
}
