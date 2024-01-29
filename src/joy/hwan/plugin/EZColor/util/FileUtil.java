package joy.hwan.plugin.EZColor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;

public class FileUtil<K, T> {
	final private File file;
	final private HashMap<K, T> map;

	public FileUtil(File file, HashMap<K, T> map) {
		this.file = file;
		this.map = map;
	}

	public void makeFile() {
		if (!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
	}

	public void mapToFile() {
		try {
			FileWriter writer = new FileWriter(file, false);

			for (K uuid : map.keySet()) {
				writer.write(uuid.toString() + "|" + map.get(uuid) + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fileToMap() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String fileLine = null;

			while ((fileLine = reader.readLine()) != null) {
				UUID uuid = UUID.fromString(fileLine.split("\\|")[0]);
				ChatColor chatColor = ChatColor.valueOf(fileLine.split("\\|")[1]);

				map.put((K) uuid, (T) chatColor.name());

			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
