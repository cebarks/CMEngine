package net.cme.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.cme.engine.CMEngine;
import net.cme.util.Util;
import net.cme.util.Vector3;

public class Model {
	private List<Vertex> vertices = new ArrayList<Vertex>();
	private List<Integer> indicies = new ArrayList<Integer>();

	private int vbo;
	private int ibo;
	private int size;

	public Model(String location) {
		try {
			parseObj(new FileInputStream(new File("src/main/resources/" + location)));
		} catch (FileNotFoundException e) {
			CMEngine.LOGGER.error("Couldn't load model: " + location, e);
		}

		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = indicies.size();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.vertexToBuffer(vertices.toArray(new Vertex[] {})), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.intToBuffer(Util.integerListToIntArray(indicies)), GL_STATIC_DRAW);
	}

	public void render() {
		glEnableVertexAttribArray(0);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, Vertex.SIZE, GL_FLOAT, false, Vertex.SIZE * 4, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
	}

	public void parseObj(InputStream stream) {
		try {
			Scanner scan = new Scanner(stream);

			String line;
			while ((line = scan.nextLine()) != null) {
				if (line.startsWith("#"))
					continue;

				if (line.startsWith("v ")) {
					String[] raw = line.substring(2).split(" ");
					Vertex vertex = new Vertex(new Vector3(Float.valueOf(raw[0]), Float.valueOf(raw[1]), Float.valueOf(raw[2])));
					addVertex(vertex);
					System.out.printf("v %f %f %f\n", vertex.position.x, vertex.position.y, vertex.position.z);
				}

				if (line.startsWith("f ")) {
					String[] raw = line.substring(2).split(" ");
					for (String s : raw) {
						addIndex(Integer.parseInt(s));
					}
					System.out.printf("f %s %s %s\n", raw[0], raw[1], raw[2]);
				}
			}
			scan.close();
		} catch (Exception e) {
			// CMEngine.LOGGER.error("Error parsing OBJ file.", e);
		}
	}

	private void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}

	private void addIndex(int parseInt) {
		indicies.add(parseInt);
	}
}
