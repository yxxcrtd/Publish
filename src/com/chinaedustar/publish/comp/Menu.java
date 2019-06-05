package com.chinaedustar.publish.comp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

/**
 * 定义一个菜单，菜单是子菜单和菜单项的集合。
 * 
 * @author liujunxing
 */
public class Menu extends MenuBase implements java.util.List<MenuBase> {
	/** 子项目的集合。 */
	private final ArrayList<MenuBase> children = new ArrayList<MenuBase>();
	
	/** 此菜单使用的图标或图片。 */
	private String icon;
	
	/**
	 * 构造一个菜单。
	 *
	 */
	public Menu() {
		
	}
	
	/**
	 * 使用指定的菜单文字构造一个菜单。
	 * @param text
	 */
	public Menu(String text) {
		super(text);
	}
	
	/**
	 * 使用指定的标识、文字构造一个菜单。
	 * @param id
	 * @param text
	 */
	public Menu(String id, String text) {
		super(text);
		super.setId(id);
	}

	/**
	 * 使用指定的标识、文字、图标构造一个菜单。
	 * @param id
	 * @param text
	 */
	public Menu(String id, String text, String icon) {
		super(text);
		super.setId(id);
		this.icon = icon;
	}

	/** 获得友好的字符串表示形式。 */
	@Override public String toString() {
		return "Menu{text=" + super.getText() + ",item_num=" + this.children.size() + "}";
	}
	
	/**
	 * 在最后位置添加一个菜单项。
	 * @param pos
	 * @param item
	 */
	public void addMenuItem(MenuItem item) {
		internalAddMenu(size(), item);
	}
	
	/**
	 * 在指定的位置插入一个菜单项。
	 * @param pos
	 * @param item
	 */
	public void addMenuItem(int pos, MenuItem item) {
		internalAddMenu(pos, item);
	}

	/**
	 * 在指定的位置插入一个子菜单。
	 * @param pos
	 * @param subMenu
	 */
	public void addSubMenu(int pos, Menu subMenu) {
		internalAddMenu(pos, subMenu);
	}

	/**
	 * 判定这个项目是否是一个菜单或子菜单。不是菜单(Menu)就是菜单项(MenuItem)。
	 * @return
	 */
	public boolean getIsMenu() {
		return true;
	}
	
	/**
	 * 判定这个项目是否是一个菜单项。不是菜单项就是子菜单。
	 * @return
	 */
	public boolean getIsMenuItem() {
		return false;
	}

	/**
	 * 获得指定 id 的子菜单。
	 * @param submenu_id
	 * @return 返回找到的子菜单。
	 */
	public Menu getSubMenuById(String submenu_id) {
		if (submenu_id == null) return null;
		for (int i = 0; i < children.size(); ++i) {
			MenuBase sub_menu = children.get(i);
			if (sub_menu instanceof Menu) {
				if (submenu_id.equalsIgnoreCase(((Menu)sub_menu).getId()))
					return (Menu)sub_menu;
			}
		}
		return null;
	}
	
	/**
	 * 根据 MenuItem 的正文查找菜单项目。
	 * @param text
	 * @return
	 */
	public MenuItem getMenuItemByText(String text) {
		if (children == null) return null;
		for (int i = 0; i < children.size(); ++i) {
			MenuBase child = children.get(i);
			if (child instanceof MenuItem) {
				if (child.getText().equalsIgnoreCase(text))
					return (MenuItem)child;
			}
		}
		return null;
	}
	
	/* === getter and setter ========================================================== */
	
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	/* === Collection 接口实现 ========================================================= */

    public int size() {
    	return this.children.size();
    }

    public boolean isEmpty() {
    	return this.children.isEmpty();
    }

    public boolean contains(Object o) {
    	return this.children.contains(o);
    }

    public Iterator<MenuBase> iterator() {
    	return this.children.iterator();
    }

    public Object[] toArray() {
    	return this.children.toArray();
    }

    public <T> T[] toArray(T[] a) {
    	throw new UnsupportedOperationException();
    }

    // Modification Operations

