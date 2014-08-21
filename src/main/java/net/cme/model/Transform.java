package net.cme.model;

import net.cme.engine.Camera;
import net.cme.util.Matrix4;
import net.cme.util.Vector;

public class Transform {
	private Camera camera;
	private float zNear, zFar, width, height, fov;
	public Vector translation, rotation, scale;
	
	public Transform() {
		translation = new Vector(0, 0, 0);
		rotation = new Vector(0, 0, 0);
		scale = new Vector(1, 1, 1);
	}
	
	public Matrix4 getTransformation() {
		Matrix4 translationMatrix = new Matrix4().initTranslation(translation.x, translation.y, translation.z);
		Matrix4 rotationMatrix = new Matrix4().initRotation(rotation.x, rotation.y, rotation.z);
		Matrix4 scaleMatrix = new Matrix4().initScale(scale.x, scale.y, scale.z);
		
		return translationMatrix.mult(rotationMatrix.mult(scaleMatrix));
	}
	
	public Matrix4 getProjectedTransformation() {
		Matrix4 transformationMatrix = getTransformation();
		Matrix4 projectionMatrix = new Matrix4().initProjection(zNear, zFar, width, height, fov);
		Matrix4 cameraRotation = new Matrix4().initCamera(camera.getForward(), camera.getUp());
		Matrix4 cameraTranslation = new Matrix4().initTranslation(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
		
		return projectionMatrix.mult(cameraRotation.mult(cameraTranslation.mult(transformationMatrix)));
	}
	
	public void setProjection(float zNear, float zFar, float width, float height, float fov) {
		this.zNear = zNear;
		this.zFar = zFar;
		this.width = width;
		this.height = height;
		this.fov = fov;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
