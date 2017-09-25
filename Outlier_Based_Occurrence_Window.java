/**
 *
 * @author Himel Dev
 */
import java.util.*;

public class Outlier_Based_Occurrence_Window 
{
    public static void main(String[] args) 
    {
        Outlier_Based_Occurrence_Window instance = new Outlier_Based_Occurrence_Window();
        ArrayList<Integer> example_sequence = new ArrayList<>(Arrays.asList(1, 1, 2, 4, 5, 3, 2, 5, 2, 1));
        ArrayList<Integer> example_pattern = new ArrayList<>(Arrays.asList(1, 2, 3));
        int ret[] = instance.get_minimum_outlier_window(example_sequence, example_pattern);
        if(ret[0] == 1)
            System.out.println(ret[1]+" "+ret[2]+" "+ret[3]);
    }
    
    int[] get_minimum_outlier_window(ArrayList<Integer> sequence, ArrayList<Integer> pattern) 
    {
        int sequence_length = sequence.size();
        int pattern_length = pattern.size();
        
        int window_start = 0;
        int window_end = sequence_length; 
        
        int pattern_element_tally[] = new int [pattern_length];
        
        int member_count = 0;
        int outlier_count = 0;
        
        int minimum_outlier_count = sequence_length;
        
        for (int start = 0, end = 0; end < sequence_length; end++) 
        {
            // Check if the last event of current window is a pattern element
            int position = -1;
            for(int i = 0; i < pattern_length; i++)
            {
                if(sequence.get(end) - pattern.get(i) == 0)
                {
                    position = i;
                    break;
                }
            }
            
            // If the last event of current window is not a pattern element, increment number of outliers and expand the window
            if (position == -1)
            {
                outlier_count++;
                continue;
            }
            
            // If the last event of current window is a pattern element, check if the window has covered any new pattern element
            pattern_element_tally[position]++;
            if (pattern_element_tally[position] == 1)
                member_count++; 
            
            // If current window has covered all elements of pattern, shift the window's starting boundary to make it as compact as possible
            // Also, make appropriate adjustments to number of outliers and tally of pattern elements
            if (member_count == pattern_length) 
            {
                //System.out.println("Start: "+start+" "+end);
                position = -1;
                for(int i = 0; i < pattern_length; i++)
                {
                    if(sequence.get(start) - pattern.get(i) == 0)
                        position = i;
                }

                while (position == -1 || pattern_element_tally[position] > 1) 
                {
                    start++;
                    
                    if (position != -1)
                        pattern_element_tally[position]--;
                    else
                        outlier_count--;
                    
                    position = -1;
                    for(int i = 0; i < pattern_length; i++)
                    {
                        if(sequence.get(start) - pattern.get(i) == 0)
                            position = i;
                    }
                }
                //System.out.println("End: "+start+" "+end);
                if (outlier_count < minimum_outlier_count) 
                {
                    window_start = start;
                    window_end = end;
                    minimum_outlier_count = outlier_count;
                }
            }
        }
        int ret[] = new int[4];
        
        if(member_count == pattern_length) 
            ret[0] = 1;
        else 
            ret[0] = 0;
        
        ret[1] = window_start;
        ret[2] = window_end;
        ret[3] = minimum_outlier_count;
        
        return ret;        
    }    
}
