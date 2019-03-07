/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of every
	 *         tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		Instant end = Instant.parse("1970-01-01T00:00:00Z");
		Instant start = Instant.now();
		for (int i = 0; i < tweets.size(); i++) {
			if (tweets.get(i).getTimestamp().isAfter(end)) {
				end = tweets.get(i).getTimestamp();
			}
			if (start.isAfter(tweets.get(i).getTimestamp())) {
				start = tweets.get(i).getTimestamp();
			}
		}
		Timespan timespan = new Timespan(start, end);
		return timespan;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a Twitter username (as defined by
	 *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
	 *         preceded or followed by any character valid in a Twitter username.
	 *         For this reason, an email address like bitdiddle@mit.edu does NOT
	 *         contain a mention of the username mit. Twitter usernames are
	 *         case-insensitive, and the returned set may include a username at most
	 *         once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		Set<String> Men = new HashSet<String>();
		Pattern pattern1 = Pattern.compile("[a-zA-Z0-9_-]{0,}@[a-zA-Z0-9_-]{0,}");
		Pattern pattern2 = Pattern.compile("[a-zA-Z0-9_-]{1,}@");

		for (int i = 0; i < tweets.size(); i++) {
			Matcher m = pattern1.matcher(tweets.get(i).getText());
			while (m.find()) {
				if (pattern2.matcher(m.group()).find()) {

				} else {
					Men.add(m.group().replace("@", ""));
				}
			}
		}
		return Men;
	}

}
