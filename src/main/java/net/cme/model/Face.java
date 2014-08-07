package net.cme.model;

import net.cme.util.Vector3;

public class Face {
	public Vector3 verticies;
	public Vector3 normal;

	public Face(Vector3 verticies, Vector3 normal) {
		this.verticies = verticies;
		this.normal = normal;
	}
}
