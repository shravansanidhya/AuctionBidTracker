package com.example.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.entity.Bid;
import com.example.entity.Item;
import com.example.entity.User;
import com.example.exception.InvalidBidException;
import com.example.service.BidTrackerService;
import com.example.serviceImpl.BidTrackerServiceImpl;

public class BidTrackerServiceImplTest {

	private static final User user1 = new User(201, "Rahul");
	private static final User user2 = new User(202, "Suresh");
	private static final User user3 = new User(203, "Mohan");

	private static final Item item1 = new Item(101, "bazaz");
	private final Item item2 = new Item(102, "Apache");
	private static final Item item3 = new Item(103, "Pulsor");
	private static final Item item4 = new Item(104, "Maruti");

	private static BidTrackerService bidTrackerServiceImpl;

	@BeforeAll
	public static void initAuctionBoard() throws InvalidBidException {
		
		bidTrackerServiceImpl = new BidTrackerServiceImpl();
		
		bidTrackerServiceImpl.recordUserBidOnItem(item1, new Bid(user1, 55000));
		bidTrackerServiceImpl.recordUserBidOnItem(item1, new Bid(user2, 60000));
		bidTrackerServiceImpl.recordUserBidOnItem(item1, new Bid(user3, 65000));
		bidTrackerServiceImpl.recordUserBidOnItem(item3, new Bid(user1, 25000));
		bidTrackerServiceImpl.recordUserBidOnItem(item3, new Bid(user2, 50000));
		bidTrackerServiceImpl.recordUserBidOnItem(item3, new Bid(user3, 70000));
		bidTrackerServiceImpl.recordUserBidOnItem(item4, new Bid(user3, 15000));
		bidTrackerServiceImpl.recordUserBidOnItem(item4, new Bid(user3, 20000));
		bidTrackerServiceImpl.recordUserBidOnItem(item4, new Bid(user1, 35000));
		bidTrackerServiceImpl.recordUserBidOnItem(item4, new Bid(user2, 40000));
	}

	@Test
	public void recordUserBidOnItemTest() throws InvalidBidException {

		List<Bid> totalBidsOnItem1 = bidTrackerServiceImpl.getCurrentAuctionRoomCopy().get(item1);

		assertEquals(3, totalBidsOnItem1.size());
	}

	@Test
	public void recordUserBidOnItem_InvalidBidExceptionTest() throws InvalidBidException {

		Assertions.assertThrows(InvalidBidException.class, () -> {
			bidTrackerServiceImpl.recordUserBidOnItem(item2, new Bid(user1, 55000));
			bidTrackerServiceImpl.recordUserBidOnItem(item2, new Bid(user2, 50000));
		});
	}

	@Test
	public void getCurrentWinningBidForItemTest() throws InvalidBidException {

		Bid winningBid = bidTrackerServiceImpl.getCurrentWinningBidForItem(item4);

		assertEquals(40000.0, winningBid.getPrice());

	}

	@Test
	public void getAllBidForItemTest() throws InvalidBidException {

		List<Bid> expectedBids = new LinkedList<Bid>();
		expectedBids.add(new Bid(user1, 25000));
		expectedBids.add(new Bid(user2, 50000));
		expectedBids.add(new Bid(user3, 70000));

		List<Bid> actualBids = bidTrackerServiceImpl.getAllBidForItem(item3);

		assertEquals(expectedBids, actualBids);

	}

	@Test
	public void getAllItemsBidOfUserTest() throws InvalidBidException {

		Set<Item> expectedItemsBidByUser = new HashSet<>();
		expectedItemsBidByUser.add(item1);
		expectedItemsBidByUser.add(item3);
		expectedItemsBidByUser.add(item4);

		Set<Item> actualItemsBidByUser = bidTrackerServiceImpl.getAllItemsBidOfUser(user3);

		assertEquals(expectedItemsBidByUser, actualItemsBidByUser);
	}

}
