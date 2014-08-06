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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;
import net.cme.util.Vector3;

import org.lwjgl.BufferUtils;

public class Model {

	public List<Vector3> vertexList, normalList;
	public List<Face> faceList;

	public Shader shader;
	public int vaoID, vboID, cboID, iboID;

	public Model() {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
	}

	public void render() { 
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glDrawElements(GL_TRIANGLES, faceList.size() * 3, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0); 
		glBindVertexArray(0);
	}

	public static Model loadModel(String location) throws FileNotFoundException, IOException {
		long time = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("src/main/resources/models/" + location));
		Model m = new Model();
		
		int vertexIndex = 0, normalIndex = 0, faceIndex = 0;

		String line;
		while ((line = read.readLine()) != null) {
			String[] token = line.split("[ ]+");

			if (line.isEmpty() || token.length == 0)
				continue;
			
			if (token[0].equals("v")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.vertexList.add(vertexIndex, new Vector3(x, y, z));
				vertexIndex++;
			}

			if (token[0].equals("vn")) {
				float x = Float.parseFloat(token[1]);
				float y = Float.parseFloat(token[2]);
				float z = Float.parseFloat(token[3]);
				m.normalList.add(normalIndex, new Vector3(x, y, z));
				normalIndex++;
			}

			if (token[0].equals("f")) {
				Vector3 vertexIndicies = new Vector3(
						Float.parseFloat(token[1].split("/")[0]),
						Float.parseFloat(token[2].split("/")[0]),
						Float.parseFloat(token[3].split("/")[0]));
				
				if(token[1].contains("/") || token[2].contains("/") || token[3].contains("/") ) {
					Vector3 normalIndicies = new Vector3(
							Float.parseFloat(token[1].split("/")[2]),
							Float.parseFloat(token[2].split("/")[2]),
							Float.parseFloat(token[3].split("/")[2]));
					m.faceList.add(faceIndex, new Face(vertexIndicies, normalIndicies));
				}else {
					m.faceList.add(faceIndex, new Face(vertexIndicies, new Vector3(0, 0, 0)));
				}
				faceIndex++;
			}
		}

		read.close();

		CMEngine.LOGGER.info(String.format("Took %dms to parse: %s", System.currentTimeMillis() - time, location));

		return m;
	}

	public void generateModel(Shader shader) {
		this.shader = shader;

		List<Vector3> indexList = new ArrayList<Vector3>();
		for(Face f : faceList) {
			indexList.add(new Vector3(f.verticies.x, f.verticies.y, f.verticies.z));
		}
		
		FloatBuffer vertexBuffer = createFlippedFloatBuffer(vertexList);
		IntBuffer indexBuffer = createFlippedIntBuffer(indexList);
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		//This creates indicies (VERY GLITCHY)
		
		iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}
	
	private FloatBuffer createFlippedFloatBuffer(List<Vector3> data) {
		FloatBuffer floatBuffer = createFloatBuffer(data.size() * 3);
		
		for (int i = 0; i < data.size(); i++) {
			floatBuffer.put(new float[] { data.get(i).x, data.get(i).y, data.get(i).z });
		}
		floatBuffer.flip();
		
		return floatBuffer;
	}
	
	private IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}
	
	private IntBuffer createFlippedIntBuffer(List<Vector3> data) {
		IntBuffer intBuffer = createIntBuffer(data.size() * 3);
		
		for (int i = 0; i < data.size(); i++) {
			intBuffer.put(new int[] { (int) data.get(i).x, (int) data.get(i).y, (int) data.get(i).z });
		}
		intBuffer.flip();
		
		return intBuffer;
	}
}
