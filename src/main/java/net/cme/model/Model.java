package net.cme.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;
import java.util.List;

public class Model {
	private List<Vertex> unbakedVertices = new ArrayList<Vertex>();
	private int vbo;
	private int size;
	
	public Model(String location) {
		//Load vertices here
		vbo = glGenBuffers();
		//for blah blah blah create vao
	}
	
	public void render() {
		
	}
	
	public void addVertex(Vertex vertex) {
		unbakedVertices.add(vertex);
	}
}