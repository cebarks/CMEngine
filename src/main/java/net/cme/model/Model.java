package net.cme.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;
import net.cme.util.Vector3;

import org.lwjgl.BufferUtils;

public class Model {

	public List<Vector3> vertexList, normalList;
	public List<Face> faceList;

	public Shader shader;
	public int vaoID, vboID, iboID;

	public Model() {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
	}

	public void render() {
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);

		glDrawArrays(GL_TRIANGLES, 0, vertexList.size() * 3);

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

			if (line.isEmpty() || token[0].equals("") || token.length == 0)
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

			if (token[1].equals("f")) {
				Vector3 vertexIndicies = new Vector3(
						Float.parseFloat(token[1].split("/")[0]),
						Float.parseFloat(token[2].split("/")[0]),
						Float.parseFloat(token[3].split("/")[0]));
				
				Vector3 normalIndicies = new Vector3(
						Float.parseFloat(token[1].split("/")[3]),
						Float.parseFloat(token[2].split("/")[3]),
						Float.parseFloat(token[3].split("/")[3]));

				m.faceList.add(faceIndex, new Face(vertexIndicies, normalIndicies));
				faceIndex++;
			}
		}

		read.close();

		CMEngine.LOGGER.info(String.format("Took %dms to parse: %s", System.currentTimeMillis() - time, location));

		return m;
	}

	public void generateModel(Shader shader) {
		this.shader = shader;
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexList.size() * 3);

		for (int i = 0; i < vertexList.size(); i++) {
			vertexBuffer.put(new float[] {
					vertexList.get(i).x, vertexList.get(i).y, vertexList.get(i).z 	
			});
		}

		vertexBuffer.flip();

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		//TODO This would be the thing that creates faces and sends them to the gpu, but i think the model loader doesnt get them properly because its supposed to be sent 2 at a time, and antens triangle only  has 3 face integers... Whatever.
		//TODO Also make the creation of buffers more modular, like in the benny box tutorials (these are notes to myself)
		
		//IntBuffer indexBuffer = BufferUtils.createIntBuffer(vertexList.size() * 3);

		//for (int i = 0; i < faceList.size(); i++) {
			//indexBuffer.put((int) faceList.get(i).verticies.x, (int) faceList.get(i).verticies.y);
		//}
		
		//indexBuffer.flip(); 
		
		//iboID = glGenBuffers();
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		//glBufferData(GL_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
}
