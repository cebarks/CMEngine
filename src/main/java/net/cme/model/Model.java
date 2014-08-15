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
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.cme.util.Util;
import net.cme.util.Vector3;

public class Model {

	public List<Vector3> vertexList, normalList;
	public List<Face> faceList;

	public Shader shader;
	public int vaoID, vboID, cboID, iboID;

	public Model(String location) {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
		
		BufferedReader meshReader = null;
		
		try {
			meshReader = new BufferedReader(new FileReader("src/main/resources/models/" + location));
			String line;
			
			while ((line = meshReader.readLine()) != null) {
				
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if (tokens.length == 0 || tokens[0].isEmpty() || tokens[0].equals("#"))
					continue;
				
				else if (tokens[0].equals("v")) {
					vertexList.add(new Vector3(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
					
				} else if (tokens[0].equals("vn")) {
					normalList.add(new Vector3(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
					
				} else if (tokens[0].equals("f")) {	
					for (int i = 0; i < tokens.length - 3; i++) {
						faceList.add(new Face(
								new Vector3(Float.parseFloat(tokens[1].split("/")[0]), Float.parseFloat((tokens[2] + i).split("/")[0]), Float.parseFloat((tokens[3] + i).split("/")[0])),
								new Vector3(Float.parseFloat(tokens[1].split("/")[2]), Float.parseFloat((tokens[2] + i).split("/")[2]), Float.parseFloat((tokens[3] + i).split("/")[2]))));
								System.out.println(Integer.parseInt(tokens[1].split("/")[0]) + " "  + Integer.parseInt((tokens[2] + i).split("/")[0]) + " " + Integer.parseInt((tokens[3] + i).split("/")[0]));
					}		
				}
			}
			
			meshReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
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

	public void bufferData() {
		List<Vector3> indexList = new ArrayList<Vector3>();
		for (Face f : faceList) {
			indexList.add(new Vector3(f.verticies.x, f.verticies.y, f.verticies.z));
		}

		FloatBuffer vertexBuffer = Util.createFlippedFloatBuffer(vertexList);
		IntBuffer indexBuffer = Util.createFlippedIntBuffer(indexList);

		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void destroy() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboID);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(iboID);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
}
