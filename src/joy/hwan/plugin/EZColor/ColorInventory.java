package joy.hwan.plugin.EZColor;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorInventory implements Listener {
	private final Inventory inv;

	private final Material[] woolMaterialList;

	private final String[] woolColorList;
	private final Main main;

	private HashMap<String, Material> stringToWoolMap = new HashMap<String, Material>();
	private HashMap<Material, String> woolToStringMap = new HashMap<Material, String>();

	public ColorInventory(Main main) {
		inv = Bukkit.createInventory(null, 9, "CHAT COLOR");

		this.woolMaterialList = new Material[] { Material.BLACK_WOOL, Material.BLUE_WOOL, Material.GRAY_WOOL,
				Material.GREEN_WOOL, Material.RED_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL };

		this.woolColorList = new String[] { "BLACK", "BLUE", "GRAY", "GREEN", "RED", "WHITE", "YELLOW" };

		this.main = main;

		initializeMap();

		initializeItem();
	}

	// You can call this whenever you want to put the items in
	public void initializeMap() {
		for (int index = 0; index < woolMaterialList.length; index++) {
			stringToWoolMap.put(woolColorList[index], woolMaterialList[index]);
			woolToStringMap.put(woolMaterialList[index], woolColorList[index]);
		}
	}

	public void initializeItem() {
		for (String color : stringToWoolMap.keySet()) {
			Material material = stringToWoolMap.get(color);

			inv.addItem(createGuiItem(material, color));
		}
		inv.setItem(8, createGuiItem(Material.BARRIER, "CLOSE"));
	}

	// Nice little method to create a gui item with a custom name, and description
	protected ItemStack createGuiItem(final Material material, final String name) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		// Set the name of the item
		meta.setDisplayName(name);

		item.setItemMeta(meta);

		return item;
	}

	// You can open the inventory with this
	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
	}

	public void closeInventory(final HumanEntity ent) {
		ent.closeInventory();
	}

	// Check for clicks on items
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (!e.getInventory().equals(inv))
			return;

		e.setCancelled(true);

		final ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir())
			return;

		final Player player = (Player) e.getWhoClicked();
		Material material = clickedItem.getType();

		if (material == Material.BARRIER) {
			closeInventory(player);
			return;
		}

		String color = woolToStringMap.get(material);
		ChatColor chatColor = ChatColor.valueOf(color);

		player.sendMessage("플레이어의 채팅 색깔이 " + (chatColor + color) + ChatColor.RESET + " 로 변경됐습니다.");
		main.useChatColorMap.put(player.getUniqueId(), color);
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory().equals(inv)) {
			e.setCancelled(true);
		}
	}
}
