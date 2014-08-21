package net.cme.util;

public class Matrix4 {
	private float[][] matrix;

	public Matrix4() {
		setMatrix(new float[4][4]);
	}

	public Matrix4 initIdentity() {
		matrix[0][0] = 1;
		matrix[0][1] = 0;
		matrix[0][2] = 0;
		matrix[0][3] = 0;

		matrix[1][0] = 0;
		matrix[1][1] = 1;
		matrix[1][2] = 0;
		matrix[1][3] = 0;

		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = 1;
		matrix[2][3] = 0;

		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 0;
		matrix[3][3] = 1;

		return this;
	}

	public Matrix4 initTranslation(float x, float y, float z) {
		matrix[0][0] = 1;
		matrix[0][1] = 0;
		matrix[0][2] = 0;
		matrix[0][3] = x;

		matrix[1][0] = 0;
		matrix[1][1] = 1;
		matrix[1][2] = 0;
		matrix[1][3] = y;

		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = 1;
		matrix[2][3] = z;

		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 0;
		matrix[3][3] = 1;

		return this;
	}

	public Matrix4 initRotation(float x, float y, float z) {
		Matrix4 rx = new Matrix4();
		Matrix4 ry = new Matrix4();
		Matrix4 rz = new Matrix4();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		rz.matrix[0][0] = (float) Math.cos(z);
		rz.matrix[0][1] = -(float) Math.sin(z);
		rz.matrix[0][2] = 0;
		rz.matrix[0][3] = 0;

		rz.matrix[1][0] = (float) Math.sin(z);
		rz.matrix[1][1] = (float) Math.cos(z);
		rz.matrix[1][2] = 0;
		rz.matrix[1][3] = 0;

		rz.matrix[2][0] = 0;
		rz.matrix[2][1] = 0;
		rz.matrix[2][2] = 1;
		rz.matrix[2][3] = 0;

		rz.matrix[3][0] = 0;
		rz.matrix[3][1] = 0;
		rz.matrix[3][2] = 0;
		rz.matrix[3][3] = 1;

		rx.matrix[0][0] = 1;
		rx.matrix[0][1] = 0;
		rx.matrix[0][2] = 0;
		rx.matrix[0][3] = 0;

		rx.matrix[1][0] = 0;
		rx.matrix[1][1] = (float) Math.cos(x);
		rx.matrix[1][2] = -(float) Math.sin(x);
		rx.matrix[1][3] = 0;

		rx.matrix[2][0] = 0;
		rx.matrix[2][1] = (float) Math.sin(x);
		rx.matrix[2][2] = (float) Math.cos(x);
		rx.matrix[2][3] = 0;

		rx.matrix[3][0] = 0;
		rx.matrix[3][1] = 0;
		rx.matrix[3][2] = 0;
		rx.matrix[3][3] = 1;

		ry.matrix[0][0] = (float) Math.cos(y);
		ry.matrix[0][1] = 0;
		ry.matrix[0][2] = -(float) Math.sin(y);
		ry.matrix[0][3] = 0;

		ry.matrix[1][0] = 0;
		ry.matrix[1][1] = 1;
		ry.matrix[1][2] = 0;
		ry.matrix[1][3] = 0;

		ry.matrix[2][0] = (float) Math.sin(y);
		ry.matrix[2][1] = 0;
		ry.matrix[2][2] = (float) Math.cos(y);
		ry.matrix[2][3] = 0;

		ry.matrix[3][0] = 0;
		ry.matrix[3][1] = 0;
		ry.matrix[3][2] = 0;
		ry.matrix[3][3] = 1;

		matrix = rz.mult(ry.mult(rx)).getMatrix();

		return this;
	}

	public Matrix4 initScale(float x, float y, float z) {
		matrix[0][0] = x;
		matrix[0][1] = 0;
		matrix[0][2] = 0;
		matrix[0][3] = 0;
		matrix[1][0] = 0;
		matrix[1][1] = y;
		matrix[1][2] = 0;
		matrix[1][3] = 0;
		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = z;
		matrix[2][3] = 0;
		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 0;
		matrix[3][3] = 1;

		return this;
	}

	public Matrix4 initProjection(float zNear, float zFar, float width, float height, float fov) {

		float aspectRatio = width / height;
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
		float zRange = zNear - zFar;

		matrix[0][0] = 1.0f / (tanHalfFOV * aspectRatio);
		matrix[0][1] = 0;
		matrix[0][2] = 0;
		matrix[0][3] = 0;
		matrix[1][0] = 0;
		matrix[1][1] = 1.0f / tanHalfFOV;
		matrix[1][2] = 0;
		matrix[1][3] = 0;
		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = (-zNear - zFar) / zRange;
		matrix[2][3] = 2 * zFar * zNear / zRange;
		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 1;
		matrix[3][3] = 0;

		return this;
	}

	public Matrix4 initCamera(Vector forward, Vector up) {

		Vector f = forward.normalize();

		Vector r = up.normalize();

		r = r.cross(f);

		Vector u = f.cross(r);

		matrix[0][0] = r.x;
		matrix[0][1] = r.y;
		matrix[0][2] = r.z;
		matrix[0][3] = 0;
		matrix[1][0] = u.x;
		matrix[1][1] = u.y;
		matrix[1][2] = u.z;
		matrix[1][3] = 0;
		matrix[2][0] = f.x;
		matrix[2][1] = f.y;
		matrix[2][2] = f.z;
		matrix[2][3] = 0;
		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 0;
		matrix[3][3] = 1;

		return this;
	}

	public Matrix4 mult(Matrix4 r) {
		Matrix4 res = new Matrix4();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, matrix[i][0] * r.get(0, j) + matrix[i][1] * r.get(1, j) + matrix[i][2] * r.get(2, j) + matrix[i][3] * r.get(3, j));
			}
		}

		return res;
	}

	public float[][] getMatrix() {
		return matrix;
	}

	public float get(int x, int y) {
		return matrix[x][y];
	}

	public void setMatrix(float[][] matrix) {
		this.matrix = matrix;
	}

	public void set(int x, int y, float value) {
		matrix[x][y] = value;
	}
}
