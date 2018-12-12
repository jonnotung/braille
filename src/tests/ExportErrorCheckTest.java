package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import authoring.ExportErrorCheck;
import commands.CellCharCommand;
import commands.CellLowerCommand;
import commands.CellRaiseCommand;
import commands.ClearCellCommand;
import commands.GoHereCommand;
import commands.PauseCommand;
import commands.PlayerCommand;
import commands.RepeatButtonCommand;
import commands.SetPinsCommand;
import commands.SetStringCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;
import commands.TTSCommand;
import commands.UserInputCommand;

/**
 * Class which extensively tests the ExportErrorCheck class. If given improper
 * command orders, the application must determine that there is a problem with
 * the given sequence of commands. This set of tests puts the responsible class
 * under several scenarios to tests functionality.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class ExportErrorCheckTest {

	ArrayList<PlayerCommand> commands;

	/**
	 * Tests the checkUserInput static method to see if the application
	 * correctly catches missing User Input commands
	 */
	@Test
	public void testCheckUserInput() {
		commands = new ArrayList<PlayerCommand>();
		// ExportErrorCheck checker = new ExportErrorCheck();

		// Situation where there is a UserInputCommand without a button setup
		// before, should be false
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new UserInputCommand());
		commands.add(new PauseCommand(""));
		assertFalse("test", ExportErrorCheck.checkUserInput(commands));

		// Situation where there is a SkipButtonCommand before UserInput, should
		// be true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new SkipButtonCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new UserInputCommand());
		commands.add(new PauseCommand(""));
		assertTrue("test", ExportErrorCheck.checkUserInput(commands));

		// Situation where there is a RepeatButtonCommand before UserInput,
		// should be true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new RepeatButtonCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new UserInputCommand());
		commands.add(new PauseCommand(""));
		assertTrue("test", ExportErrorCheck.checkUserInput(commands));

		// Situation where there is a RepeatButtonCommand and SkipButton before
		// UserInput, should be true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new RepeatButtonCommand(""));
		commands.add(new SkipButtonCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new UserInputCommand());
		commands.add(new PauseCommand(""));
		assertTrue("test", ExportErrorCheck.checkUserInput(commands));

		// Situation where there is no UserInput, should be true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new RepeatButtonCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new PauseCommand(""));
		assertTrue("test", ExportErrorCheck.checkUserInput(commands));
	}

	/**
	 * Tests that the application correctly detects when braille cells are
	 * referenced, but were never defined.
	 */
	@Test
	public void testCheckCellNumber() {
		commands = new ArrayList<PlayerCommand>();

		// Situation where SetStringCommand sets a string that's too long,
		// should return false
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetStringCommand("longstring"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where SetStringCommand sets a string that's not too long,
		// should return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellCharCommand sets a cell that doesn't exist,
		// should return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellCharCommand("5 a"));
		commands.add(new SetStringCommand("longs"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellCharCommand sets a cell that does exist, should
		// return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellCharCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellLowerCommand sets a cell that doesn't exist,
		// should return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellLowerCommand("5 a"));
		commands.add(new SetStringCommand("longs"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellLowerCommand sets a cell that does exist, should
		// return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellLowerCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellRaiseCommand sets a cell that doesn't exist,
		// should return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellRaiseCommand("5 a"));
		commands.add(new SetStringCommand("longs"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where CellRaiseCommand sets a cell that does exist, should
		// return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new CellRaiseCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where ClearCellCommand sets a cell that doesn't exist,
		// should return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new ClearCellCommand("5 a"));
		commands.add(new SetStringCommand("longs"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where ClearCellCommand sets a cell that does exist, should
		// return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new ClearCellCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where SetPinsCommand sets a cell that doesn't exist, should
		// return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("5 a"));
		commands.add(new SetStringCommand("longs"));
		assertFalse(ExportErrorCheck.checkCellNumber(commands, 5));

		// Situation where SetPinsCommand sets a cell that does exist, should
		// return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		assertTrue(ExportErrorCheck.checkCellNumber(commands, 5));
	}

	/**
	 * Tests that the application correctly detects when buttons are referenced
	 * or used, but were never defined in the settings.
	 */
	@Test
	public void testCheckButtonNumber() {

		// If repeatButton command calls a button that exists, should return
		// true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		commands.add(new RepeatButtonCommand("4"));
		assertTrue(ExportErrorCheck.checkButtonNumber(commands, 5));

		// If repeatButton command calls a button that doesn't exists, should
		// return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		commands.add(new RepeatButtonCommand("5"));
		assertFalse(ExportErrorCheck.checkButtonNumber(commands, 5));

		// If SkipButton command calls a button that exists, should return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		commands.add(new SkipButtonCommand("4 test"));
		assertTrue(ExportErrorCheck.checkButtonNumber(commands, 5));

		// If SkipButton command calls a button that doesn't exists, should
		// return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand(""));
		commands.add(new TTSCommand(""));
		commands.add(new SetPinsCommand("4 a"));
		commands.add(new SetStringCommand("longs"));
		commands.add(new SkipButtonCommand("5 test"));
		assertFalse(ExportErrorCheck.checkButtonNumber(commands, 5));
	}

	/**
	 * Tests that the application correctly catches when a jump command is given
	 * to an impossible (undefined) location
	 */
	@Test
	public void testCheckJumpTag() {
		// When tags and jumps match up, should return true
		commands = new ArrayList<PlayerCommand>();
		commands.add(new GoHereCommand("test1"));
		commands.add(new SkipCommand("test1"));
		commands.add(new SkipButtonCommand("1 test1"));
		assertTrue(ExportErrorCheck.checkJumpTags(commands));

		// When there are jumps where tags don't exist, should return false
		commands = new ArrayList<PlayerCommand>();
		commands.add(new SkipButtonCommand("1 test2"));
		commands.add(new GoHereCommand("test1"));
		commands.add(new SkipCommand("test1"));
		commands.add(new SkipButtonCommand("1 test1"));
		commands.add(new SkipCommand("test2"));
		assertFalse(ExportErrorCheck.checkJumpTags(commands));
	}
}
