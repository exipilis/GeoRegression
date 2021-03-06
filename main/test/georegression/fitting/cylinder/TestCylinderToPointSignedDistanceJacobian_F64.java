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

package georegression.fitting.cylinder;

import georegression.fitting.sphere.SphereToPointSignedDistanceJacobian_F64;
import georegression.misc.GrlConstants;
import georegression.struct.point.Point3D_F64;
import org.ddogleg.optimization.DerivativeChecker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestCylinderToPointSignedDistanceJacobian_F64 {

	@Test
	public void compareToNumerical() {

		CylinderToPointSignedDistance_F64 function = new CylinderToPointSignedDistance_F64();
		CylinderToPointSignedDistanceJacobian_F64 jacobian = new CylinderToPointSignedDistanceJacobian_F64();

		// sphere
		/**/double param[] = new /**/double[]{1,2,3,0,0,2,4};

		List<Point3D_F64> points = new ArrayList<Point3D_F64>();

		// inside, should be negative
		points.add(new Point3D_F64(1.1,1.95,4.2));
		// outside, should be positive
		points.add(new Point3D_F64(0.96,-2.2,3.001));
		points.add(new Point3D_F64(5.2,2.05,3.1));

		function.setPoints(points);
		jacobian.setPoints(points);

//		DerivativeChecker.jacobianPrint(function, jacobian, param, 100.0*GrlConstants.TEST_F64,
//				GrlConstants.TEST_F64);
		assertTrue(DerivativeChecker.jacobian(function, jacobian, param, 100.0 * GrlConstants.TEST_F64,
				GrlConstants.TEST_F64));
	}

	@Test
	public void getN_and_getM() {
		SphereToPointSignedDistanceJacobian_F64 alg = new SphereToPointSignedDistanceJacobian_F64();

		List<Point3D_F64> points = new ArrayList<Point3D_F64>();
		points.add(new Point3D_F64(1,2,3.5));
		points.add(new Point3D_F64(1,2,3.5));
		points.add(new Point3D_F64(1,2,3.5));

		alg.setPoints(points);

		assertEquals(4,alg.getNumOfInputsN());
		assertEquals(points.size(), alg.getNumOfOutputsM());
	}

}
