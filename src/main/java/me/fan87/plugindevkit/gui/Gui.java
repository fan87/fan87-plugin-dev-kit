package me.fan87.plugindevkit.gui;

import lombok.Getter;
import lombok.Setter;
import me.fan87.plugindevkit.PluginInstanceGrabber;
import me.fan87.plugindevkit.gui.impl.itemprovider.GuiItemProvider;
import me.fan87.plugindevkit.utils.StaticEventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

/**
 * Gui API Main Class.<br>
 * Coordinates System:<br>
 * &#09;The coord of first item in the first row will be 1, 1, so last item in the last row will be 9, 6<br>
 * &#09;Example:<br>
 * &#09;&#09;x x x x x x x x x x<br>
 * &#09;&#09;x x x x x x o x x x<br>
 * &#09;&#09;x x x x x x x x x x<br>
 * &#09;&#09;The O is at 7, 2<br><br>
 *
 * Style:<br>
 * &#09;The GuiItemProvider provides the style of built-in APIs, for example: {@link Gui#renderGoBackItems(Gui, Player)}<br>
 * &#09;The style is based on Hypixel's Gui Style, including GuiList<br>
 * &#09;If you want to use your own style, override {@link Gui#getGuiItemProvider()} method<br>
 *
 * @author fan87
 */
public abstract class Gui implements Listener {
    @Getter
    private GuiItem[] items;
    @Getter
    protected int size;
    @Getter
    @Setter
    private String title;
    private Inventory inventory;

    /**
     * Initialize the Gui. Add some items to the Gui in this method
     */
    public abstract void init();

    /**
     * Constructor, gives some information to Gui in order to let it know some information to generate a Gui
     * @param title Title of the gui. Colorless is recommended
     * @param rows How many rows, min 1 max 6
     */
    public Gui(String title, int rows) {
        this.items = new GuiItem[rows*9];
        this.title = title.substring(0, Math.min(32, title.length()));
        this.size = rows*9;
        StaticEventManager.register(this);
    }

    /**
     * Will be called when someone clicked on the Gui.
     * @param inventoryClickEvent The raw InventoryClickEvent
     * @return True if it shouldn't be cancelled
     */
    protected boolean onClick(InventoryClickEvent inventoryClickEvent) {
        return false;
    }

    @EventHandler
    public final void _onClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inventory)) {
            if (!onClick(event)) {
                event.setCancelled(true);
            }
            for (int i = 0; i < items.length; i++) {
                if (i == event.getRawSlot()) {
                    if (items[i] == null) continue;
                    if (items[i].getHandler() == null) continue;
                    items[i].getHandler().handleClick(event);
                }
            }
        }
    }

    @EventHandler
    public final void _onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            destroy();
            onGuiClose(event);
        }
    }

    /**
     * Will be called when someone closed the Gui.
     * @param event The raw InventoryClickEvent
     */
    protected void onGuiClose(InventoryCloseEvent event) {

    }

    /**
     * Fills the border with <code>item</code>.
     * @param item The item you want to fill with
     * @return <code>this</code>
     */
    public Gui fillBorder(GuiItem item) {
        fill(1, 1, 9, 1, item);
        fill(1, size/9, 1, 1, item);
        fill(9, size/9, 9, 0, item);
        fill(9, size/9, 0, size/9, item);
        updateInventory();
        return this;
    }

    /**
     * Fills the entire Gui with <code>item</code>.
     * @param item The item you want to fill with
     * @return <code>this</code>
     */
    public Gui fill(GuiItem item) {
        Arrays.fill(items, item);
        if (inventory != null) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, item.getItemStack());
            }
        }
        updateInventory();
        return this;
    }

    /**
     * Get the raw slot number with given X and Y position<br>
     * See the coordinate system document in the JavaDoc of {@link Gui}
     * @param x X Position, max: 9, min: 1
     * @param y Y Position, max: Value of <code>row</code> ({@link Gui#getSize()}/6), min: 1
     * @return The raw slot number that can be used in Bukkit and NMS Codebase
     */
    public static int getSlotNumberByXY(int x, int y) {
        return (y - 1) * 9 + (x - 1);
    }

    /**
     * Set the Gui Item of the given position
     * @param x X Position, max: 9, min: 1
     * @param y Y Position, max: Value of <code>row</code> ({@link Gui#getSize()}/6), min: 1
     * @param item The item you want to set
     * @return <code>this</code>
     */
    public Gui set(int x, int y, GuiItem item) {
        int slot = getSlotNumberByXY(x, y);
        slot = Math.min(size - 1, Math.max(0, slot));
        items[slot] = item;
        if (inventory != null) {
            inventory.setItem(slot, item.getItemStack());
        }
        updateInventory();
        return this;
    }

    public void updateInventory() {
        Bukkit.getScheduler().runTaskLater(PluginInstanceGrabber.getPluginInstance(), new Runnable() {
            @Override
            public void run() {
                if (inventory != null) {
                    for (HumanEntity viewer : inventory.getViewers()) {
                        if (viewer instanceof Player) {
                            ((Player) viewer).updateInventory();
                       }
                    }
                }
            }
        }, 1);
    }

    public Gui fill(int fromX, int fromY, int toX, int toY, GuiItem item) {
        fromX--;
        fromY--;
        toX--;
        toY--;
        if (fromX > toX) {
            int temp = fromX;
            fromX = toX;
            toX = temp;
        }
        if (fromY > toY) {
            int temp = fromY;
            fromY = toY;
            toY = temp;
        }
        for (int i = 0; i < items.length; i++) {
            int x = i % 9;
            int y = i / 9;
            if (x >= fromX && x <= toX
                    && y >= fromY && y <= toY) {
                items[i] = item;
                if (inventory != null) {
                    inventory.setItem(i, item.getItemStack());
                }
            }
        }
        updateInventory();
        return this;
    }

    public void open(Player player) {
        Bukkit.getScheduler().runTaskLater(PluginInstanceGrabber.getPluginInstance(), () -> {
            player.openInventory(getInventory());
        }, 0);
    }

    public Inventory getInventory() {
        if (this.inventory != null) return this.inventory;
        init();
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getItemStack());
            }
        }
        this.inventory = inventory;
        return inventory;
    }

    public void renderGoBackItems(Gui previousPage, Player player) {
        String title = previousPage.getTitle();

        set(4, 6, new GuiItem(getGuiItemProvider().getPreviousPageItem(title), event -> {
            previousPage.open(player);
        }));
        set(5, 6, new GuiItem(getGuiItemProvider().getClosePageItem(), event -> {
            player.closeInventory();
        }));
    }

    /**
     * Destroy the Gui. Note that <strong>it won't close the Gui</strong> but destroy it<br>
     * like unregistering it from Event System
     */
    protected void destroy() {
        StaticEventManager.unregister(this);
    }

    public IGuiItemProvider getGuiItemProvider() {
        return new GuiItemProvider();
    }

}
