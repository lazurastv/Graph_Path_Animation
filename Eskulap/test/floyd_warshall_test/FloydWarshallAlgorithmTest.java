package floyd_warshall_test;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import floyd_warshall.FloydWarshallAlgorithm;
import storage.Hospital;
import storage.Road;
import storage.Map;

public class FloydWarshallAlgorithmTest {
	
	private static FloydWarshallAlgorithm fwl;	
	
	@BeforeClass
	public static void setUpBeforeClass() {
		Hospital hospitals [] = new Hospital[5];
		hospitals[0] = new Hospital(0, "h0", 10, 20, 100, 10);
		hospitals[1] = new Hospital(1, "h1", 10, 20, 100, 10);
		hospitals[2] = new Hospital(2, "h2", 10, 20, 100, 10);
		hospitals[3] = new Hospital(3, "h3", 10, 20, 100, 10);
		hospitals[4] = new Hospital(4, "h4", 10, 20, 100, 10);
		
		Road roads [] = new Road[5];
		roads[0] = new Road(0, 0, 1, 100);
		roads[1] = new Road(1, 0, 2, 90);
		roads[2] = new Road(2, 1, 2, 30);
		roads[3] = new Road(3, 1, 3, 10);
		roads[4] = new Road(4, 2, 3, 15);
		
		Map map = new Map();
		map.setHospitals(hospitals);
		map.setRoads(roads);
		
		fwl = new FloydWarshallAlgorithm(map);
		fwl.applyAlgorithm();
	}

	@Before
	public void Before() {
		fwl.reset();
	}
	
	@Test
	public void resettingShouldReset() {		
		boolean visited [] = fwl.getVisited();
		
		fwl.getClosestVertex(0);
		fwl.reset();
		
		assertArrayEquals(visited, fwl.getVisited()); 
	}
	
	@Test
	public void shouldReturnMinusOne_whenStartIsOutOfBounds() {		
		int expectedId [] = {-1, -1};		
		int actualId [] = {fwl.getClosestVertex(-1), fwl.getClosestVertex(10)};

		assertArrayEquals(expectedId, actualId);
	}
	
	@Test
	public void shouldReturnMinusTwo_whenThereIsNoEdgeConnecting_aVertice() {		
		int expectedId = -2;		
		int actualId = fwl.getClosestVertex(4);

		assertEquals(expectedId, actualId);
	}
	
	@Test
	public void shouldReturnMinusTwo_whenAllVerticesWereVisited() {		
		int expectedId = -2;		
		fwl.getClosestVertex(0);
		fwl.getClosestVertex(0);
		fwl.getClosestVertex(0);
		fwl.getClosestVertex(0);
		int actualId = fwl.getClosestVertex(0);

		assertEquals(expectedId, actualId);
	}
	
	@Test
	public void shouldGetClosestVertex() {		
		int expectedId = 2;
		int actualId = fwl.getClosestVertex(0);

		assertEquals(expectedId, actualId);
	}
	
	@Test
	public void shouldReturnNull_whenThereisNoPath() {		
		assertEquals(null, fwl.getPath(0, 4));
	}
	
	@Test
	public void shouldReturnShortestPath() {		
		int expectedPath [] = {0, 2, 3};
		int actualPath [] = fwl.getPath(0, 3);

		assertArrayEquals(expectedPath, actualPath); 
	}	
	
	@AfterClass
	public static void tearDownAfterClass() {
		fwl = null;	
	}


}
