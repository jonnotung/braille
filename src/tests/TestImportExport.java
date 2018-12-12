package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import authoring.ColourMapper;
import commands.CellCharCommand;
import commands.PlayerCommand;
import commands.RepeatCommand;
import listeners.ExportListener;
import listeners.ImportListener;
/**
 * This Junit test class tests all methods in ExportListener and ImportListener classes
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-15
 */
public class TestImportExport {
	/**
	 * Test all methods in ExportListener
	 */
	@Test
	public void testExportSimple() {
		List<PlayerCommand> list = new ArrayList<>();

		list.add(new CellCharCommand("a"));
		list.add(new RepeatCommand("Test repeat"));

		ExportListener el = new ExportListener(null);

		String result = el.parseCommands(list);
		String expectedResult = "/~disp-cell-char:a\n/~repeat\nTest repeat\n/~endrepeat";

		// Trim newlines from the end, ensure they're equal still
		assertEquals(result.trim(), expectedResult.trim());
	}
	/**
	 * Test all methods in ImportListener
	 */
	@Test
	public void testImportSimple() {
		String inputText = "Cell 1\nButton 2\nTitle\n/~disp-cell-char:a\n/~repeat\nTest repeat\n/~endrepeat";

		ImportListener il = new ImportListener(null, new ColourMapper());

		List<PlayerCommand> result = il.parseString(Arrays.asList(inputText.split("\n")));

		List<PlayerCommand> expectedResult = new ArrayList<>();

		expectedResult.add(new CellCharCommand("a"));
		expectedResult.add(new RepeatCommand("Test repeat"));

		// Ensure they're equal length
		assertEquals(result.size(), expectedResult.size());

		// Go item-by-item, needs to be in same order
		for (int i = 0; i < result.size(); i++) {
			PlayerCommand resultPC = result.get(i);
			PlayerCommand expectPC = expectedResult.get(i);

			assertEquals(resultPC.getClass(), expectPC.getClass());
			assertEquals(resultPC.getCurrentValue(), expectPC.getCurrentValue());
		}
	}
}



