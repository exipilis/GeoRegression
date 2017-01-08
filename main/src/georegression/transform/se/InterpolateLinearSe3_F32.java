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

package georegression.transform.se;

import georegression.geometry.ConvertRotation3D_F32;
import georegression.struct.point.Vector3D_F32;
import georegression.struct.se.Se3_F32;
import georegression.struct.so.Rodrigues_F32;
import org.ejml.data.RowMatrix_F32;
import org.ejml.ops.CommonOps_D32;

/**
 * <p>
 * Linear interpolation between two {@link georegression.struct.se.Se3_F32} transforms.  It is assumed that
 * the rotation able between the two transforms is {@code < 180} degrees.  If more than 180 degrees than it will chose
 * the wrong direction to rotate.
 * </p>
 * <p>
 * The rotation and translation are interpolated separately.  Translation has straight forward linear interpolation
 * applied to it.  Rotations are interpolated by finding the axis of rotation and the rotation angle.  Then
 * the angle is linearly interpolated and the rotation matrix found by the angle and axis.
 * </p>
 * @author Peter Abeles
 */
public class InterpolateLinearSe3_F32 {

	// transform at the start
	Se3_F32 initial = new Se3_F32();
	// rodrigues representation of the rotation
	Rodrigues_F32 rotation = new Rodrigues_F32();
	// magnitude of the rotation.  between initial and end
	float rotMagnitude;
	// translation between initial and end
	Vector3D_F32 translation = new Vector3D_F32();

	// difference between the initial transform and the end or the output
	RowMatrix_F32 R = new RowMatrix_F32(3,3);

	/**
	 * Specify the two transforms which values are to be interpolated between
	 * @param initial first transform
	 * @param end second transform
	 */
	public void setTransforms( Se3_F32 initial , Se3_F32 end) {
		this.initial.set(initial);

		translation.x = end.T.x - initial.T.x;
		translation.y = end.T.y - initial.T.y;
		translation.z = end.T.z - initial.T.z;

		CommonOps_D32.multTransA(initial.getR(), end.getR(), R);

		ConvertRotation3D_F32.matrixToRodrigues(R,rotation);
		rotMagnitude = rotation.theta;
	}

	/**
	 * Interpolates a value between the first and second transform.  A value close to 0 will be
	 * more similar to the initial and 1 more similar to the end.
	 * @param where A value between 0 and 1 which specifies the interpolation location
	 * @param output Resulting transform
	 */
	public void interpolate( float where , Se3_F32 output ) {
		rotation.setTheta(where*rotMagnitude);
		ConvertRotation3D_F32.rodriguesToMatrix(rotation,R);

		output.T.x = initial.T.x + where*translation.x;
		output.T.y = initial.T.y + where*translation.y;
		output.T.z = initial.T.z + where*translation.z;

		CommonOps_D32.mult(initial.R,R,output.R);
	}

}
