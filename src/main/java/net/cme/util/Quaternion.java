package net.cme.util;

public class Quaternion {
	public float x, y, z, w;

	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quaternion normalized() {
		float length = length();
		return new Quaternion(x / length, y / length, z / length, w / length);
	}

	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}

	public Quaternion mul(Quaternion r) {
		float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
		float x_ = x * r.w + w * r.x + y * r.z - z * r.y;
		float y_ = y * r.w + w * r.y + z * r.x - x * r.z;
		float z_ = z * r.w + w * r.z + x * r.y - y * r.x;

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion mul(Vector3 r) {
		float w_ = -x * r.x - y * r.y - z * r.z;
		float x_ = w * r.x + y * r.z - z * r.y;
		float y_ = w * r.y + z * r.x - x * r.z;
		float z_ = w * r.z + x * r.y - y * r.x;

		return new Quaternion(x_, y_, z_, w_);
	}
}
