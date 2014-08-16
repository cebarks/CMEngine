package net.cme.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.cme.engine.CMEngine;

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
			shaderReader = new BufferedReader(new FileReader("src/main/resources/" + location));
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

	public static FloatBuffer createFlippedFloatBuffer(List<Vector3> data) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.size() * 3);

		for(int i = 0; i < data.size(); i ++) {
			floatBuffer.put(new float[] {data.get(i).x, data.get(i).y, data.get(i).z});
		}

		floatBuffer.flip();
		
		return floatBuffer;
	}

	public static IntBuffer createFlippedIntBuffer(List<Vector3> data) {
		IntBuffer intBuffer = BufferUtils.createIntBuffer(data.size() * 3);

		for(int i = 0; i < data.size(); i ++) {
			intBuffer.put(new int[] {(int) data.get(i).x, (int) data.get(i).y, (int) data.get(i).z});
		}

		intBuffer.flip();
		
		return intBuffer;
	}
}
