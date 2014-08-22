/*
{"Constance", "Bertha", "Alice"}
{"aaba", "baab", "aaaa"}
{"Chip", "Biff", "Abe"}
{"bbaa", "baaa", "aaab"}
"Bertha"
Returns: "Biff"

Alice has two answers in common with Chip and three answers in common with both
Abe and Biff; Abe gets lexicographic preference. Bertha also has two answers in
common with Chip and three answers in common with both Abe and Biff. 
Since Abe has already been matched to Alice, Bertha lands Biff.


{"Constance", "Bertha", "Alice"}
{"aaba", "baab", "aaaa"}
{"Chip", "Biff", "Abe"}
{"bbaa", "baaa", "aaab"}
"Constance"

Returns: "Chip"
We are dealing with the same names and answers as before. Constance is the last
to go. Although she has two answers in common with Abe and Biff, they are both
taken. She ends up with Chip, with whom she has only one answer in common.
    	
{"Constance", "Alice", "Bertha", "Delilah", "Emily"}
{"baabaa", "ababab", "aaabbb", "bababa", "baabba"}
{"Ed", "Duff", "Chip", "Abe", "Biff"}
{"aabaab", "babbab", "bbbaaa", "abbbba", "abaaba"}
"Constance"

Returns: "Duff"*/

//package com.pe.interview;

import java.util.*;

public class MatchMaking 
{

    /**
     *  Sort the names in to lexigraphical order and maintain the answers.
     **/
    public static String[][] sortNamesAnswers(String[] names, String[] answers)
    {
        int col = 0, row = 0;
        String[] sortNames = names.clone();
    
        Arrays.sort(sortNames);
        
        String[][] pair = new String[2][names.length];
            
        for(int i = 0; i < sortNames.length; i++)
        {
        
            for(int j = 0; j < names.length; j++) 
            {
                if( sortNames[i].equals(names[j]) )
                {
                    pair[row][col] = names[j];
                    pair[row + 1][col] = answers[j];
                       
                    col++;
                }
            }
        }
     
        return pair;

    }//end sortNamesAnswers()       
    

    /**
     *  return a man that matches the queryWoman
     **/
    public static String makeMatch(String[] namesWomen, String[] answersWomen, 
                                   String[] namesMen,   String[] answersMen, String queryWoman){

        int currMatch = 0, maxMatch = 0, row = 0, rowMatch = 0, colMatch = 0, manIndex = 0;

        String man = "NO ONE!";

        String[] currAnswerWomen, currAnswerMen;

        String[][] pairWomen = sortNamesAnswers(namesWomen, answersWomen);
        String[][] pairMen = sortNamesAnswers(namesMen, answersMen);
        String[][] pairMatch = new String[2][pairWomen[0].length];

        //select the first woman from the array
        for(int i = 0; i < pairWomen[row].length; i++)
        {
            currAnswerWomen = new String[pairWomen[row + 1][i].length() ];
            currAnswerMen = new String[pairWomen[row + 1][i].length() ];

            //fill an array with the current womans answers
            for(int j = 0; j < pairWomen[row + 1][i].length(); j++)
            {
                currAnswerWomen[j] = String.valueOf( pairWomen[row + 1][i].charAt(j) );
    
            }

            //compare current womans answers to next available mens answers 
            maxMatch = 0;
            for(int m = 0; m < pairMen[row].length; m++)
            {
                currMatch = 0;//reset the current mens total to 0                               
            
                if( !(pairMen[row + 1][m].equals("X")) )
                {
                    //fill mens answers to an array
                    for(int n = 0; n < pairMen[row + 1][m].length(); n++)
                    {
                        currAnswerMen[n] = String.valueOf( pairMen[row + 1][m].charAt(n) );
                    }//n

                    //start looping through both answer arrays and count number of matches
                    //highest match count wins!
                    for(int n = 0; n < currAnswerWomen.length; n++)
                    {
                        
                        if(currAnswerWomen[n].equals( currAnswerMen[n] ) )
                        {
                            currMatch++;
                        }
                        
                    }//n
                    if(currMatch > maxMatch)
                    {
                        maxMatch = currMatch;
                        pairMatch[rowMatch][colMatch] = pairWomen[row][i];//womans name
                        pairMatch[rowMatch + 1][colMatch] = pairMen[row][m];//mens name

                        if(pairWomen[row][i].equals(queryWoman))
                        {
                            man = pairMen[row][m];//assign current man for return value
                        }
                        pairMen[row + 1][m] = "X";//man is matched with a woman
                        colMatch++;
                    }
                    
                }//end if

            }//end for m

        }//end for i

        
        return man;
    }//end makeMatch()


    public static void main(String args[])
    { 

        ///////////////////////////////////////////////////EXAMPLE 1//////////////////////////////////////////////////////////////

        String[] namesWomen0 = {"Constance", "Bertha", "Alice"};
        String[] answersWomen0 = {"aaba", "baab", "aaaa"};
        String[] namesMen0 = {"Chip", "Biff", "Abe"};
        String[] answersMen0 = {"bbaa", "baaa", "aaab"};

        String name0 = "Bertha";

        System.out.println( name0 + " is matched with: " + makeMatch(namesWomen0, answersWomen0, namesMen0, answersMen0, name0) );

        ///////////////////////////////////////////////////EXAMPLE 2//////////////////////////////////////////////////////////////

        String[] namesWomen1 = {"Constance", "Bertha", "Alice"};
        String[] answersWomen1 = {"aaba", "baab", "aaaa"};
        String[] namesMen1 = {"Chip", "Biff", "Abe"};
        String[] answersMen1 = {"bbaa", "baaa", "aaab"};

        String name1 = "Constance";

        System.out.println( name1 + " is matched with: " + makeMatch(namesWomen1, answersWomen1, namesMen1, answersMen1, name1) );

        ///////////////////////////////////////////////////EXAMPLE 3//////////////////////////////////////////////////////////////

        String[] namesWomen2 = {"Constance", "Alice", "Bertha", "Delilah", "Emily"};
        String[] answersWomen2 = {"baabaa", "ababab", "aaabbb", "bababa", "baabba"};
        String[] namesMen2 = {"Ed", "Duff", "Chip", "Abe", "Biff"};
        String[] answersMen2 = {"aabaab", "babbab", "bbbaaa", "abbbba", "abaaba"};

        String name2 = "Constance";

        System.out.println( name2 + " is matched with: " + makeMatch(namesWomen2, answersWomen2, namesMen2, answersMen2, name2) );

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }//end main()

}//end class
