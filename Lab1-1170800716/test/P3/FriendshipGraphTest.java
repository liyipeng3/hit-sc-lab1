import static org.junit.Assert.*;
import java.lang.reflect.*;

import org.junit.Test;

public class FriendshipGraphTest {
	private final Method methods[] = FriendshipGraph.class.getDeclaredMethods();

	public Object addVertex(FriendshipGraph graph, Person person) throws Exception {
		Object result = null;
		for (int i = 0; i < methods.length; ++i) {
			if (methods[i].getName().equals("addVertex")) {
				methods[i].setAccessible(true);
				Object para[] = { person };
				result = methods[i].invoke(graph, para);
				return result;
			}
		}
		return result;
	}

	public Object addEdge(FriendshipGraph graph, Person person1, Person person2) throws Exception {
		Object result = null;
		for (int i = 0; i < methods.length; ++i) {
			if (methods[i].getName().equals("addEdge")) {
				methods[i].setAccessible(true);
				Object para[] = { person1, person2 };
				result = methods[i].invoke(graph, para);
				return result;
			}
		}
		return result;
	}

	public Object getDistance(FriendshipGraph graph, Person person1, Person person2) throws Exception {
		Object result = null;
		for (int i = 0; i < methods.length; ++i) {
			if (methods[i].getName().equals("getDistance")) {
				methods[i].setAccessible(true);
				Object para[] = { person1, person2 };
				result = methods[i].invoke(graph, para);
				return result;
			}
		}
		return result;
	}

	@Test
	public void addVertexTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person A = new Person("A");
		Person B = new Person("B");
		Person C = new Person("C");
		Person D = new Person("D");
		Person E = new Person("E");
		Person F = new Person("F");
		Person G = new Person("G");
		assertEquals(1, addVertex(graph, A));
		assertEquals(2, addVertex(graph, B));
		assertEquals(3, addVertex(graph, C));
		assertEquals(4, addVertex(graph, D));
		assertEquals(5, addVertex(graph, E));
		assertEquals(6, addVertex(graph, F));
		assertEquals(7, addVertex(graph, G));
	}

	@Test
	public void addEdgeTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person A = new Person("A");
		Person B = new Person("B");
		Person C = new Person("C");
		Person D = new Person("D");
		Person E = new Person("E");
		Person F = new Person("F");
		Person G = new Person("G");
		addVertex(graph, A);
		addVertex(graph, B);
		addVertex(graph, C);
		addVertex(graph, D);
		addVertex(graph, E);
		addVertex(graph, F);
		addVertex(graph, G);
		assertEquals(2, addEdge(graph, A, D));
		assertEquals(2, addEdge(graph, D, A));
		assertEquals(3, addEdge(graph, A, C));
		assertEquals(2, addEdge(graph, C, A));
		assertEquals(2, addEdge(graph, B, C));
		assertEquals(3, addEdge(graph, C, B));
		assertEquals(2, addEdge(graph, E, C));
		assertEquals(4, addEdge(graph, C, E));
		assertEquals(5, addEdge(graph, C, G));
		assertEquals(2, addEdge(graph, G, C));
	}

	@Test
	public void getDistanceTest() throws Exception {

		FriendshipGraph graph = new FriendshipGraph();
		Person A = new Person("A");
		Person B = new Person("B");
		Person C = new Person("C");
		Person D = new Person("D");
		Person E = new Person("E");
		Person F = new Person("F");
		Person G = new Person("G");
		addVertex(graph, A);
		addVertex(graph, B);
		addVertex(graph, C);
		addVertex(graph, D);
		addVertex(graph, E);
		addVertex(graph, F);
		addVertex(graph, G);
		addEdge(graph, A, D);
		addEdge(graph, D, A);
		addEdge(graph, A, C);
		addEdge(graph, C, A);
		addEdge(graph, B, C);
		addEdge(graph, C, B);
		addEdge(graph, E, C);
		addEdge(graph, C, E);
		addEdge(graph, C, G);
		addEdge(graph, G, C);
		assertEquals(2, getDistance(graph, A, G));
		assertEquals(1, getDistance(graph, B, C));
		assertEquals(3, getDistance(graph, D, G));
		assertEquals(0, getDistance(graph, C, C));
		assertEquals(-1, getDistance(graph, B, F));
		assertEquals(2, getDistance(graph, E, B));
		assertEquals(2, getDistance(graph, B, E));
	}

}
