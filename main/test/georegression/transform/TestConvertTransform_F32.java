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

package georegression.transform;

import georegression.misc.GrlConstants;
import georegression.misc.test.GeometryUnitTest;
import georegression.struct.affine.Affine2D_F32;
import georegression.struct.homography.Homography2D_F32;
import georegression.struct.point.Point2D_F32;
import georegression.struct.se.Se2_F32;
import georegression.transform.affine.AffinePointOps_F32;
import georegression.transform.homography.HomographyPointOps_F32;
import georegression.transform.se.SePointOps_F32;
import org.junit.Test;


/**
 * @author Peter Abeles
 */
public class TestConvertTransform_F32 {
	@Test
	public void Se_To_Affine_2D() {
		Se2_F32 a = new Se2_F32(2,3,0.5f);
		Affine2D_F32 b = ConvertTransform_F32.convert(a,new Affine2D_F32());

		Point2D_F32 pt = new Point2D_F32(3,4);
		Point2D_F32 expected = SePointOps_F32.transform(a,pt,null);
		Point2D_F32 found = AffinePointOps_F32.transform(b, pt, null);

		GeometryUnitTest.assertEquals(expected,found, GrlConstants.TEST_F32);
	}

	@Test
	public void Se_To_Homography_2D() {
		Se2_F32 a = new Se2_F32(2,3,0.5f);
		Homography2D_F32 b = ConvertTransform_F32.convert(a,new Homography2D_F32());

		Point2D_F32 pt = new Point2D_F32(3,4);
		Point2D_F32 expected = SePointOps_F32.transform(a,pt,null);
		Point2D_F32 found = HomographyPointOps_F32.transform(b, pt, null);

		GeometryUnitTest.assertEquals(expected, found, GrlConstants.TEST_F32);
	}

	@Test
	public void Affine_To_Homography_2D() {
		Affine2D_F32 a = new Affine2D_F32(1,2,3,4,5,6);
		Homography2D_F32 b = ConvertTransform_F32.convert(a,new Homography2D_F32());

		Point2D_F32 pt = new Point2D_F32(3,4);
		Point2D_F32 expected = AffinePointOps_F32.transform(a, pt, null);
		Point2D_F32 found = HomographyPointOps_F32.transform(b, pt, null);

		GeometryUnitTest.assertEquals(expected, found, GrlConstants.TEST_F32);
	}
}
