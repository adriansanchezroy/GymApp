package test;

import datacenter.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.IOException;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests supplementaires qui verifient plus que les diagrammes.
 */
public class AccountingTest {
	@BeforeClass
	public static void accountingTest() throws IOException {
		// Simuler l'input d'un client et des agents
		String test = "1" + System.lineSeparator() + "3" + System.lineSeparator() + "Adrian" + System.lineSeparator()
				+ "1234567981" + System.lineSeparator() + "adriansanchezroy@gmail.com" + System.lineSeparator() + "test"
				+ System.lineSeparator() + "tt" + System.lineSeparator() + "test" + System.lineSeparator() + "tteest"
				+ System.lineSeparator() + "1" + System.lineSeparator() + "3" + System.lineSeparator() + "Dominique"
				+ System.lineSeparator() + "1234567981" + System.lineSeparator() + "dominiqueducharme@gmail.com"
				+ System.lineSeparator() + "test" + System.lineSeparator() + "tt" + System.lineSeparator() + "test"
				+ System.lineSeparator() + "tteest" + System.lineSeparator() + "2" + System.lineSeparator() + "3"
				+ System.lineSeparator() + "Simon" + System.lineSeparator() + "1234567981" + System.lineSeparator()
				+ "test@Test.com" + System.lineSeparator() + "test" + System.lineSeparator() + "tt"
				+ System.lineSeparator() + "test" + System.lineSeparator() + "tteest" + System.lineSeparator() + "1"
				+ System.lineSeparator() + "1" + System.lineSeparator() + "dominiqueducharme@gmail.com"
				+ System.lineSeparator() + "6" + System.lineSeparator() + "test" + System.lineSeparator()
				+ LocalDate.now().toString() + System.lineSeparator() + LocalDate.now().plusDays(50).toString()
				+ System.lineSeparator() + "15:00" + System.lineSeparator() + "1234567" + System.lineSeparator() + "30"
				+ System.lineSeparator() + "47" + System.lineSeparator() + "Wow" + System.lineSeparator() + "0"
				+ System.lineSeparator() + "2" + System.lineSeparator() + "1" + System.lineSeparator()
				+ "adriansanchezroy@gmail.com" + System.lineSeparator() + "2" + System.lineSeparator() + "001"
				+ System.lineSeparator() + "o" + System.lineSeparator() + "n" + System.lineSeparator() + "3"
				+ System.lineSeparator() + "1" + System.lineSeparator() + "test@Test.com" + System.lineSeparator() + "2"
				+ System.lineSeparator() + "001" + System.lineSeparator() + "o" + System.lineSeparator() + "n"
				+ System.lineSeparator() + "3" + System.lineSeparator() + "1" + System.lineSeparator()
				+ "dominiqueducharme@gmail.com" + System.lineSeparator() + "2" + System.lineSeparator() + "o"
				+ System.lineSeparator() + "001" + System.lineSeparator() + "000000001" + System.lineSeparator() + "2"
				+ System.lineSeparator() + "o" + System.lineSeparator() + "001" + System.lineSeparator() + "000000003"
				+ System.lineSeparator() + "0" + System.lineSeparator() + "1" + System.lineSeparator() + "2"
				+ System.lineSeparator() + "4" + System.lineSeparator() + "0" + System.lineSeparator() + "0";

		// System.setIn(InputStream in): Reallocate the "standard" input stream.
		System.setIn(new ByteArrayInputStream(test.getBytes()));

		DataCenter.main(null);
	}

	@Test
	public void memberBillTest() {
		String fileName = "Membres_Factures\\Adrian_" + LocalDate.now() + ".txt";
		File fileToCheck = new File(fileName);
		assertTrue(fileToCheck != null);
		fileName = "Membres_Factures\\Simon_" + LocalDate.now() + ".txt";
		fileToCheck = new File(fileName);
		assertTrue(fileToCheck.exists());

	}

	@Test
	public void noticeTest() {
		String fileName = "Professional_Notice\\Dominique_" + LocalDate.now() + ".txt";
		File fileToCheck = new File(fileName);
		assertTrue(fileToCheck != null);
	}

	@Test
	public void managerRepportTest() {
		String fileName = "Rapport_Gérant\\Rapport_Gérant_" + LocalDate.now() + ".txt";
		File fileToCheck = new File(fileName);
		assertTrue(fileToCheck.exists());
	}
}
