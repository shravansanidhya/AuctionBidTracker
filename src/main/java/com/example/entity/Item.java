package com.example.entity;

/**
 * The representation of an item in the auction.
 */
public class Item {

	private int itemId;
	private String itemName;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemId;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (itemId != other.itemId)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + "]";
	}

}
