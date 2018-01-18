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

package georegression.geometry;

import georegression.misc.GrlConstants;
import georegression.struct.EulerType;
import georegression.struct.plane.PlaneGeneral3D_F32;
import georegression.struct.plane.PlaneNormal3D_F32;
import georegression.struct.plane.PlaneTangent3D_F32;
import georegression.struct.point.Point2D_F32;
import georegression.struct.point.Point3D_F32;
import georegression.struct.point.Vector3D_F32;
import georegression.struct.se.Se3_F32;
import georegression.transform.se.SePointOps_F32;
import org.ejml.UtilEjml;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Peter Abeles
 */
public class TestUtilPlane3D_F32 {

	Random rand = new Random(234);

	@Test
	public void convert_norm_general() {
		PlaneNormal3D_F32 original = new PlaneNormal3D_F32();
		original.n.set(1,2,3);
		original.n.normalize();
		original.p.set(-2,3,5);

		PlaneGeneral3D_F32 test = UtilPlane3D_F32.convert(original,null);
		List<Point3D_F32> points = randPointOnPlane(original,10);

		for( Point3D_F32 p : points ) {
			float found = UtilPlane3D_F32.evaluate(test,p);
			assertEquals(0,found, GrlConstants.TEST_F32);
		}
	}

	@Test
	public void convert_general_norm() {
		PlaneGeneral3D_F32 general = new PlaneGeneral3D_F32(1,2,3,4);

		PlaneNormal3D_F32 foundPlane = UtilPlane3D_F32.convert(general,null);
		List<Point3D_F32> points = randPointOnPlane(foundPlane,10);

		for( Point3D_F32 p : points ) {
			float found = UtilPlane3D_F32.evaluate(general,p);
			assertEquals(0,found, GrlConstants.TEST_F32);
		}
	}

	@Test
	public void convert_tangent_norm() {
		PlaneNormal3D_F32 original = new PlaneNormal3D_F32();
		original.n.set(1,0,0);
		original.n.normalize();
		original.p.set(-2,3,5);

		// create a bunch of points which are on the original plane
		List<Point3D_F32> points = randPointOnPlane(original,10);

		// now manually construct the plane in tangent form
		PlaneTangent3D_F32 tangent = new PlaneTangent3D_F32(-2,0,0);

		// convert this back into normal form
		PlaneNormal3D_F32 conv = UtilPlane3D_F32.convert(tangent,(PlaneNormal3D_F32)null);

		// the points should still be on the plane
		for( Point3D_F32 p : points ) {
			float found = UtilPlane3D_F32.evaluate(conv,p);
			assertEquals(0,found, GrlConstants.TEST_F32);
		}
	}

	@Test
	public void convert_se3_plane() {
		Se3_F32 p2w = new Se3_F32();

		convert_se3_plane(p2w);
		p2w.T.set(2,6,-3);
		convert_se3_plane(p2w);
		ConvertRotation3D_F32.eulerToMatrix(EulerType.XYZ,0.2f,3,-4.5f,p2w.R);
		convert_se3_plane(p2w);
	}

	private void convert_se3_plane( Se3_F32 p2w ){

		List<Point3D_F32> points = new ArrayList<>();
		points.add(new Point3D_F32(0,0,0));
		points.add(new Point3D_F32(10,0,0));
		points.add(new Point3D_F32(0,1,0));
		points.add(new Point3D_F32(-0.2f,0,0));
		points.add(new Point3D_F32(-3,2,0));
		points.add(new Point3D_F32(6,1000,0));

		// if the extracted plane is correct then all these points should be on it
		PlaneNormal3D_F32 plane = UtilPlane3D_F32.convert(p2w,null);
		for( Point3D_F32 p : points ) {
			SePointOps_F32.transform(p2w,p,p);
			assertEquals(0,UtilPlane3D_F32.evaluate(plane,p) , GrlConstants.TEST_F32);
		}
	}

