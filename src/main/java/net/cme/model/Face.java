package net.cme.model;

import net.cme.util.Vector;

public class Face {
	public Vector verticies, uv, normal;
	
	public Face(Vector vector) {
		this.verticies = vector;
		this.uv = new Vector(1, 1);
		this.normal = new Vector(0, 0, 1);
	}

	public Face(Vector vector1, Vector vector2) {	
		if(vector1.SIZE == 3 && vector2.SIZE == 3) {
			this.verticies = vector1;
			this.normal = vector2;
			this.uv = new Vector(1, 1);
		} else if(vector1.SIZE == 3 && vector2.SIZE == 2) {
			this.verticies = vector1;
			this.uv = vector2;
			this.normal = new Vector(0, 0, 1);
		}
	}
	
	public Face(Vector verticies, Vector uv, Vector normal) {
		this.verticies = verticies;
		this.uv = uv;
		this.normal = normal;
	}
}
