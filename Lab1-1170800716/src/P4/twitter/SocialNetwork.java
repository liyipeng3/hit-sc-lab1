/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets a list of tweets providing the evidence, not modified by this
	 *               method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		new Extract();
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		Map<String, Set<String>> top = new HashMap<String, Set<String>>();
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		Set<String> set3 = new HashSet<String>();
		for (int i = 0; i < tweets.size(); i++) {
			map.put(tweets.get(i).getAuthor(), set);
			top.put(tweets.get(i).getAuthor(), set);
		}
		for (int i = 0; i < tweets.size(); i++) {
			set1 = map.get(tweets.get(i).getAuthor());
			set2 = Extract.getMentionedUsers(Arrays.asList(tweets.get(i)));
			for (String s : set1) {
				set2.add(s);
			}
			map.put(tweets.get(i).getAuthor(), set2);
		}
		for (int i = 0; i < tweets.size(); i++) {
			set3 = getMentionedTopics(Arrays.asList(tweets.get(i)));
			for (String s : set3) {
				for (int j = i + 1; j < tweets.size(); j++) {
					if (getMentionedTopics(Arrays.asList(tweets.get(j))).contains(s)) {
						set1 = map.get(tweets.get(i).getAuthor());
						set1.add(tweets.get(j).getAuthor());
						map.put(tweets.get(i).getAuthor(), set1);
						set2 = map.get(tweets.get(j).getAuthor());
						set2.add(tweets.get(i).getAuthor());
						map.put(tweets.get(j).getAuthor(), set2);
					}
				}
			}
		}
		return map;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in the
	 * sense that they have the most followers.
	 * 
	 * @param followsGraph a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		int max = -1;
		String most = "";
		int rank = 0;
		List<String> list = new ArrayList<String>();
		new Extract();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String key : followsGraph.keySet()) {
			map.put(key, 0);
		}
		for (Set<String> value : followsGraph.values()) {
			for (String name : value) {
				if (map.containsKey(name)) {
					rank = map.get(name);
					rank = rank + 1;
					map.put(name, rank);
				} else {
					map.put(name, 1);
				}
			}
		}
		while (!map.isEmpty()) {
			max = -1;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > max) {
					max = entry.getValue();
					most = entry.getKey();
				}
			}
			map.remove(most);
			list.add(most);
		}
		return list;
	}

	private static Set<String> getMentionedTopics(List<Tweet> tweets) {
		Set<String> Men = new HashSet<String>();
		Pattern pattern = Pattern.compile("#[a-zA-Z0-9_-]*");

		for (int i = 0; i < tweets.size(); i++) {
			Matcher m = pattern.matcher(tweets.get(i).getText());
			while (m.find()) {
				Men.add(m.group());
			}
		}
		return Men;
	}
}
