package net.cme.engine;

import net.cme.mesh.Vertex;

public class Camera {
	public void create(Engine engine, Vertex position, Vertex rotation, float fov, float zNear, float zFar) {
		engine.addCamera(this);
	}
	
	public void render() {
		
	}

	public void destroy() {
		//TODO Delete all shaders, textures, VBO's
	}
}
