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
	public List<Vector3> vertexList;
	public List<Vector3> normalList;
	public List<Face> faceList;

	private Model() {
		vertexList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<Face>();
	}

	public static Model loadModel(String location) throws FileNotFoundException, IOException {
		long time = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("src/main/resources/models/" + location));

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

			if (token[1].equals("f")) {
				Vector3 vertexIndicies = new Vector3(Float.parseFloat(token[1].split("/")[0]), Float.parseFloat(token[2].split("/")[0]), Float.parseFloat(token[3].split("/")[0]));
				Vector3 normalIndicies = new Vector3(Float.parseFloat(token[1].split("/")[3]), Float.parseFloat(token[2].split("/")[3]), Float.parseFloat(token[3].split("/")[3]));

				m.faceList.add(new Face(vertexIndicies, normalIndicies));
			}
		}

		read.close();

		CMEngine.LOGGER.info(String.format("Took %dms to parse: %s", System.currentTimeMillis() - time, location));

		return new Model();
	}
}
