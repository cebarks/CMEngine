package net.cme.util;

public class Vector3 {

	public float x;

	public float y;

	public float z;

	public float length;
	
	public static final int SIZE = 3;

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		length = length();
	}

	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, z + v.z);
	}

	public Vector3 sub(Vector3 v) {
		return new Vector3(x - v.x, y - v.y, z - v.z);
	}

	public Vector3 mult(Vector3 v) {
		return new Vector3(x * v.x, y * v.y, z * v.z);
	}

	public Vector3 div(Vector3 v) {
		return new Vector3(x / v.x, y / v.y, z / v.z);
	}

	public float length() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public Vector3 normalize() {
		float m = magnitude();
		return new Vector3(x / m, y / m, z / m);
	}

	public float magnitude() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public Vector3 cross(Vector3 v) {
		float crossX = y * v.z - v.y * z;
		float crossY = z * v.x - v.z * x;
		float crossZ = x * v.y - v.x * y;
		return new Vector3(crossX, crossY, crossZ);
	}

	public Vector3 invert() {
		return new Vector3(z, -y, x);
	}

	public Vector3 negative() {
		return new Vector3(-x, -y, -z);
	}
}
