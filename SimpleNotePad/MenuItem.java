import javax.swing.*;
/*
This class enables us to create Menu items for our application that allow subMenu's to be added to them
They are similar to the JMenu items, but have some features more customized to our application
 */
public class MenuItem {
    //private feilds that every subMenu has
    private JMenu menuItem;
    private String menuItemName;

    public MenuItem(String menuItemName) {
        //create the actual menuItem
        this.menuItem = new JMenu(menuItemName);
        this.menuItemName = menuItemName;
    }

    public JMenu getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(JMenu menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    /*
    This mehtod allows us to add subMenu elements to our Menu item
    */
    public void addSubMenu(String subItemName,SimpleNotePad app,Boolean submenu, Object ref){
           //to "this" menu item, we can add sub menus, will have to create sub menu first
        SubMenuItem subItem = new SubMenuItem(subItemName);
            //add the subitem to this menuItem
        subItem.addToMenu(this.menuItem, app,submenu, ref);


    }

    /*
   This mehtod allows us to add sub-...-subMenu elements to our Menu item
   */
    public MenuItem addSubMenuWithSubMenu(String subItemName){

        //to already existing menu < add this new "item",then add "sub menu" to the new item
        MenuItem menuWithDropDown= new MenuItem(subItemName);

        this.getMenuItem().add(menuWithDropDown.getMenuItem());
        //menuWithDropDown.addSubMenuWithSubMenu(subItemName,app,true,ref);

        return menuWithDropDown;
    }

    /*the method clears all the submenus within our menu
     */
    public void removeAllSubMenus(){
        this.getMenuItem().removeAll();
    }


}
