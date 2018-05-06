
/*
This class allows us fast access to object that we will very often have in our application. There are subMenu item objects.
The objects can be subItems of the type JMenu (if they have sub-sub-... menus) or just simple JMenuItem if they are the only level of sub menus

 */
import javax.swing.*;

public class SubMenuItem {

    private JMenuItem item;
    private String itemName;

    //construct the subMenus
    public SubMenuItem(String itemName) {
        this.item = new JMenuItem(itemName);
        this.itemName = itemName;


    }

    public JMenuItem getItem() {
        return item;
    }

    public void setItem(JMenuItem item) {
        this.item = item;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //each one of these items should be added to a JMenu and should have listeners and commands

    public void addToMenu(JMenu menu, SimpleNotePad app, boolean subMenu, Object reference) {
        //add the submenu item to the menu
        if (!subMenu) {

            menu.add(this.getItem());

            //add listeners and commands for this item into the currently running app
            this.item.addActionListener(app);

            //set command for this sub menu
            this.item.setActionCommand(this.itemName);
        } else {

            //if the item being added to the Menu element is a subMenu, then we will be storing the submenu in a Data Stucture and we will want
            //to id it by listening to its unique reference
            menu.add(this.getItem());

            //add listeners and commands for this item into the currently running app
            this.item.addActionListener(app);

            //set command for this sub menu
            this.item.setActionCommand(reference + ""); //change type object to String
        }
    }


}
