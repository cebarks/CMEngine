package net.cme.mesh;

public class Vertex {
	public float x, y, z, w;
	public final int SIZE;
	
	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		SIZE = 3;
	}
	
	public Vertex(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		SIZE = 4;
	}
}
