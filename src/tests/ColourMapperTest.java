package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import authoring.ColourMapper;
import commands.GoHereCommand;
import commands.PlayerCommand;
import commands.SetStringCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;
import commands.TTSCommand;
import commands.UserInputCommand;

/**
 * Test suite to verify the behaviour of the colour mapper. The colour mapper must map location tags to colours in order to make the UI more readable.
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-05
 */
public class ColourMapperTest {
	//list with 3 different location tags
	ArrayList<PlayerCommand> commandsFull = new ArrayList<PlayerCommand>();
	//list with no location tags
	ArrayList<PlayerCommand> commandsNoSkip =  new ArrayList<PlayerCommand>();

	/**
	 * Setup commands, generates the lists in order to pass them through the mapper later
	 */
	@Before
	public void setUp() {
		commandsFull.add(new SkipCommand("one"));
		commandsFull.add(new GoHereCommand("one"));
		commandsFull.add(new SkipButtonCommand("1 one"));
		commandsFull.add(new TTSCommand("two"));
		commandsFull.add(new UserInputCommand());
		commandsFull.add(new GoHereCommand("two"));
		commandsFull.add(new TTSCommand("one"));
		commandsFull.add(new SkipCommand("two"));
		commandsFull.add(new SetStringCommand("three"));
		commandsFull.add(new SkipCommand("three"));
		//test colour cycling
		for(int i = 0; i<11; i++) {
			commandsFull.add(new SkipCommand("" + i));
		}

		commandsNoSkip.add(new TTSCommand("one"));
		commandsNoSkip.add(new TTSCommand("two"));
		commandsNoSkip.add(new SetStringCommand("three"));
		commandsNoSkip.add(new UserInputCommand());
	}

	/**
	 * Basic test to ensure that colors are mapped when required
	 */
	@Test
	public void test() {
		ColourMapper colourMap_full = new ColourMapper();
		ColourMapper colourMap_noSkip = new ColourMapper();

		colourMap_full.addColourMapping(commandsFull);
		colourMap_noSkip.addColourMapping(commandsNoSkip);

		assertTrue(colourMap_noSkip.colourMap.isEmpty());
		assertEquals(Color.blue, colourMap_full.getColour("one"));
		assertEquals(Color.cyan, colourMap_full.getColour("two"));
		assertEquals(Color.red, colourMap_full.getColour("three"));
		//test colour cycling
		assertEquals(Color.magenta, colourMap_full.getColour("1"));
		assertEquals(Color.orange, colourMap_full.getColour("2"));
		assertEquals(Color.yellow, colourMap_full.getColour("3"));
		assertEquals(Color.gray, colourMap_full.getColour("4"));
		assertEquals(Color.lightGray, colourMap_full.getColour("5"));
		assertEquals(Color.pink, colourMap_full.getColour("6"));
		assertEquals(Color.darkGray, colourMap_full.getColour("7"));
		assertEquals(Color.blue, colourMap_full.getColour("8"));
		assertEquals(Color.cyan, colourMap_full.getColour("9"));
		assertEquals(Color.red, colourMap_full.getColour("10"));
	}

}
