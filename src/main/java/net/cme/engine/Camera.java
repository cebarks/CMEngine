package net.cme.engine;

import net.cme.vec.Vector3;

public class Camera {
	public Camera(Engine engine, Vector3 position, Vector3 rotation, float fov, float zNear, float zFar) {
		engine.addCamera(this);
	}
	
	public void render() {
		
	}

	public void destroy() {
		//TODO Destroys opengl
	}
}
