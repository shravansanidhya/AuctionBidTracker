package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.entity.Bid;
import com.example.entity.Item;
import com.example.entity.User;
import com.example.exception.InvalidBidException;

/**
 * The interface for a Bid Tracker.
 */
public interface BidTrackerService {

	/**
	 * To get the copy of auction room.
	 * 
	 * @return the copy of auction room.
	 */
	public Map<Item, List<Bid>> getCurrentAuctionRoomCopy();

	/**
	 * 
	 * @param item
	 * @param bid
	 * @return message of bidding successful or not.
	 * @throws InvalidBidException
	 */
	public String recordUserBidOnItem(Item item, Bid bid) throws InvalidBidException;

	/**
	 * 
	 * @param item the item
	 * @return the current winning bid for the given item.
	 */
	public Bid getCurrentWinningBidForItem(Item item);

	/**
	 * 
	 * @param item
	 * @return the list of bid for a particular item.
	 */
	public List<Bid> getAllBidForItem(Item item);

	/**
	 * 
	 * @param user
	 * @return the set of items bid by a particular user.
	 */
	public Set<Item> getAllItemsBidOfUser(User user);

}
