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

package georegression.fitting.line;

import georegression.struct.line.LinePolar2D_F32;
import georegression.struct.line.LinePolar2D_F64;
import georegression.struct.point.Point2D_I32;

import java.util.List;

/**
 * @author Peter Abeles
 */
public class FitLine_I32 {

	/**
	 * <p>
	 * Computes the unweighted best fit line to the set of points using the polar line equation. The solution
	 * is optimal in the Euclidean sense, see [1] for more details.
	 * </p>
	 *
	 * <p>
	 * [1] K. Arras, R. Siegwart, "Feature Extraction and Scene Interpretation for Map-Based Navigation
	 * and Map Building" Proc. SPIE, Mobile Robotics XIII, Vol. 3210, 1997
	 * </p>
	 *
	 * @param points Set of points on the line.
	 * @param ret Storage for the line.  If null a new line will be declared.
	 * @return Best fit line
	 */
	public static LinePolar2D_F32 polar(List<Point2D_I32> points , LinePolar2D_F32 ret ) {
		if( ret == null )
			ret = new LinePolar2D_F32();

		int sumX = 0;
		int sumY = 0;

		final int N = points.size();
		for( int i = 0; i < N; i++ ) {
			Point2D_I32 p = points.get(i);
			sumX += p.x;
			sumY += p.y;
		}
		float meanX = sumX / (float)N;
		float meanY = sumY / (float)N;

		float top = 0;
		float bottom = 0;

		for( int i = 0; i < N; i++ ) {
			Point2D_I32 p = points.get(i);
			float dx = meanX - p.x;
			float dy = meanY - p.y;

			top += dx*dy;
			bottom += dy*dy - dx*dx;
		}

		ret.angle = (float)Math.atan2(-2.0f*top , bottom)/2.0f;
		ret.distance = (float)( meanX*Math.cos(ret.angle) + meanY*Math.sin(ret.angle));

		return ret;
	}

	/**
	 * <p>
	 * Computes the unweighted best fit line to the set of points using the polar line equation. The solution
	 * is optimal in the Euclidean sense, see [1] for more details.
	 * </p>
	 *
	 * <p>
	 * [1] K. Arras, R. Siegwart, "Feature Extraction and Scene Interpretation for Map-Based Navigation
	 * and Map Building" Proc. SPIE, Mobile Robotics XIII, Vol. 3210, 1997
	 * </p>
	 *
	 * @param points Set of points on the line.
	 * @param ret Storage for the line.  If null a new line will be declared.
	 * @return Best fit line
	 */
	public static LinePolar2D_F64 polar(List<Point2D_I32> points , LinePolar2D_F64 ret ) {
		if( ret == null )
			ret = new LinePolar2D_F64();

		int sumX = 0;
		int sumY = 0;

		final int N = points.size();
		for( int i = 0; i < N; i++ ) {
			Point2D_I32 p = points.get(i);
			sumX += p.x;
			sumY += p.y;
		}
		double meanX = sumX / (double)N;
		double meanY = sumY / (double)N;

		double top = 0;
		double bottom = 0;

		for( int i = 0; i < N; i++ ) {
			Point2D_I32 p = points.get(i);
			double dx = meanX - p.x;
			double dy = meanY - p.y;

			top += dx*dy;
			bottom += dy*dy - dx*dx;
		}

		ret.angle = Math.atan2(-2.0*top , bottom)/2.0;
		ret.distance = ( meanX*Math.cos(ret.angle) + meanY*Math.sin(ret.angle));

		return ret;
	}
}
