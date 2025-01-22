package com.example;

import java.util.ArrayList;
import java.util.List;

public class DialogueStep {
		private static int id=0;
		private String line;
		private List<String> player_options = new ArrayList<String>();
		private List<DialogueStep> next_steps = new ArrayList<DialogueStep>();
	
		public int getId(){
				return id;
		}

}
