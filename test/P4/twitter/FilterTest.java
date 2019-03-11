/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T15:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-18T01:00:00Z");
	private static final Instant d5 = Instant.parse("2017-02-17T21:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(3, "green", " @alyssa Do you know rivest's wife?", d3);
	private static final Tweet tweet4 = new Tweet(4, "alyssa", "@green@bob I don't know what's wrong?", d4);
	private static final Tweet tweet5 = new Tweet(5, "bob",
			"You are so skin-deep!@alyssa,you should email to me by bob@mit.edu.", d5);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testWrittenByMultipleTweetsSingleResult() {
		List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "alyssa");

		assertEquals("expected singleton list", 2, writtenBy.size());
		assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
		assertEquals(Arrays.asList(tweet1, tweet4),
				Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "alyssa"));
		assertEquals(Arrays.asList(tweet4, tweet1),
				Filter.writtenBy(Arrays.asList(tweet4, tweet2, tweet3, tweet1, tweet5), "alyssa"));
		assertEquals(Arrays.asList(tweet2),
				Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "bbitdiddle"));
		assertEquals(Arrays.asList(tweet3),
				Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "green"));
		assertEquals(Arrays.asList(tweet5),
				Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "bob"));
	}

	@Test
	public void testInTimespanMultipleTweetsMultipleResults() {
		Instant testStart1 = Instant.parse("2016-02-17T09:00:00Z");
		Instant testEnd1 = Instant.parse("2016-02-17T12:00:00Z");
		Instant testStart2 = Instant.parse("2016-02-17T13:00:00Z");
		Instant testEnd2 = Instant.parse("2016-12-17T12:00:00Z");
		Instant testStart3 = Instant.parse("2015-02-17T13:00:00Z");
		Instant testEnd3 = Instant.parse("2018-12-17T12:00:00Z");

		List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart1, testEnd1));

		assertFalse("expected non-empty list", inTimespan.isEmpty());
		assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
		assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
		assertEquals(Arrays.asList(tweet3, tweet4), Filter
				.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), new Timespan(testStart2, testEnd2)));
		assertEquals(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Filter
				.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), new Timespan(testStart3, testEnd3)));
		assertEquals(Arrays.asList(tweet5, tweet2, tweet3, tweet1, tweet4), Filter
				.inTimespan(Arrays.asList(tweet5, tweet2, tweet3, tweet1, tweet4), new Timespan(testStart3, testEnd3)));

	}

	@Test
	public void testContaining() {
		List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

		assertFalse("expected non-empty list", containing.isEmpty());
		assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
		assertEquals("expected same order", 0, containing.indexOf(tweet1));
		assertEquals(Arrays.asList(tweet1, tweet5),
				Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("to")));
		assertEquals(Arrays.asList(tweet1, tweet5),
				Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("TO")));
		assertEquals(Arrays.asList(tweet3, tweet4),
				Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("Know")));
		assertEquals(Arrays.asList(tweet3, tweet4, tweet5),
				Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("Know", "You")));
		assertEquals(Arrays.asList(tweet5, tweet3, tweet4),
				Filter.containing(Arrays.asList(tweet5, tweet3, tweet2, tweet4, tweet1), Arrays.asList("Know", "You")));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Filter
	 * class that follows the spec. It will be run against several staff
	 * implementations of Filter, which will be done by overwriting (temporarily)
	 * your version of Filter with the staff's version. DO NOT strengthen the spec
	 * of Filter or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Filter, because that means you're testing a stronger spec
	 * than Filter says. If you need such helper methods, define them in a different
	 * class. If you only need them in this test class, then keep them in this test
	 * class.
	 */

}
