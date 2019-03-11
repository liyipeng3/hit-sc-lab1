/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T15:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-18T01:00:00Z");
	private static final Instant d5 = Instant.parse("2017-02-17T21:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "@bob is it reasonable to talk about rivest so much?",d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa@bob rivest talk in 30 minutes", d2);
	private static final Tweet tweet3 = new Tweet(3, "green", " @alyssa Do you know rivest's wife?", d3);
	private static final Tweet tweet4 = new Tweet(4, "alyssa", "@green@bob I don't know what's wrong?", d4);
	private static final Tweet tweet5 = new Tweet(5, "bob","You are so skin-deep!@alyssa,you should email to me by bob@mit.edu.", d5);

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	@Test
	public void testGuessFollowsGraphSome() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
		Set<String> set = new HashSet<String>();
		set.add("green");
		set.add("bob");

		assertEquals(set, followsGraph.get("alyssa"));
		assertEquals(2, followsGraph.get("alyssa").size());
		assertEquals(2, followsGraph.get("bbitdiddle").size());
		assertEquals(1, followsGraph.get("green").size());
		assertEquals(1, followsGraph.get("bob").size());

		followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet2, tweet3, tweet1, tweet5));

		assertEquals(set, followsGraph.get("alyssa"));
		assertEquals(2, followsGraph.get("alyssa").size());
		assertEquals(2, followsGraph.get("bbitdiddle").size());
		assertEquals(1, followsGraph.get("green").size());
		assertEquals(1, followsGraph.get("bob").size());
		
		followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet4));
		assertEquals(set, followsGraph.get("alyssa"));
		set.clear();
		set.add("bob");
		set.add("alyssa");
		assertEquals(set, followsGraph.get("bbitdiddle"));

	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue("expected empty list", influencers.isEmpty());
	}

	@Test
	public void testInfluencersSome() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);
		
		influencers = SocialNetwork.influencers(SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5)));
		assertEquals(Arrays.asList("alyssa", "bob", "green", "bbitdiddle"), influencers);
		
		influencers = SocialNetwork.influencers(SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2)));
		assertEquals(Arrays.asList("bob", "alyssa", "bbitdiddle"), influencers);
		
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO NOT
	 * strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in SocialNetwork, because that means you're testing a stronger
	 * spec than SocialNetwork says. If you need such helper methods, define them in
	 * a different class. If you only need them in this test class, then keep them
	 * in this test class.
	 */

}
