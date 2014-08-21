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
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;
import net.cme.shader.Shader;
import net.cme.util.ResourceLoader;
import net.cme.util.Util;
import net.cme.util.Vector;

public class Model {

	public List<Vector> verticies, uv, normals;
	public List<Face> faces;

	public Shader shader;
	public int vao, vbo, ibo;

	public Model(String location) {

		verticies = new ArrayList<Vector>();
		uv = new ArrayList<Vector>();
		normals = new ArrayList<Vector>();
		faces = new ArrayList<Face>();

		BufferedReader meshReader = null;

		try {
			meshReader = new BufferedReader(new FileReader(ResourceLoader.getResource("models/" + location)));
			String line;

			while ((line = meshReader.readLine()) != null) {

				String[] tokens = line.split("[ ]+");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].isEmpty() || tokens[0].equals("#"))
					continue;

				else if (tokens[0].equals("v")) {
					verticies.add(new Vector(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));

				} else if (tokens[0].equals("vt")) {
					uv.add(new Vector(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));

				} else if (tokens[0].equals("vn")) {
					normals.add(new Vector(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));

				} else if (tokens[0].equals("f")) {

					if (tokens[1].split("/").length == 1) {
						faces.add(new Face(new Vector(Float.parseFloat(tokens[1].split("/")[0]) - 1, Float.parseFloat((tokens[2]).split("/")[0]) - 1, Float.parseFloat((tokens[3]).split("/")[0]) - 1)));
					}

					if (tokens[1].split("/").length == 2) {
						faces.add(new Face(new Vector(Float.parseFloat(tokens[1].split("/")[0]) - 1, Float.parseFloat((tokens[2]).split("/")[0]) - 1, Float.parseFloat((tokens[3]).split("/")[0]) - 1), new Vector(Float.parseFloat(tokens[1].split("/")[1]), Float.parseFloat(tokens[2].split("/")[1]))));
					}

					if (tokens[1].split("/").length == 3) {

						if (tokens[1].split("/")[1].equals("")) {
							faces.add(new Face(new Vector(Float.parseFloat(tokens[1].split("/")[0]) - 1, Float.parseFloat((tokens[2]).split("/")[0]) - 1, Float.parseFloat((tokens[3]).split("/")[0]) - 1), new Vector(Float.parseFloat(tokens[1].split("/")[2]), Float.parseFloat((tokens[2]).split("/")[2]), Float.parseFloat((tokens[3]).split("/")[2]))));
						}

						else if (!tokens[1].split("/")[1].equals("")) {
							faces.add(new Face(new Vector(Float.parseFloat(tokens[1].split("/")[0]) - 1, Float.parseFloat((tokens[2]).split("/")[0]) - 1, Float.parseFloat((tokens[3]).split("/")[0]) - 1), new Vector(Float.parseFloat(tokens[1].split("/")[1]), Float.parseFloat(tokens[2].split("/")[1])), new Vector(Float.parseFloat(tokens[1].split("/")[2]), Float.parseFloat((tokens[2]).split("/")[2]), Float.parseFloat((tokens[3]).split("/")[2]))));
						}
					}
				}
			}

			meshReader.close();

		} catch (Exception e) {
			CMEngine.LOGGER.error("Could not load model");
			CMEngine.exitOnError(1, e);
		}
	}

	public void render() {
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, faces.size() * 8, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	public void bufferData() {

		List<Vector> data = new ArrayList<Vector>();
		for (Vector v : verticies)
			data.add(v);
		for (Vector v : uv)
			data.add(v);
		for (Vector v : normals)
			data.add(v);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedVertexBuffer(data), GL_STATIC_DRAW);

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 2 * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 3 * 4, 20);

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedFaceBuffer(faces), GL_STATIC_DRAW);
	}

	public void destroy() {

	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
}
