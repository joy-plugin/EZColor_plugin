package joy.hwan.plugin.EZColor;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
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

	HashMap<String, Material> woolMap = new HashMap<String, Material>();

	public ColorInventory() {
		// Create a new inventory, with no owner (as this isn't a real inventory), a
		// size of nine, called example
		inv = Bukkit.createInventory(null, 54, "Example");

		// Put the items into the inventory
		initializeMap();

		initializeItem();
	}

	// You can call this whenever you want to put the items in
	public void initializeMap() {
		woolMap.put("BLACK", Material.BLACK_WOOL);
		woolMap.put("BLUE", Material.BLUE_WOOL);
		woolMap.put("BROWN", Material.BROWN_WOOL);
		woolMap.put("CYAN", Material.CYAN_WOOL);
		woolMap.put("GRAY", Material.GRAY_WOOL);
		woolMap.put("GREEN", Material.GREEN_WOOL);
		woolMap.put("LIGHT_BLUE", Material.LIGHT_BLUE_WOOL);
		woolMap.put("LIGHT_GRAY", Material.LIGHT_GRAY_WOOL);
		woolMap.put("LIME", Material.LIME_WOOL);
		woolMap.put("MAGENTA", Material.MAGENTA_WOOL);
		woolMap.put("ORANGE", Material.ORANGE_WOOL);
		woolMap.put("PINK", Material.PINK_WOOL);
		woolMap.put("PURPLE", Material.PURPLE_WOOL);
		woolMap.put("RED", Material.RED_WOOL);
		woolMap.put("WHITE", Material.WHITE_WOOL);
		woolMap.put("YELLOW", Material.YELLOW_WOOL);
	}

	public void initializeItem() {
		for (String color : woolMap.keySet()) {
			Material material = woolMap.get(color);

			inv.addItem(createGuiItem(material, color));
		}
		inv.setItem(53, createGuiItem(Material.BARRIER, "CLOSE"));
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

		final Player p = (Player) e.getWhoClicked();

		// Using slots click is a best option for your inventory click's
		p.sendMessage("You clicked at slot " + e.getRawSlot());
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory().equals(inv)) {
			e.setCancelled(true);
		}
	}
}
