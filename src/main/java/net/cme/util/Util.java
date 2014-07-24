package net.cme.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import net.cme.model.Vertex;

public class Util {
	public static FloatBuffer vertexToBuffer(Vertex[] vertex) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertex.length * Vertex.SIZE);
		for (Vertex v : vertex) {
			buffer.put(v.position.x);
			buffer.put(v.position.y);
			buffer.put(v.position.z);
		}

		buffer.flip();

		return buffer;
	}

	public static int[] integerListToIntArray(List<Integer> intList) {
		int[] arr = new int[intList.size()];

		int c = 0;
		for (Integer i : intList) {
			arr[c] = i;
			c++;
		}

		return arr;
	}

	public static IntBuffer intToBuffer(int[] ints) {
		IntBuffer buffer = BufferUtils.createIntBuffer(ints.length);

		for (int i : ints) {
			buffer.put(i);
		}

		buffer.flip();

		return buffer;
	}

	public static String[] removeEmptyStrings(String... strings) {
		List<String> nonempty = new ArrayList<String>();

		for (String s : strings) {
			if (!s.equals("")) {
				nonempty.add(s);
			}
		}

		return nonempty.toArray(new String[0]);
	}
}