	@Test
	public void hessianNormalForm() {
		PlaneGeneral3D_F32 a = new PlaneGeneral3D_F32(2,-3,4,5);
		float n = (float)Math.sqrt(2*2 + 3*3 + 4*4);

		UtilPlane3D_F32.hessianNormalForm(a);

		assertEquals(2/n,a.A, GrlConstants.TEST_F32);
		assertEquals(-3/n,a.B, GrlConstants.TEST_F32);
		assertEquals(4/n,a.C, GrlConstants.TEST_F32);
		assertEquals(5/n,a.D, GrlConstants.TEST_F32);
	}

	@Test
	public void evaluate_general() {
		PlaneNormal3D_F32 original = new PlaneNormal3D_F32();
		original.n.set(1,2,3);
		original.n.normalize();
		original.p.set(-2,3,5);

		PlaneGeneral3D_F32 test = UtilPlane3D_F32.convert(original,null);

		List<Point3D_F32> points = randPointOnPlane(original,10);

		for( Point3D_F32 p : points ) {
			float found = UtilPlane3D_F32.evaluate(test,p);
			assertEquals(0,found, GrlConstants.TEST_F32);
		}
	}

	@Test
	public void evaluate_normal() {
		PlaneNormal3D_F32 input = new PlaneNormal3D_F32();
		input.n.set(1,2,3);
		input.n.normalize();
		input.p.set(-2,3,5);

		List<Point3D_F32> points = randPointOnPlane(input, 10);

		for( Point3D_F32 p : points ) {
			float found = UtilPlane3D_F32.evaluate(input,p);
			assertEquals(0,found, GrlConstants.TEST_F32);
		}
	}

	@Test
	public void equals_planeNorm() {

		for( int i = 0; i < 100; i++ ) {
			PlaneNormal3D_F32 a = new PlaneNormal3D_F32(
					(float)(float)rand.nextGaussian(),(float)(float)rand.nextGaussian(),(float)(float)rand.nextGaussian(),
					(float)(float)rand.nextGaussian(),(float)(float)rand.nextGaussian(),(float)(float)rand.nextGaussian());
			PlaneNormal3D_F32 b = new PlaneNormal3D_F32(a);

			b.p.x +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;
			b.p.y +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;
			b.p.z +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;
			b.n.x +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;
			b.n.y +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;
			b.n.z +=(rand.nextFloat()-0.5f)*GrlConstants.TEST_F32;

			// change scaling
			float scale = (float)rand.nextGaussian()*2;
			b.n.x *= scale;
			b.n.y *= scale;
			b.n.z *= scale;

			assertTrue(UtilPlane3D_F32.equals(a, b, GrlConstants.TEST_F32 *50));

			b.p.x +=(rand.nextFloat()-0.5f);
			b.p.y +=(rand.nextFloat()-0.5f);
			b.p.z +=(rand.nextFloat()-0.5f);
			b.n.x +=(rand.nextFloat()-0.5f);
			b.n.y +=(rand.nextFloat()-0.5f);
			b.n.z +=(rand.nextFloat()-0.5f);

			assertFalse(UtilPlane3D_F32.equals(a, b, GrlConstants.TEST_F32 *50));
		}
	}

	/**
	 * Randomly generate points on a plane by randomly selecting two vectors on the plane using cross products
	 */
	private List<Point3D_F32> randPointOnPlane( PlaneNormal3D_F32 plane , int N ) {
		Vector3D_F32 v = new Vector3D_F32(-2,0,1);
		Vector3D_F32 a = UtilTrig_F32.cross(plane.n,v);
		a.normalize();
		Vector3D_F32 b = UtilTrig_F32.cross(plane.n,a);
		b.normalize();

		List<Point3D_F32> ret = new ArrayList<Point3D_F32>();

		for( int i = 0; i < N; i++ ) {
			float v0 = (float)rand.nextGaussian();
			float v1 = (float)rand.nextGaussian();

			Point3D_F32 p = new Point3D_F32();
			p.x = plane.p.x + v0*a.x + v1*b.x;
			p.y = plane.p.y + v0*a.y + v1*b.y;
			p.z = plane.p.z + v0*a.z + v1*b.z;

			ret.add(p);
		}

		return ret;
	}

