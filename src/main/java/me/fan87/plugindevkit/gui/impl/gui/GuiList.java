package me.fan87.plugindevkit.gui.impl.gui;

import lombok.Getter;
import me.fan87.plugindevkit.gui.Gui;
import me.fan87.plugindevkit.gui.GuiItem;
import me.fan87.plugindevkit.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Gui List, an implementation of {@link Gui} based on Hypixel's Gui Style<br>
 * You must call {@link GuiList#putItems()} in your {@link Gui#init()} function or it won't work
 * @author fan87
 */
public abstract class GuiList extends Gui {

    private final int currentPage;
    @Getter
    private final List<GuiItem> contents;

    /**
     * Initialize the Gui List.<br>
     * Note that default Gui Style is based on Hypixel's Gui Style<br>
     * if you want your own Gui Style please override {@link GuiList#getNextPageItem()}, {@link GuiList#getPreviousPageItem()} and {@link GuiList#getFormattedTitle(int, int, String)}<br>
     * @param title Title of the Gui List. It's not going be the final Gui title, but the {@link GuiList#getFormattedTitle(int, int, String)}'s return value
     * @param currentPage The current page, you will normally want to keep 1 current page argument instead of overriding it with your own value
     * @param contents Content of the Gui. You can set this to an empty {@link java.util.ArrayList} and add the content before {@link GuiList#putItems()} being called in {@link Gui#init()}
     */
    public GuiList(String title, int currentPage, List<GuiItem> contents) {
        super(title, 6);
        this.contents = contents;
        this.currentPage = currentPage;
    }

    /**
     * Put contents into Gui, including the title, next page button and previous page button.
     * Must be called in {@link Gui#init()} or items won't appear, and it will just be a regular Gui
     */
    public void putItems() {
        setTitle(getFormattedTitle(getCurrentPage(), getMaxPages(), getTitle()));
        for (int i = 0; i < contents.size(); i++) {
            GuiItem item = contents.get(i);
            if (i > getLastIndex()) {
                break;
            }
            if (i >= getFirstIndex()) {
                int[] position = getPositionOf(i - getFirstIndex());
                set(position[1], position[0], item);
            }
        }
        if (canGoNextPage()) {
            set(9, 6, new GuiItem(getNextPageItem(), event -> goPage(event, getCurrentPage() + 1)));
        }
        if (canGoPreviousPage()) {
            set(1, 6, new GuiItem(getPreviousPageItem(), event -> goPage(event, getCurrentPage() - 1)));
        }
    }

    /**
     * @return The appearance of the next page button
     * @implNote  You can override this function to change the next page item appearance
     */
    public ItemStack getNextPageItem() {
        return new ItemStackBuilder(Material.ARROW)
                .addAllItemFlags()
                .setDisplayName(ChatColor.GREEN + "Next Page")
                .addLore(ChatColor.YELLOW + "Page " + (getCurrentPage() + 1))
                .build();
    }

    /**
     * @return The appearance of the previous page button
     * @implNote  You can override this function to change the next page item appearance
     */
    public ItemStack getPreviousPageItem() {
        return new ItemStackBuilder(Material.ARROW)
                .addAllItemFlags()
                .setDisplayName(ChatColor.GREEN + "Previous Page")
                .addLore(ChatColor.YELLOW + "Page " + (getCurrentPage() - 1))
                .build();
    }

    /**
     * @param currentPage The current page number
     * @param maxPages Number of total pages
     * @param title The original title (For example: <code>Kits</code>
     * @return The formatted title with given currentPage, maxPages, and Gui title
     */
    public String getFormattedTitle(int currentPage, int maxPages, String  title) {
        return "(" + currentPage + "/" + maxPages + ") " + title;
    }

    /**
     * The max value will be {@link Integer#MAX_VALUE}, and the min value will be 1 (If there are more than 0 content)
     * @return Number of total pages
     */
    public int getMaxPages() {
        return (int) Math.ceil(contents.size() / 28d);
    }

    /**
     * @return The current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @return Returns true if there's previous page available
     */
    public boolean canGoPreviousPage() {
        return getCurrentPage() > 1;
    }

    /**
     * @return Returns true if there's next page available
     */
    public boolean canGoNextPage() {
        return getCurrentPage() < getMaxPages();
    }

    /**
     * @return The index of the first content in the Gui based on the current page
     */
    public int getFirstIndex() {
        return (getCurrentPage() - 1) * 28;
    }

    /**
     * @return The index of the last content in the Gui based on the current page
     */
    public int getLastIndex() {
        return getCurrentPage() * 28 - 1;
    }

    public int[] getPositionOf(int index) {
        return new int[] {
                index/7 + 2,
                index%7 + 2
        };
    }

    /**
     * Open the gui with page with given InventoryClickEvent and page number.<br>
     * We don't know the constructor of your class, so you need to implement this manually
     * @param event The InventoryClickEvent, so you can get the information about the player who clicked on the item
     * @param page The page number, you just pass it to the <code>currentPage</code> constructor parameter directly
     */
    public abstract void goPage(InventoryClickEvent event, int page);

}
