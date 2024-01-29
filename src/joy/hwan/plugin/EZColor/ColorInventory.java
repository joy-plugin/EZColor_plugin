package joy.hwan.plugin.EZColor;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorInventory implements Listener {
	private final Inventory inv;

	Material[] woolMaterialList = new Material[] { Material.BLACK_WOOL, Material.BLUE_WOOL, Material.GRAY_WOOL,
			Material.GREEN_WOOL, Material.RED_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL };

	String[] woolColorList = new String[] { "BLACK", "BLUE", "GRAY", "GREEN", "RED", "WHITE", "YELLOW" };

	HashMap<String, Material> stringToWoolMap = new HashMap<String, Material>();
	HashMap<Material, String> woolToStringMap = new HashMap<Material, String>();

	final Main main;

	public ColorInventory(Main main) {
		inv = Bukkit.createInventory(null, 9, "Example");
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

	@EventHandler
	public void ballFiring(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR))
			return;
		if (!(e.getItem().getType() == Material.STICK))
			return;
		openInventory(p);
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
			player.closeInventory();
			return;
		}

		ChatColor chatColor = ChatColor.valueOf(woolToStringMap.get(material));
		// Using slots click is a best option for your inventory click's

		main.useChatColorMap.put(player.getUniqueId(), woolToStringMap.get(material));
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory().equals(inv)) {
			e.setCancelled(true);
		}
	}
}
