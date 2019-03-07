import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {
	private List<MyNodeList> list = new ArrayList<MyNodeList>();
	private Set<String> uni = new HashSet<String>();

	/**
	 * Create a Object.
	 * 
	 * @param
	 */
	public FriendshipGraph() {

	}

	private int addVertex(Person person) {
		try {
			if (uni.contains(person.getName())) {
				throw new Exception("Error: Each person should have a unique name.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		uni.add(person.getName());
		MyNodeList mylist = new MyNodeList();
		Node n = new Node();
		n.per = person;
		mylist.add(n);
		list.add(mylist);
		return list.size();

	}

	private int addEdge(Person p1, Person p2) {
		for (int i = 0; i < list.size(); i++) {
			MyNodeList mylist = new MyNodeList();
			Node n = new Node();
			Node t = new Node();
			mylist = list.get(i);
			n = mylist.getRoot();
			if (n.per == p1) {
				t.per = p2;
				mylist.add(t);
				return mylist.size();
			}
		}
		return -1;
	}

	private int getDistance(Person p1, Person p2) {
		int dis = 0;
		MyNodeList mylist = new MyNodeList();
		Node n = new Node();
		int i = 0;
		if (p1 == p2)
			return 0;
		Person p;
		Map<Person, Integer> visited = new HashMap<Person, Integer>();
		Queue<Person> Q = new LinkedList<Person>();
		for (i = 0; i < list.size(); i++) {
			visited.put(list.get(i).getRoot().per, -1);
		}
		visited.put(p1, 0);
		Q.add(p1);
		while (Q.size() != 0) {
			p = Q.remove();
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getRoot().per == p) {
					mylist = list.get(i);
					n = mylist.getRoot();
					dis = visited.get(n.per) + 1;
					break;
				}
			}
			while (true) {
				if (visited.get(n.per) < 0) {
					visited.put(n.per, dis);
					Q.add(n.per);
				}
				if (n.next) {
					n = mylist.getNext(n);
				} else {
					break;
				}
			}
		}
		return visited.get(p2);
	}

	private class Node {
		public Person per;
		public int index = 1;
		public boolean next = false;
	}

	private class MyNodeList {
		private List<Node> list = new ArrayList<Node>();
		private int size = 0;

		/**
		 * Get MyNodeList's size.
		 * 
		 * @param color new pen color
		 */
		public int size() {
			return size;
		}

		/**
		 * Get first node in MyNodeList.
		 * 
		 * @param
		 */
		public Node getRoot() {
			return list.get(0);
		}

		/**
		 * Add node to MyNodeList.
		 * 
		 * @param The node you want to add.
		 */
		public void add(Node node) {
			size++;
			node.index = size;
			list.add(node);
			for (int i = 0; i < list.size() - 1; i++) {
				list.get(i).next = true;
			}
		}

		/**
		 * Get the next node.
		 * 
		 * @param The current node.
		 * @return The index of the node.
		 */
		public Node getNext(Node node) {
			int index = node.index;
			return list.get(index);
		}
	}

	public static void main(String[] args) {

		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross); // 如果注释掉这一行是-1 -1 0 -1
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}
