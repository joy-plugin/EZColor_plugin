package joy.hwan.plugin.EZColor;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import joy.hwan.plugin.EZColor.command.EZColorCommand;
import joy.hwan.plugin.EZColor.event.AsyncPlayerChat;
import joy.hwan.plugin.EZColor.util.FileUtil;

public class Main extends JavaPlugin {

	public HashMap<UUID, String> useChatColorMap = new HashMap<UUID, String>();

	private final FileUtil<UUID, String> fileUtil;

	private final File file = new File(getDataFolder().getParentFile(), "/chatColor.txt");

	final ColorInventory colorInventory;
	final AsyncPlayerChat asyncPlayerChat;

	public Main() {
		this.fileUtil = new FileUtil<UUID, String>(file, useChatColorMap);
		this.colorInventory = new ColorInventory(this);
		this.asyncPlayerChat = new AsyncPlayerChat(this);
	}

	@Override
	public void onLoad() {
		super.onLoad();

		fileUtil.makeFile();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();

		fileUtil.mapToFile();
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();

		fileUtil.fileToMap();

		registerEvent();
		registerCommand();
	}

	public void registerEvent() {
		getServer().getPluginManager().registerEvents(asyncPlayerChat, this);
		getServer().getPluginManager().registerEvents(colorInventory, this);
	}

	public void registerCommand() {
		getCommand("ezcolor").setExecutor(new EZColorCommand(this, colorInventory));
	}

}
