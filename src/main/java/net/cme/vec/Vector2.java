package net.cme.vec;

public class Vector2 {
	
	public float x;
	
	public float y;

	public float length;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
		length = length();
	}

	public Vector2 add(Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}

	public Vector2 sub(Vector2 v) {
		return new Vector2(x - v.x, y - v.y);
	}

	public Vector2 mult(Vector2 v) {
		return new Vector2(x * v.x, y * v.y);
	}

	public Vector2 div(Vector2 v) {
		return new Vector2(x / v.x, y / v.y);
	}

	public float length() {
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public Vector2 normalize() {
		float m = magnitude();
		return new Vector2(x / m, y / m);
	}

	public float magnitude() {
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public Vector2 invert() {
		return new Vector2(y, x);
	}

	public Vector2 negative() {
		return new Vector2(-x, -y);
	}
}
