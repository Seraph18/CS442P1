package assignment02;
import java.util.Iterator;

import java.util.HashSet;
import java.util.Set;

public abstract class Decorator extends Component {
	private Component delegate;
	
	public Decorator(Component delegateIn) {
		delegate = delegateIn;
	}

	public abstract Set<Character> getConsonantsInWord();
	public abstract Set<Character> getVowelsInWord();	
	
	@Override
	public Set<Character> getConsonants() {
		// TODO Auto-generated method stub
		var consonants = delegate.getConsonants();
        
		for (Character c : getConsonantsInWord()) {
			consonants.add(c);
			break;
		}
		return consonants;
	}

	@Override
	public Set<Set<Character>> getVowels() {
		// TODO Auto-generated method stub
		// Existing vowel combinations
		var existingCombinations = delegate.getVowels();
		// Vowels from the current word
		var currentWordVowels = getVowelsInWord();
		
		// If the current word has no vowels, return the existing combinations
		if (currentWordVowels.isEmpty()) {
			return existingCombinations;
		}
	
		// Prepare a new set to hold updated combinations
		var updatedCombinations = new HashSet<Set<Character>>();
		int max = 0; // Track the maximum size of the vowel sets
	
		// If there are no existing combinations, initialize the process
		if (existingCombinations.isEmpty()) {
			// Directly add each vowel as a new set
			for (Character vowel : currentWordVowels) {
				var singleVowelSet = new HashSet<Character>();
				singleVowelSet.add(vowel);
				updatedCombinations.add(singleVowelSet);
			}
			max = 1; // Since we're adding individual vowels, the max size is 1
		} else {
			// Expand existing combinations with vowels from the current word
			for (Set<Character> set : existingCombinations) {
				for (Character vowel : currentWordVowels) {
					var newCombination = new HashSet<Character>(set);
					newCombination.add(vowel);
					max = Math.max(max, newCombination.size());
					updatedCombinations.add(newCombination);
				}
			}
		}
	
		// Remove sets that are not of the maximum size to ensure each set has vowels from all words
		Iterator<Set<Character>> iter = updatedCombinations.iterator();
		while (iter.hasNext()) {
			var set = iter.next();
			if (set.size() < max) {
				iter.remove();
			}
		}
	
		// Return the updated combinations
		return updatedCombinations;
	}

	@Override
	public void print() {
		delegate.print();
	}}
