package net.cme.util;

public class Vector {

	public float x;

	public float y;

	public float z;

	public float length;
	
	public final int SIZE;
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		SIZE = 3;
		length = length();
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
		
		SIZE = 2;
		length = length();
	}

	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}

	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

	public Vector mult(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}

	public Vector mult(float f) {
		return new Vector(x * f, y * f, z * f);
	}

	public Vector div(Vector v) {
		return new Vector(x / v.x, y / v.y, z / v.z);
	}

	public float length() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public Vector normalize() {
		float m = length();
		return new Vector(x / m, y / m, z / m);
	}

	public Vector cross(Vector v) {
		float crossX = y * v.z - v.y * z;
		float crossY = z * v.x - v.z * x;
		float crossZ = x * v.y - v.x * y;
		return new Vector(crossX, crossY, crossZ).normalize();
	}
	
	public float dot(Vector v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public Vector invert() {
		return new Vector(z, -y, x);
	}

	public Vector negative() {
		return new Vector(-x, -y, -z);
	}
	
	public Vector rotate(float angle, Vector axis) {
		
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
		float rotationX = axis.x * sinHalfAngle;
		float rotationY = axis.y * sinHalfAngle;
		float rotationZ = axis.z * sinHalfAngle;
		
		float rotationW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rotationX, rotationY, rotationZ, rotationW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mult(this).mult(conjugate);
		
		x = w.x;
		y = w.y;
		z = w.z;
		
		return this; 
	}
}
