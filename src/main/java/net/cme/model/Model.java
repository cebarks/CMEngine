package net.cme.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;
import net.cme.util.Util;
import net.cme.util.Vector3;

public class Model {
	private List<Vector3> vertexList;
	private List<Vector3> normalList;
	private List<Face> faceList;

	private Model() {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
	}

<<<<<<< HEAD
	public void render() {
		if(shader != null) {
			shader.bind();
		}
		
		glEnableVertexAttribArray(0);
=======
	public static Model loadModel(String location) throws FileNotFoundException, IOException {
		long time = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("src/main/resources/models/" + location));
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083

		Model m = new Model();

		String line;
		while ((line = read.readLine()) != null) {
			String[] token = line.split(" ");
			Util.removeEmptyStrings(token);
			if (line.isEmpty() || token[0].equals("") || token.length == 0)
				continue;

			if (token[0].equals("v")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.vertexList.add(new Vector3(x, y, z));
			}

			if (token[1].equals("vn")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.normalList.add(new Vector3(x, y, z));
			}
		}
<<<<<<< HEAD
	}

	public void bindShader(Shader shader) {
		this.shader = shader;
	}

	private void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}
=======
		CMEngine.LOGGER.info(String.format("Took %dms to parse: %s", System.currentTimeMillis() - time, location));
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083

		return new Model();
	}
}
