package joy.hwan.plugin.EZColor.event;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import joy.hwan.plugin.EZColor.Main;

public class AsyncPlayerChat implements Listener {
	private final Main main;

	public AsyncPlayerChat(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@EventHandler
	public void asyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		String color = main.useChatColorMap.get(uuid);
		if (color != null) {
			ChatColor chatColor = ChatColor.valueOf(color);

			if (chatColor != null) {
				String message = event.getMessage();
				event.setMessage(chatColor + message);
			}
		}
	}
}
