package net.cme.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cme.engine.CMEngine;
import net.cme.util.Vector3;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

public class Model {
	private static Map<String, Model> models = new HashMap<String, Model>();

	public List<Vector3> vertexList;
	public List<Vector3> normalList;
	public List<Face> faceList;

	public Transform transform;
	public Shader shader;
	public int vaoID, vboID;

	public Model() {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
	}

	float test = 0;
	public void render() {
		
		test += 0.01f;
		
		shader.setUniformFloat("uniformFloat", (float) Math.sin(test));
		
		shader.bind();

		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);

		glDrawArrays(GL_TRIANGLES, 0, vertexList.size() * 3);

		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	public static Model getModel(String location) {
		if (!models.containsKey(location))
			try {
				models.put(location, loadModel(location));
			} catch (FileNotFoundException e) {
				CMEngine.LOGGER.error("Model does not exist: " + location, e);
			} catch (IOException e) {
				CMEngine.LOGGER.error("Enable to parse model: " + location, e);
			}

		return models.get(location);
	}

	public static Model loadModel(String location) throws FileNotFoundException, IOException {
		long time = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("src/main/resources/models/" + location));

		Model m = new Model();

		String line;
		while ((line = read.readLine()) != null) {
			String[] token = line.split(" ");

			if (line.isEmpty() || token[0].equals("") || token.length == 0)
				continue;

			if (token[0].equals("v")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.vertexList.add(new Vector3(x, y, z));
			}

			if (token[0].equals("vn")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.normalList.add(new Vector3(x, y, z));
			}

			if (token[1].equals("f")) {
				Vector3 vertexIndicies = new Vector3(Float.parseFloat(token[1].split("/")[0]), Float.parseFloat(token[2].split("/")[0]), Float.parseFloat(token[3].split("/")[0]));
				Vector3 normalIndicies = new Vector3(Float.parseFloat(token[1].split("/")[3]), Float.parseFloat(token[2].split("/")[3]), Float.parseFloat(token[3].split("/")[3]));

				m.faceList.add(new Face(vertexIndicies, normalIndicies));
			}
		}

		read.close();

		CMEngine.LOGGER.info(String.format("Took %dms to parse: %s", System.currentTimeMillis() - time, location));

		return m;
	}

	public void generateModel(Shader shader) {
		this.shader = shader;
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexList.size() * 3);
		
		for (Vector3 vertex : vertexList) {
			vertexBuffer.put(new float[] { vertex.x, vertex.y, vertex.z });
		}
		
		vertexBuffer.flip();

		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindVertexArray(0);
	}
}
