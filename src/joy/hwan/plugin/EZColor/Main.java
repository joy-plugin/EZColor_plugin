package joy.hwan.plugin.EZColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import joy.hwan.plugin.EZColor.command.EZColorCommand;
import joy.hwan.plugin.EZColor.event.AsyncPlayerChat;

public class Main extends JavaPlugin {

	public HashMap<UUID, String> useChatColorMap = new HashMap<UUID, String>();

	private final File file = new File(getDataFolder().getParentFile(), "/chatColor.txt");

	final ColorInventory colorInventory;

	public Main() {
		this.colorInventory = new ColorInventory(this);
	}

	@Override
	public void onLoad() {
		super.onLoad();

		makeFile();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();

		mapToFile();
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();

		fileToMap();

		registerEvent();
		registerCommand();
	}

	public void registerEvent() {
		getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
		getServer().getPluginManager().registerEvents(colorInventory, this);
	}

	public void registerCommand() {
		getCommand("ezcolor").setExecutor(new EZColorCommand(this, colorInventory));
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

			for (UUID uuid : useChatColorMap.keySet()) {
				writer.write(uuid.toString() + "|" + useChatColorMap.get(uuid) + "\n");
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

				useChatColorMap.put(uuid, chatColor.name());
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
