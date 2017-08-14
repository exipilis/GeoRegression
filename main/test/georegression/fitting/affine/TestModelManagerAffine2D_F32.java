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

package georegression.fitting.affine;

import georegression.misc.GrlConstants;
import georegression.struct.affine.Affine2D_F32;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestModelManagerAffine2D_F32 {

	@Test
	public void createModelInstance() {
		ModelManagerAffine2D_F32 alg = new ModelManagerAffine2D_F32();

		assertTrue(alg.createModelInstance() != null);
	}

	@Test
	public void copyModel() {
		ModelManagerAffine2D_F32 alg = new ModelManagerAffine2D_F32();

		Affine2D_F32 model = new Affine2D_F32(1,2,3,4,5,6);
		Affine2D_F32 found = new Affine2D_F32();

		alg.copyModel(model,found);

		assertEquals(model.a11,found.a11, GrlConstants.TEST_F32);
		assertEquals(model.a12,found.a12, GrlConstants.TEST_F32);
		assertEquals(model.a21,found.a21, GrlConstants.TEST_F32);
		assertEquals(model.a22,found.a22, GrlConstants.TEST_F32);
		assertEquals(model.tx,found.tx, GrlConstants.TEST_F32);
		assertEquals(model.ty,found.ty, GrlConstants.TEST_F32);
	}

}