	/**
	 * Tests the planeToWorld function by randomly generating 2D points and converting them to 3D.  Then it
	 * tests to see if the points lie on the plane.
	 */
	@Test
	public void planeToWorld() {
		checkPlaneToWorld(new PlaneNormal3D_F32(1,2,3,0,0,1));
		checkPlaneToWorld(new PlaneNormal3D_F32(1,2,3,0,1,0));
		checkPlaneToWorld(new PlaneNormal3D_F32(1,2,3,1,0,0));
		checkPlaneToWorld(new PlaneNormal3D_F32(1,2,3,-2,-4,0.5f));
	}

	private void checkPlaneToWorld(PlaneNormal3D_F32 planeN) {

		planeN.getN().normalize();
		PlaneGeneral3D_F32 planeG = UtilPlane3D_F32.convert(planeN, null);

		List<Point2D_F32> points2D = UtilPoint2D_F32.random(-2, 2, 100, rand);

		Se3_F32 planeToWorld = UtilPlane3D_F32.planeToWorld(planeG,null);
		Point3D_F32 p3 = new Point3D_F32();
		Point3D_F32 l3 = new Point3D_F32();
		Point3D_F32 k3 = new Point3D_F32();
		for( Point2D_F32 p : points2D ) {
			p3.set(p.x,p.y,0);
			SePointOps_F32.transform(planeToWorld, p3, l3);

			// see if it created a valid transform
			SePointOps_F32.transformReverse(planeToWorld,l3,k3);
			assertEquals(0,k3.distance(p3), GrlConstants.TEST_F32);

			assertEquals(0,UtilPlane3D_F32.evaluate(planeG,l3), GrlConstants.TEST_F32);
		}
	}

	@Test
	public void selectAxis2D() {

		Vector3D_F32 z = new Vector3D_F32(-1.2f,5.6f,9.9f);
		Vector3D_F32 x = new Vector3D_F32();
		Vector3D_F32 y = new Vector3D_F32();

		UtilPlane3D_F32.selectAxis2D(z,x,y);

		assertEquals(0,z.dot(x), UtilEjml.TEST_F32);
		assertEquals(0,z.dot(y), UtilEjml.TEST_F32);
		assertEquals(0,x.dot(y), UtilEjml.TEST_F32);

		assertEquals( 1 , x.norm(), UtilEjml.TEST_F32);
		assertEquals( 1 , y.norm(), UtilEjml.TEST_F32);

		Vector3D_F32 found = new Vector3D_F32();
		found.cross(x,y);

		assertTrue(found.dot(z) > 0 ); // make sure it's right handed
	}

	@Test
	public void point3Dto2D_point2Dto3D() {

		Point3D_F32 c = new Point3D_F32(5.1f,-3.1f,3);
		Vector3D_F32 z = new Vector3D_F32(-1.2f,5.6f,9.9f);

		Vector3D_F32 x = new Vector3D_F32();
		Vector3D_F32 y = new Vector3D_F32();

		Point3D_F32 p3 = new Point3D_F32();
		Point2D_F32 p2 = new Point2D_F32();

		UtilPlane3D_F32.selectAxis2D(z,x,y);

		// pick a point on the plane
		p3.x = c.x + x.x;
		p3.y = c.y + x.y;
		p3.z = c.z + x.z;

		UtilPlane3D_F32.point3Dto2D(c,x,y,p3,p2);

		Point3D_F32 found = new Point3D_F32();
		UtilPlane3D_F32.point2Dto3D(c,x,y,p2,found);

		assertTrue(found.distance(p3) <= UtilEjml.TEST_F32);
	}
}
