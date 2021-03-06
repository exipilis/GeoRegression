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

package georegression.struct;

import georegression.misc.GrlConstants;
import georegression.struct.affine.Affine2D_F32;
import georegression.struct.affine.Affine2D_F64;
import georegression.struct.point.*;
import georegression.struct.se.Se3_F32;
import georegression.struct.se.Se3_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.RandomMatrices_DDRM;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestConvertFloatType {

	Random rand = new Random(234);

	@Test
	public void convert_Se_64_32() {
		Se3_F64 src = new Se3_F64();
		RandomMatrices_DDRM.fillUniform(src.getR(), rand);
		src.getT().set(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

		Se3_F32 dst = ConvertFloatType.convert(src, null);

		assertTrue(isIdentical(src.getR(), dst.getR(), GrlConstants.TEST_F32));
		assertEquals(src.T.x, dst.T.x, GrlConstants.TEST_F32);
		assertEquals(src.T.y, dst.T.y, GrlConstants.TEST_F32);
		assertEquals(src.T.z, dst.T.z, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Affine2D_64_32() {
		Affine2D_F64 src = new Affine2D_F64(1,2,3,4,5,6);

		Affine2D_F32 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.a11, dst.a11, GrlConstants.TEST_F32);
		assertEquals(src.a12, dst.a12, GrlConstants.TEST_F32);
		assertEquals(src.a21, dst.a21, GrlConstants.TEST_F32);
		assertEquals(src.a22, dst.a22, GrlConstants.TEST_F32);
		assertEquals(src.tx, dst.tx, GrlConstants.TEST_F32);
		assertEquals(src.ty, dst.ty, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Affine2D_32_64() {
		Affine2D_F32 src = new Affine2D_F32(1,2,3,4,5,6);

		Affine2D_F64 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.a11, dst.a11, GrlConstants.TEST_F32);
		assertEquals(src.a12, dst.a12, GrlConstants.TEST_F32);
		assertEquals(src.a21, dst.a21, GrlConstants.TEST_F32);
		assertEquals(src.a22, dst.a22, GrlConstants.TEST_F32);
		assertEquals(src.tx, dst.tx, GrlConstants.TEST_F32);
		assertEquals(src.ty, dst.ty, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Point3D_64_32() {
		Point3D_F64 src = new Point3D_F64(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

		Point3D_F32 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.x, dst.x, GrlConstants.TEST_F32);
		assertEquals(src.y, dst.y, GrlConstants.TEST_F32);
		assertEquals(src.z, dst.z, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Point2D_64_32() {
		Point2D_F64 src = new Point2D_F64(rand.nextDouble(), rand.nextDouble());

		Point2D_F32 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.x, dst.x, GrlConstants.TEST_F32);
		assertEquals(src.y, dst.y, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Vector3D_64_32() {
		Vector3D_F64 src = new Vector3D_F64(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

		Vector3D_F32 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.x, dst.x, GrlConstants.TEST_F32);
		assertEquals(src.y, dst.y, GrlConstants.TEST_F32);
		assertEquals(src.z, dst.z, GrlConstants.TEST_F32);
	}

	@Test
	public void convert_Vector2D_64_32() {
		Vector2D_F64 src = new Vector2D_F64(rand.nextDouble(), rand.nextDouble());

		Vector2D_F32 dst = ConvertFloatType.convert(src, null);

		assertEquals(src.x, dst.x, GrlConstants.TEST_F32);
		assertEquals(src.y, dst.y, GrlConstants.TEST_F32);
	}

	public static boolean isIdentical(DMatrixRMaj a, FMatrixRMaj b, double tol) {
		if (a.numRows != b.numRows || a.numCols != b.numCols) {
			return false;
		}
		if (tol < 0)
			throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");

		final int length = a.getNumElements();
		for (int i = 0; i < length; i++) {
			double valA = a.get(i);
			float valB = b.get(i);

			// if either is negative or positive infinity the result will be positive infinity
			// if either is NaN the result will be NaN
			double diff = Math.abs(valA - valB);

			// diff = NaN == false
			// diff = infinity == false
			if (tol >= diff)
				continue;

			if (Double.isNaN(valA)) {
				return Float.isNaN(valB);
			} else if (Double.isInfinite(valA)) {
				return valA == valB;
			} else {
				return false;
			}
		}
		return true;
	}
}
