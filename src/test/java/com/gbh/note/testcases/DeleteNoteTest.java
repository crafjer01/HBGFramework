package com.gbh.note.testcases;

import org.testng.annotations.Test;

import com.gbh.note.base.BaseTest;

public class DeleteNoteTest extends BaseTest {
	
	@Test
	public void deleteNoteTest() {
		click("noteCreated");
		click("btnDelete");
	}

}
