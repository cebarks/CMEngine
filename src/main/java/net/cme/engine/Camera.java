package net.cme.engine;

import net.cme.util.Vector;

public class Camera {
	public static final Vector yAxis = new Vector(0, 1, 0);

	private Vector position, forward, up;

	public Camera(Vector position, Vector forward, Vector up) {
		this.position = position;
		this.forward = forward;
		this.up = up;
	}

	public void move(Vector direction, float speed) {
		position = position.add(direction.mult(speed));
	}

	public void rotateY(float angle) {
		Vector hAxis = yAxis.cross(forward);

		forward.rotate(angle, yAxis);
		forward.normalize();

		up = forward.cross(hAxis);
		up.normalize();
	}

	public void rotateX(float angle) {
		Vector hAxis = forward.cross(yAxis);

		forward.rotate(angle, hAxis);
		forward.normalize();

		up = forward.cross(hAxis);
		up.normalize();
	}

	public Vector getForward() {
		return forward;
	}

	public Vector getBackward() {
		return new Vector(-forward.x, -forward.y, -forward.z);
	}

	public Vector getUp() {
		return up;
	}

	public Vector getDown() {
		return new Vector(-up.x, -up.y, -up.z);
	}

	public Vector getLeft() {
		Vector left = forward.cross(up);
		left.normalize();

		return left;
	}

	public Vector getRight() {
		Vector right = up.cross(forward);
		right.normalize();

		return right;
	}

	public Vector getPosition() {
		return position;
	}
}
