package com.example.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.example.entity.Bid;
import com.example.entity.Item;
import com.example.entity.User;
import com.example.exception.InvalidBidException;
import com.example.service.BidTrackerService;

/**
 * The implementation of a BidTrackerService.
 */
public class BidTrackerServiceImpl implements BidTrackerService{

	public Map<Item, List<Bid>> auctionRoom;
	
	private LinkedList<Bid> bids;
	
	public BidTrackerServiceImpl() {
		auctionRoom = new ConcurrentHashMap<>();
	}
	
	public Map<Item, List<Bid>> getCurrentAuctionRoomCopy() {
	    return new HashMap<>(auctionRoom);
	  }
	
	// synchronized keyword is used to make sure that only one bid can be processed at a time.
	@Override
	public synchronized String recordUserBidOnItem(Item item, Bid bid) throws InvalidBidException {
		String message = null;
		
		checkNullItem(item);
		checkNullBid(bid);
		
		if(auctionRoom.get(item) == null) {
			bids = new LinkedList<>();
			bids.add(bid);
			auctionRoom.put(item, bids);
			message = "First bid added successfully.";
		} else
		if( bid.getPrice()>bids.getLast().getPrice()) {
			bids.add(bid);
			auctionRoom.put(item, bids);
			message = "Bid added successfully.";
		} else {
			throw new InvalidBidException("Given Bid should be more than the current winning bid");
		}
		return message;
	}

	private void checkNullItem(Item item) {

		if(item==null) {
			throw new IllegalArgumentException("Item can't be null.");
		}
	}
	private void checkNullBid(Bid bid) {

		if(bid==null) {
			throw new IllegalArgumentException("Bid can't be null.");
		}
	}

	
	
	@Override
	public Bid getCurrentWinningBidForItem(Item item) {
		
		checkNullItem(item);
		
		List<Bid> bidsForItem = auctionRoom.get(item);
		return ((LinkedList<Bid>) bidsForItem).getLast();
		
	}

	@Override
	public List<Bid> getAllBidForItem(Item item) {
		
		checkNullItem(item);
		List<Bid> allBidsForItem = auctionRoom.get(item);
		
		return allBidsForItem;
	}

	@Override
	public Set<Item> getAllItemsBidOfUser(User user) {
		
		Set<Item> getAllItemsBidOfUser = new HashSet<>();
		for(Map.Entry<Item, List<Bid>> m : auctionRoom.entrySet()) {
			for(Bid b: m.getValue()) {
				if(b.getUser()==user) {
					getAllItemsBidOfUser.add(m.getKey());
				}
			}
		}
		return getAllItemsBidOfUser;
		
	}

}
