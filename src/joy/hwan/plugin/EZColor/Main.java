package joy.hwan.plugin.EZColor;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();

		getCommand("ezcolor").setExecutor(new EZColorCommand());

	}

}
