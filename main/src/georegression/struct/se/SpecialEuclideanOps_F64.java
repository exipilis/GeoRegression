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

package georegression.struct.se;

import georegression.geometry.ConvertRotation3D_F64;
import georegression.struct.EulerType;
import georegression.struct.affine.Affine2D_F64;
import georegression.struct.point.Vector3D_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;


/**
 * Various operations related to {@link SpecialEuclidean} transformations.
 *
 * @author Peter Abeles
 */
public class SpecialEuclideanOps_F64 {

	/**
	 * Sets the provided transform so that it does not transform any points.
	 *
	 * @param se The transform which is to be set to no motion.
	 */
	public static void setToNoMotion( Se3_F64 se ) {
		CommonOps_DDRM.setIdentity( se.getR() );
		se.getT().set( 0, 0, 0 );
	}

	/**
	 * Converts {@link Se2_F64} into {@link Affine2D_F64}.
	 * @param se (Input) Se2
	 * @param affine (Output) Equivalent affine.  If null a new object will be declared.
	 * @return Equivalent affine.
	 */
	public static Affine2D_F64 toAffine( Se2_F64 se , Affine2D_F64 affine ) {
		if( affine == null )
			affine = new Affine2D_F64();

		affine.a11 = se.c;
		affine.a12 = -se.s;
		affine.a21 = se.s;
		affine.a22 = se.c;

		affine.tx = se.T.x;
		affine.ty = se.T.y;

		return affine;
	}

	/**
	 * Converts it into a 4 by 4 homogeneous matrix.
	 *
	 * @param se  original 3D transform
	 * @param ret Where the results will be written to.  If null a new matrix is declared. Modified.
	 * @return equivalent homogeneous transform.
	 */
	public static DMatrixRMaj toHomogeneous( Se3_F64 se, DMatrixRMaj ret ) {
		if( ret == null )
			ret = new DMatrixRMaj( 4, 4 );
		else {
			ret.set( 3, 0, 0 );
			ret.set( 3, 1, 0 );
			ret.set( 3, 2, 0 );
		}

		CommonOps_DDRM.insert( se.getR(), ret, 0, 0 );
		Vector3D_F64 T = se.getT();

		ret.set( 0, 3, T.x );
		ret.set( 1, 3, T.y );
		ret.set( 2, 3, T.z );
		ret.set( 3, 3, 1 );

		return ret;
	}

	/**
	 * Converts a homogeneous representation into {@link Se3_F64}.
	 *
	 * @param H   Homogeneous 4 by 4 matrix.
	 * @param ret If not null where the results are written to.
	 * @return Se3_F64 transform.
	 */
	public static Se3_F64 toSe3(DMatrixRMaj H, Se3_F64 ret ) {
		if( H.numCols != 4 || H.numRows != 4 )
			throw new IllegalArgumentException( "The homogeneous matrix must be 4 by 4 by definition." );

		if( ret == null )
			ret = new Se3_F64();

		ret.setTranslation( (double) H.get( 0, 3 ), (double) H.get( 1, 3 ), (double) H.get( 2, 3 ) );

		CommonOps_DDRM.extract( H, 0, 3, 0, 3, ret.getR(), 0, 0 );

		return ret;
	}

	/**
	 * Converts it into a 3 by 3 homogeneous matrix.
	 *
	 * @param se  original 2D transform
	 * @param ret Where the results will be written to.  If null a new matrix is declared. Modified.
	 * @return equivalent homogeneous transform.
	 */
	public static DMatrixRMaj toHomogeneous( Se2_F64 se, DMatrixRMaj ret ) {
		if( ret == null )
			ret = new DMatrixRMaj( 3, 3 );
		else {
			ret.set( 2, 0, 0 );
			ret.set( 2, 1, 0 );
		}

		final double c = se.getCosineYaw();
		final double s = se.getSineYaw();

		ret.set( 0, 0, c );
		ret.set( 0, 1, -s );
		ret.set( 1, 0, s );
		ret.set( 1, 1, c );
		ret.set( 0, 2, se.getX() );
		ret.set( 1, 2, se.getY() );
		ret.set( 2, 2, 1 );

		return ret;
	}

	/**
	 * Converts a homogeneous representation into {@link Se2_F64}.
	 *
	 * @param H   Homogeneous 3 by 3 matrix.
	 * @param ret If not null where the results are written to.
	 * @return Se3_F64 transform.
	 */
	public static Se2_F64 toSe2( DMatrixRMaj H, Se2_F64 ret ) {
		if( H.numCols != 3 || H.numRows != 3 )
			throw new IllegalArgumentException( "The homogeneous matrix must be 3 by 3 by definition." );

		if( ret == null )
			ret = new Se2_F64();

		ret.setTranslation( (double) H.get( 0, 2 ), (double) H.get( 1, 2 ) );

		double c = (double) H.get( 0, 0 );
		double s = (double) H.get( 1, 0 );

		ret.setYaw( Math.atan2( s, c ) );

		return ret;
	}

	/**
	 * Sets the value of an {@link Se3_F64} using Euler XYZ coordinates for the rotation and
	 * a translation vector.
	 *
	 * @param rotX Rotation around X axis.
	 * @param rotY Rotation around Y axis.
	 * @param rotZ Rotation around Z axis.
	 * @param dx   Translation along x-axis.
	 * @param dy   Translation along y-axis.
	 * @param dz   Translation along z-axis.
	 * @param se   If not null then the transform is written here.
	 * @return The transform.
	 */
	public static Se3_F64 setEulerXYZ( double rotX, double rotY, double rotZ,
									   double dx, double dy, double dz,
									   Se3_F64 se ) {
		if( se == null )
			se = new Se3_F64();

		ConvertRotation3D_F64.eulerToMatrix(EulerType.XYZ, rotX, rotY, rotZ, se.getR() );
		Vector3D_F64 T = se.getT();
		T.x = dx;
		T.y = dy;
		T.z = dz;

		return se;
	}
}
