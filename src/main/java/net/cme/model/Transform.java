package net.cme.model;

import net.cme.util.Matrix4;
import net.cme.util.Vector3;

public class Transform {
	public Vector3 translation, rotation, scale;
	
	public Transform() {
		translation = new Vector3(0, 0, 0);
		rotation = new Vector3(0, 0, 0);
		scale = new Vector3(1, 1, 1);
	}
	
	public Matrix4 getTransformation() {
		Matrix4 translationMatrix = new Matrix4().initTranslation(translation.x, translation.y, translation.z);
		Matrix4 rotationMatrix = new Matrix4().initRotation(rotation.x, rotation.y, rotation.z);
		Matrix4 scaleMatrix = new Matrix4().initScale(scale.x, scale.y, scale.z);
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public void translate() {
		
	}
	
	public void rotation() {
		
	}
	
	public void scale() {
		
	}
}
