package net.cme.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;
import net.cme.model.Face;

import org.lwjgl.BufferUtils;

public class Util {
	
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++)
			if (!data[i].equals(""))
				result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}

	public static String loadShaderSource(String location) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;

		try {
			shaderReader = new BufferedReader(new FileReader("src/main/resources/shaders/" + location));
			String line;

			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}

			shaderReader.close();
		} catch (Exception e) {
			CMEngine.exitOnError(1, e);
		}
		return shaderSource.toString();
	}

	public static FloatBuffer createFlippedVertexBuffer(List<Vector> data) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.size() * 3);

		for(Vector v : data) {
			if(v.SIZE == 3)
				floatBuffer.put(new float[] {v.x, v.y, v.z});
			else
				floatBuffer.put(new float[] {v.x, v.y});
		}

		floatBuffer.flip();
		
		return floatBuffer;
	}
	
	public static IntBuffer createFlippedFaceBuffer(List<Face> data) {
		IntBuffer intBuffer = BufferUtils.createIntBuffer(data.size() * 8);

		for(Face f : data) intBuffer.put(new int[] {(int) f.verticies.x, (int) f.verticies.y, (int) f.verticies.z});
		for(Face f : data) intBuffer.put(new int[] {(int) f.uv.x, (int) f.uv.y});
		for(Face f : data) intBuffer.put(new int[] {(int) f.normal.x, (int) f.normal.y, (int) f.normal.z});

		intBuffer.flip();
		
		
		
		return intBuffer;
	}
	
	public static FloatBuffer createFlippedMatrixBuffer(Matrix4 value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int u = 0; u < 4; u++)
				buffer.put(value.get(i, u));
		
		buffer.flip();
		
		return buffer;
	}
}