    public boolean add(MenuBase o) {
    	return this.children.add(o);
    }

    public boolean remove(Object o) {
    	return this.children.remove(o);
    }

    // Bulk Operations

    public boolean containsAll(Collection<?> c) {
    	throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends MenuBase> c) {
    	throw new UnsupportedOperationException();
    }
    
    public boolean addAll(int index, Collection<? extends MenuBase> c) {
    	throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
    	throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
    	throw new UnsupportedOperationException();
    }

    public void clear() {
    	this.children.clear();
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this collection for equality. <p>
     *
     * While the <tt>Collection</tt> interface adds no stipulations to the
     * general contract for the <tt>Object.equals</tt>, programmers who
     * implement the <tt>Collection</tt> interface "directly" (in other words,
     * create a class that is a <tt>Collection</tt> but is not a <tt>Set</tt>
     * or a <tt>List</tt>) must exercise care if they choose to override the
     * <tt>Object.equals</tt>.  It is not necessary to do so, and the simplest
     * course of action is to rely on <tt>Object</tt>'s implementation, but
     * the implementer may wish to implement a "value comparison" in place of
     * the default "reference comparison."  (The <tt>List</tt> and
     * <tt>Set</tt> interfaces mandate such value comparisons.)<p>
     *
     * The general contract for the <tt>Object.equals</tt> method states that
     * equals must be symmetric (in other words, <tt>a.equals(b)</tt> if and
     * only if <tt>b.equals(a)</tt>).  The contracts for <tt>List.equals</tt>
     * and <tt>Set.equals</tt> state that lists are only equal to other lists,
     * and sets to other sets.  Thus, a custom <tt>equals</tt> method for a
     * collection class that implements neither the <tt>List</tt> nor
     * <tt>Set</tt> interface must return <tt>false</tt> when this collection
     * is compared to any list or set.  (By the same logic, it is not possible
     * to write a class that correctly implements both the <tt>Set</tt> and
     * <tt>List</tt> interfaces.)
     *
     * @param o Object to be compared for equality with this collection.
     * @return <tt>true</tt> if the specified object is equal to this
     * collection
     * 
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    // boolean equals(Object o);

    /**
     * Returns the hash code value for this collection.  While the
     * <tt>Collection</tt> interface adds no stipulations to the general
     * contract for the <tt>Object.hashCode</tt> method, programmers should
     * take note that any class that overrides the <tt>Object.equals</tt>
     * method must also override the <tt>Object.hashCode</tt> method in order
     * to satisfy the general contract for the <tt>Object.hashCode</tt>method.
     * In particular, <tt>c1.equals(c2)</tt> implies that
     * <tt>c1.hashCode()==c2.hashCode()</tt>.
     *
     * @return the hash code value for this collection
     * 
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    // int hashCode();

    // Positional Access Operations

    public MenuBase get(int index) {
    	if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
    	return this.children.get(index);
    }

    public MenuBase set(int index, MenuBase element) {
    	throw new UnsupportedOperationException();
    }

    public void add(int index, MenuBase element) {
    	this.children.add(index, element);
    }

    public MenuBase remove(int index) {
    	return this.children.remove(index);
    }

    // Search Operations

    public int indexOf(Object o) {
    	return this.children.indexOf(o); 
    }

    public int lastIndexOf(Object o) {
    	return this.children.lastIndexOf(o);
    }

    // List Iterators

    public ListIterator<MenuBase> listIterator() {
    	return this.children.listIterator();
    }

    public ListIterator<MenuBase> listIterator(int index) {
    	return this.children.listIterator(index);
    }

    // View
    public List<MenuBase> subList(int fromIndex, int toIndex) {
    	return this.children.subList(fromIndex, toIndex);
    }

	/** 插入一个子菜单或菜单项。 */
	private final void internalAddMenu(int pos, MenuBase sub_menu_or_item) {
		if (pos < 0 || pos >= this.children.size())
			this.children.add(sub_menu_or_item);
		else
			this.children.add(pos, sub_menu_or_item);
	}
}
