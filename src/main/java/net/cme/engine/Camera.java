package net.cme.engine;

import net.cme.util.Vector3;

public class Camera {
	public static final Vector3 yAxis = new Vector3(0, 1, 0);
	
	private Vector3 position, forward, up;
	
	public Camera(Vector3 position, Vector3 forward, Vector3 up) {
		this.position = position;
		this.forward = forward;
		this.up = up;
	}
	
	public void move(Vector3 direction, float speed) {
		position = position.add(direction.mult(speed));
	}
	
	public void rotateY(float angle) {
		Vector3 hAxis = yAxis.cross(forward);
		hAxis.normalize();
		
		forward.rotate(angle, yAxis);
		forward.normalize();
		
		up = forward.cross(hAxis);
		up.normalize();
	}
	
	public void rotateX(float angle) {
		Vector3 hAxis = yAxis.cross(forward);
		hAxis.normalize();
		
		forward.rotate(-angle, hAxis);
		forward.normalize();
		
		up = forward.cross(hAxis);
		up.normalize();
	}
	
	public Vector3 getForward() {
		return forward;
	}
	
	public Vector3 getBackward() {
		return new Vector3(-forward.x, -forward.y, -forward.z);
	}
	
	public Vector3 getUp() {
		return up;
	}
	
	public Vector3 getDown() {
		return new Vector3(-up.x, -up.y, -up.z);
	}
	
	public Vector3 getLeft() {
		Vector3 left = forward.cross(up);
		left.normalize();
		
		return left;
	}
	
	public Vector3 getRight() {
		Vector3 right = up.cross(forward);
		right.normalize();
		
		return right;
	}
	
	public Vector3 getPosition() {
		return position;
	}
}
