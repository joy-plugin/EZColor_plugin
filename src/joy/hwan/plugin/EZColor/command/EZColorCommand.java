package joy.hwan.plugin.EZColor.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import joy.hwan.plugin.EZColor.Main;

public class EZColorCommand implements CommandExecutor, TabExecutor {
	private final Main main;

	public EZColorCommand(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		Player player = (Player) sender;

		if (args.length == 1) {
			String color = args[0];

			try {
				ChatColor chatColor = ChatColor.valueOf(color);

				main.useChatColorMap.put(player.getUniqueId(), color);

				return true;
			} catch (NullPointerException error) {
				error.printStackTrace();
				return false;
			} catch (IllegalArgumentException error) {
				player.sendMessage(ChatColor.RED + "잘못된 컬러 입니다. 추천 리스트에 뜨는 컬러를 선택해 주세요.");
				return false;
			}

		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();

		for (ChatColor chatColor : ChatColor.values()) {
			list.add(chatColor.name());
		}

		return list;
	}

}
