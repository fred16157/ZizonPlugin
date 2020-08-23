package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
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

import java.util.Arrays;

public class ToolInventory implements Listener {
    private final Inventory inv;

    public ToolInventory() {
        inv = Bukkit.createInventory(null, 9, "아이템 선택");
        initializeItems();
    }

    public void initializeItems() {
        inv.addItem(createGuiItem(Material.IRON_INGOT, "우르르쾅쾅", "우르르쾅쾅펑펑퍼어ㅓ어어퍼ㅓㅇ"));
        inv.addItem(createGuiItem(Material.FIRE_CHARGE, "김순상의 초고출력", "잘 봐라 신입. 초고출력 각이다"));
        inv.addItem(createGuiItem(Material.BAMBOO, "곽일천천시", "성능충쉑"));
        inv.addItem(createGuiItem(Material.WOODEN_SWORD, "공간파베기", "아니 간파했다고"));
        inv.addItem(createGuiItem(Material.GOLDEN_SWORD, "투구깨기", "존재 자체가 버그임"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    @EventHandler
    public void onOpenToolInventory(OpenToolInventoryEvent otie) {
        otie.ent.openInventory(inv);
    }

    // 아이템 클릭
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // 클릭한 아이템 없으면 리턴해서 무시
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();
        p.sendMessage(clickedItem.getItemMeta().getDisplayName() + "를 선택하셨습니다.");
        p.getInventory().addItem(clickedItem);
        p.closeInventory();
    }

    // 드래그 캔슬
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }
}
