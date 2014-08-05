package net.cme.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

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
	
	public static FloatBuffer createFlippedFloatBuffer(Matrix4 value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int u = 0; u < 4; u++)
				buffer.put(value.get(i, u));
		
		buffer.flip();
		
		return buffer;
	}
}
