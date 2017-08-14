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

package georegression.fitting.sphere;

import georegression.misc.GrlConstants;
import georegression.struct.point.Point3D_F32;
import org.ddogleg.optimization.DerivativeChecker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestSphereToPointSignedDistanceJacobian_F32 {

	@Test
	public void compareToNumerical() {

		SphereToPointSignedDistance_F32 function = new SphereToPointSignedDistance_F32();
		SphereToPointSignedDistanceJacobian_F32 jacobian = new SphereToPointSignedDistanceJacobian_F32();

		// sphere
		/**/double param[] = new /**/double[]{1,2,3,4};

		List<Point3D_F32> points = new ArrayList<Point3D_F32>();

		// inside, should be negative
		points.add(new Point3D_F32(1.1f,1.95f,7.2f));
		// outside, should be positive
		points.add(new Point3D_F32(0.96f,-2.1f,3.001f));
		points.add(new Point3D_F32(5.2f,2.05f,3.1f));

		function.setPoints(points);
		jacobian.setPoints(points);

//		DerivativeChecker.jacobianPrint(function, jacobian, param, 100.0f*GrlConstants.TEST_F32,
//				GrlConstants.TEST_F32);
		assertTrue(DerivativeChecker.jacobian(function, jacobian, param, 100.0f * GrlConstants.TEST_F32,
				GrlConstants.TEST_F32));
	}

	@Test
	public void getN_and_getM() {
		SphereToPointSignedDistanceJacobian_F32 alg = new SphereToPointSignedDistanceJacobian_F32();

		List<Point3D_F32> points = new ArrayList<Point3D_F32>();
		points.add(new Point3D_F32(1,2,3.5f));
		points.add(new Point3D_F32(1,2,3.5f));
		points.add(new Point3D_F32(1,2,3.5f));

		alg.setPoints(points);

		assertEquals(4,alg.getNumOfInputsN());
		assertEquals(points.size(),alg.getNumOfOutputsM());
	}
}
