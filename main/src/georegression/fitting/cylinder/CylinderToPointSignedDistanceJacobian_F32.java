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

package georegression.fitting.cylinder;

import georegression.metric.MiscOps;
import georegression.struct.point.Point3D_F32;
import georegression.struct.point.Vector3D_F32;
import georegression.struct.shapes.Cylinder3D_F32;
import org.ddogleg.optimization.functions.FunctionNtoMxN;
import org.ejml.data.DMatrixRMaj;

import java.util.List;

/**
 * Jacobian of {@link CylinderToPointSignedDistance_F32}.
 *
 * @author Peter Abeles
 */
public class CylinderToPointSignedDistanceJacobian_F32 implements FunctionNtoMxN< /**/DMatrixRMaj > {
	// model of the cylinder
	private Cylinder3D_F32 cylinder = new Cylinder3D_F32();

	// points whose distance from the sphere is being computed
	private List<Point3D_F32> points;

	// used to convert float[] into shape parameters
	private CodecCylinder3D_F32 codec = new CodecCylinder3D_F32();

	public void setPoints(List<Point3D_F32> points) {
		this.points = points;
	}

	@Override
	public int getNumOfInputsN() {
		return 7;
	}

	@Override
	public int getNumOfOutputsM() {
		return points.size();
	}

	@Override
	public void process( /**/double[] input, /**/DMatrixRMaj output) {
		codec.decode(input,cylinder);

		Point3D_F32 cp = cylinder.line.p;
		Vector3D_F32 cs = cylinder.line.slope;

		// just need to compute this once
		float slopeDot = cs.dot(cs);
		float slopeNorm = (float)Math.sqrt(slopeDot);

		int index = 0;
		for( int i = 0; i < points.size(); i++ ) {
			Point3D_F32 p = points.get(i);

			float x = cp.x - p.x;
			float y = cp.y - p.y;
			float z = cp.z - p.z;

			float cc = x*x + y*y + z*z;

			float xdots = MiscOps.dot(x, y, z, cs);
			float b = xdots/slopeNorm;

			float distance = cc-b*b;

			// round off error can make distanceSq go negative when it is very close to zero
			if( distance < 0 ) {
				output.data[index++] = 0;
				output.data[index++] = 0;
				output.data[index++] = 0;

				output.data[index++] = 0;
				output.data[index++] = 0;
				output.data[index++] = 0;

				output.data[index++] = -1;

			} else {
				distance = (float)Math.sqrt(distance);

				output.data[index++] = (cp.x - p.x - xdots*cs.x/slopeDot)/distance;
				output.data[index++] = (cp.y - p.y - xdots*cs.y/slopeDot)/distance;
				output.data[index++] = (cp.z - p.z - xdots*cs.z/slopeDot)/distance;

				output.data[index++] = -xdots*( (cp.x - p.x)/slopeDot - (xdots/slopeDot)*(cs.x/slopeDot))/distance;
				output.data[index++] = -xdots*( (cp.y - p.y)/slopeDot - (xdots/slopeDot)*(cs.y/slopeDot))/distance;
				output.data[index++] = -xdots*( (cp.z - p.z)/slopeDot - (xdots/slopeDot)*(cs.z/slopeDot))/distance;

				output.data[index++] = -1;
			}
		}
	}

	@Override
	public /**/DMatrixRMaj declareMatrixMxN() {
		return new /**/DMatrixRMaj(getNumOfOutputsM(),getNumOfInputsN());
	}
}
