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

package georegression.geometry.algs;

import georegression.geometry.UtilEllipse_F32;
import georegression.geometry.UtilVector2D_F32;
import georegression.misc.GrlConstants;
import georegression.struct.point.Point2D_F32;
import georegression.struct.point.Vector2D_F32;
import georegression.struct.shapes.EllipseRotated_F32;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Peter Abeles
 */
public class TestTangentLinesTwoEllipses_F32 {

	Point2D_F32 tangentA0 = new Point2D_F32(); Point2D_F32 tangentA1 = new Point2D_F32();
	Point2D_F32 tangentA2 = new Point2D_F32(); Point2D_F32 tangentA3 = new Point2D_F32();
	Point2D_F32 tangentB0 = new Point2D_F32(); Point2D_F32 tangentB1 = new Point2D_F32();
	Point2D_F32 tangentB2 = new Point2D_F32(); Point2D_F32 tangentB3 = new Point2D_F32();

	@Test
	public void process() {
		TangentLinesTwoEllipses_F32 alg = new TangentLinesTwoEllipses_F32(GrlConstants.TEST_F32, 20);

		for( int i = 0; i < 20; i++ ) {
			float theta = i*GrlConstants.F_PI/20 - GrlConstants.F_PI/2.0f;
//			System.out.println(i+"  Theta = "+theta);
			check( new EllipseRotated_F32(0,10,5,4,theta), new EllipseRotated_F32(0,0,4,3,0), alg);
			check( new EllipseRotated_F32(0,10,4,4,theta), new EllipseRotated_F32(0,0,4,4,0), alg);
			check( new EllipseRotated_F32(2.5f,10,5,4,theta), new EllipseRotated_F32(0,0,4.9f,3, GrlConstants.F_PId2), alg);
		}
	}

	public void check( EllipseRotated_F32 ellipseA , EllipseRotated_F32 ellipseB , TangentLinesTwoEllipses_F32 alg ) {
		assertTrue(alg.process(ellipseA,ellipseB,
				tangentA0, tangentA1, tangentA2, tangentA3,
				tangentB0, tangentB1, tangentB2, tangentB3));

		assertTrue(alg.isConverged());

		// make sure all the points are unique
		checkResults(ellipseA, ellipseB, true);
	}

	@Test
	public void initialize() {
		TangentLinesTwoEllipses_F32 alg = new TangentLinesTwoEllipses_F32(GrlConstants.TEST_F32, 20);

		EllipseRotated_F32 ellipseA = new EllipseRotated_F32(0,10,5,4,0);
		EllipseRotated_F32 ellipseB = new EllipseRotated_F32(0,0,4,3, (float)Math.PI/2.0f);

		assertTrue(alg.initialize(ellipseA,ellipseB,
				tangentA0, tangentA1, tangentA2, tangentA3,
				tangentB0, tangentB1, tangentB2, tangentB3));

		checkResults(ellipseA, ellipseB, false);
	}

	private void checkResults(EllipseRotated_F32 ellipseA, EllipseRotated_F32 ellipseB ,
							  boolean completeTest ) {
		// make sure all the points are unique
		assertFalse(tangentA0.distance(tangentA1) <= GrlConstants.TEST_F32);
		assertFalse(tangentA0.distance(tangentA2) <= GrlConstants.TEST_F32);
		assertFalse(tangentA0.distance(tangentA3) <= GrlConstants.TEST_F32);
		assertFalse(tangentA1.distance(tangentA2) <= GrlConstants.TEST_F32);
		assertFalse(tangentA1.distance(tangentA3) <= GrlConstants.TEST_F32);
		assertFalse(tangentA2.distance(tangentA3) <= GrlConstants.TEST_F32);

		assertFalse(tangentB0.distance(tangentB1) <= GrlConstants.TEST_F32);

		if( completeTest ) {
			assertFalse(tangentB0.distance(tangentB2) <= GrlConstants.TEST_F32);
			assertFalse(tangentB0.distance(tangentB3) <= GrlConstants.TEST_F32);
			assertFalse(tangentB1.distance(tangentB2) <= GrlConstants.TEST_F32);
			assertFalse(tangentB1.distance(tangentB3) <= GrlConstants.TEST_F32);
			assertFalse(tangentB2.distance(tangentB3) <= GrlConstants.TEST_F32);

			// make sure each pair is tangent
			checkIsTangent(tangentA0, tangentB0, ellipseA, ellipseB);
			checkIsTangent(tangentA1, tangentB1, ellipseA, ellipseB);
			checkIsTangent(tangentA2, tangentB2, ellipseA, ellipseB);
			checkIsTangent(tangentA3, tangentB3, ellipseA, ellipseB);
		}
	}

	private void checkIsTangent( Point2D_F32 a , Point2D_F32 b ,
								 EllipseRotated_F32 ellipseA, EllipseRotated_F32 ellipseB)
	{
		float ta = UtilEllipse_F32.computeAngle(a,ellipseA);
		float tb = UtilEllipse_F32.computeAngle(b,ellipseB);

		float slopeX = b.x-a.x;
		float slopeY = b.y-a.y;
		float r = (float)Math.sqrt( slopeX*slopeX + slopeY*slopeY);

		slopeX /= r;
		slopeY /= r;

		Vector2D_F32 slopeA = UtilEllipse_F32.computeTangent(ta,ellipseA,null);
		Vector2D_F32 slopeB = UtilEllipse_F32.computeTangent(tb,ellipseB,null);

		assertTrue(UtilVector2D_F32.identicalSign(slopeX,slopeY, slopeA.x, slopeA.y, GrlConstants.TEST_SQ_F32));
		assertTrue(UtilVector2D_F32.identicalSign(slopeX,slopeY, slopeB.x, slopeB.y, GrlConstants.TEST_SQ_F32));

	}

	@Test
	public void selectTangent() {
		TangentLinesTwoEllipses_F32 alg = new TangentLinesTwoEllipses_F32(GrlConstants.TEST_F32, 20);

		EllipseRotated_F32 ellipse = new EllipseRotated_F32(0,10,2,2,0);

		Point2D_F32 a = new Point2D_F32(2,5);
		Point2D_F32 srcA = new Point2D_F32(2,11.5f);
		Point2D_F32 found = new Point2D_F32();

		alg.centerLine.set(0,0,0,10);
		assertTrue(alg.selectTangent(a,srcA,ellipse,found, false));

		assertEquals(2, found.x, GrlConstants.TEST_F32);
		assertEquals(10, found.y, GrlConstants.TEST_F32);

		assertEquals(1.5f*1.5f, alg.sumDifference, GrlConstants.TEST_F32);

		assertTrue(alg.selectTangent(a,srcA,ellipse,found, true));
		assertNotEquals(2, found.x, GrlConstants.TEST_F32);
		assertNotEquals(10, found.y, GrlConstants.TEST_F32);
	}
}
