package edu.njit.cs114;

import java.io.IOException;
import java.util.*;

/**
 * Author: Ashalesh Tilawat
 * Date created: 3/31/23
 */
public class AnagramFinderListImpl extends AbstractAnagramFinder {

    private List<WordArrPair> wordArrList = new ArrayList<>();

    private class WordArrPair implements Comparable<WordArrPair> {
        public final String word;
        public final char [] charArr;

        public WordArrPair(String word) {
            this.word = word;
            charArr = word.toCharArray();
            Arrays.sort(charArr);
        }

        public boolean isAnagram(WordArrPair wordArrPair) {
            return Arrays.equals(this.charArr, wordArrPair.charArr);
        }

        @Override
        public int compareTo(WordArrPair wordArrPair) {
            return this.word.compareTo(wordArrPair.word);
        }


    }


    @Override
    public void clear() {
        wordArrList.clear();
    }

    @Override
    public void addWord(String word) {
        if (!wordArrList.contains(word)) {
            wordArrList.add(new WordArrPair(word));
        }
    }

    @Override
    public List<List<String>> getMostAnagrams() {
        /**
         * To be completed
         * Repeatedly do this:
         * (a)Each wordPair in the list is compared to others to identify all its anagrams;
         * (b) add the anagram words to the same group;
         *      if there are no anagrams, single word forms a group
         * (c) remove these words from wordArrList
         */
        Collections.sort(wordArrList);
        ArrayList<List<String>> mostAnagramsList = new ArrayList<>();
        int maxAnagrams = 0;

        while (!wordArrList.isEmpty()) {
            WordArrPair wordPair = wordArrList.get(0);
            List<String> anagramGroup = new ArrayList<>();
            Iterator<WordArrPair> iterator = wordArrList.iterator();

            while (iterator.hasNext()) {
                WordArrPair currentWordPair = iterator.next();
                if (wordPair.isAnagram(currentWordPair)) {
                    anagramGroup.add(currentWordPair.word);
                    iterator.remove();
                }
            }

            if (anagramGroup.size() > maxAnagrams) {
                maxAnagrams = anagramGroup.size();
                mostAnagramsList.clear();
                mostAnagramsList.add(anagramGroup);
            } else if (anagramGroup.size() == maxAnagrams) {
                mostAnagramsList.add(anagramGroup);
            }
        }

        return mostAnagramsList;
    }

    public static void main(String [] args) {
        AnagramFinderListImpl finder = new AnagramFinderListImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords=0;
        try {
            nWords = finder.processDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace ();
        }
        List<List<String>> anagramGroups = finder.getMostAnagrams();
        long estimatedTime = System.nanoTime () - startTime ;
        double seconds = ((double) estimatedTime /1000000000) ;
        System.out.println("Number of words : " + nWords);
        System.out.println("Number of groups of words with maximum anagrams : "
                + anagramGroups.size());
        if (!anagramGroups.isEmpty()) {
            System.out.println("Length of list of max anagrams : " + anagramGroups.get(0).size());
            for (List<String> anagramGroup : anagramGroups) {
                System.out.println("Anagram Group : "+ anagramGroup);
            }
        }
        System.out.println ("Time (seconds): " + seconds);

    }
}
