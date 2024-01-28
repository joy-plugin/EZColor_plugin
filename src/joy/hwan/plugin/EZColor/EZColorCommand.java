package joy.hwan.plugin.EZColor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class EZColorCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		Player player = (Player) sender;

		if (player.isOp()) {
			player.sendMessage("명령어를 사용 했습니다!");
		} else {
			player.sendMessage("명령어를 사용 할 권한이 없습니다!");
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();

		list.add("/test");
		list.add("/test2");

		return list;
	}

}
