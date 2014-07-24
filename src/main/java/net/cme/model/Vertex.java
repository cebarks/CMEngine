package net.cme.model;

import net.cme.util.Vector2;
import net.cme.util.Vector3;

public class Vertex {
	public Vector3 position, normal, tangent;
	public Vector2 textureCoordinate;
	
	public static final int SIZE = 11;
	
	public Vertex(Vector3 position, Vector3 normal, Vector3 tangent, Vector2 textureCoordinate) {
		this.position = position;
		this.normal = normal;
		this.tangent = tangent;
		this.textureCoordinate = textureCoordinate;
	}
}
