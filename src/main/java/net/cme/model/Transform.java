package net.cme.model;

import net.cme.util.Matrix4;
import net.cme.util.Vector3;

public class Transform {
	private float zNear, zFar, width, height, fov;
	public Vector3 translation, rotation, scale;
	
	public Transform() {
		translation = new Vector3(0, 0, 0);
		rotation = new Vector3(0, 0, 0);
		scale = new Vector3(0, 0, 0);
	}
	
	public Matrix4 getTransformation() {
		Matrix4 translationMatrix = new Matrix4().initTranslation(translation.x, translation.y, translation.z);
		Matrix4 rotationMatrix = new Matrix4().initRotation(rotation.x, rotation.y, rotation.z);
		Matrix4 scaleMatrix = new Matrix4().initScale(scale.x, scale.y, scale.z);
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public Matrix4 getProjectedTransformation() {
		Matrix4 transformationMatrix = getTransformation();
		Matrix4 projectionMatrix = new Matrix4().initProjection(zNear, zFar, width, height, fov);
		
		return projectionMatrix.mul(transformationMatrix);
	}
	
	public void setProjection(float zNear, float zFar, float width, float height, float fov) {
		this.zNear = zNear;
		this.zFar = zFar;
		this.width = width;
		this.height = height;
		this.fov = fov;
	}
}
