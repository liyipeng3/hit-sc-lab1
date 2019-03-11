package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T15:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-18T01:00:00Z");
	private static final Instant d5 = Instant.parse("2017-02-17T21:00:00Z");
	private static final Instant d6 = Instant.parse("2015-02-15T11:00:00Z");
	private static final Instant d7 = Instant.parse("2016-01-13T01:30:00Z");
	private static final Instant d8 = Instant.parse("2016-02-25T20:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "@xiaohong is it reasonable to talk about rivest so much? #hype", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@xiaohong@xiaoming rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(3, "green", " @alyssa@alice@bob Do you know rivest's wife?", d3);
	private static final Tweet tweet4 = new Tweet(4, "alyssa", "@bob @alice I don't know what's wrong?", d4);
	private static final Tweet tweet5 = new Tweet(5, "bob", "@alice@alyssa,you should email to me by bob@mit.edu.", d5);
	private static final Tweet tweet6 = new Tweet(6, "alice", " @alyssa Do you like Ross? #Titanic", d6);
	private static final Tweet tweet7 = new Tweet(7, "xiaohong", "@alyssa I extremrely like Jack! #Titanic", d7);
	private static final Tweet tweet8 = new Tweet(8, "xiaoming", "@alyssa Titanic is a good movie! #Titanic", d8);

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
	public void testGuessFollowsGraph() {
		
	}
	
	@Test
	public void testInfluencersSome() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);
		
		influencers = SocialNetwork.influencers(SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8)));
		assertEquals(Arrays.asList("alyssa", "alice", "xiaohong", "xiaoming", "bob", "bbitdiddle", "green"), influencers);
		
		influencers = SocialNetwork.influencers(SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3)));
		assertEquals(7, influencers.size());
	}

}
