public class Sorter {
    
    public static void mergeSort(ResultList list) {
        if (list == null || list.empty() || list.last()) {
            // If the list is empty or has one element, it's already sorted
            return;
        }
    
        // Create two new lists for splitting
        ResultList left = new ResultList();
        ResultList right = new ResultList();
    
        // Split the list into two halves
        split(list, left, right);
    
        // Recursively sort the two halves
        mergeSort(left);
        mergeSort(right);
    
        // Merge the sorted halves back into the original list
        merge(list, left, right);
    }
    
    private static void split(ResultList original, ResultList left, ResultList right) {
        original.findFirst();
    
        // Count the total number of elements
        int size = 0;
        while (!original.last()) {
            size++;
            original.findNext();
        }
        size++; // Include the last element
    
        int mid = size / 2;
    
        original.findFirst();
    
        // Populate the left list
        for (int i = 0; i < mid; i++) {
            left.insert(original.retrieve(), original.getFrequency());
            original.findNext();
        }
    
        // Populate the right list
        while (true) {
            right.insert(original.retrieve(), original.getFrequency());
            if (original.last()) break;
            original.findNext();
        }
    }
    
    private static void merge(ResultList result, ResultList left, ResultList right) {
        result.clear(); // Clear the result list to rebuild it with sorted elements
    
        left.findFirst();
        right.findFirst();
    
        while (!left.empty() && !right.empty()) {
            // Compare frequencies and insert the smaller element into the result list
            if (left.getFrequency() <= right.getFrequency()) {
                result.insert(left.retrieve(), left.getFrequency());
                left.remove();
                if (!left.empty()) {
                    left.findFirst(); // Reset position after remove
                }
            } else {
                result.insert(right.retrieve(), right.getFrequency());
                right.remove();
                if (!right.empty()) {
                    right.findFirst(); // Reset position after remove
                }
            }
        }
    
        // Append remaining elements from the left list
        while (!left.empty()) {
            result.insert(left.retrieve(), left.getFrequency());
            left.remove();
            if (!left.empty()) {
                left.findFirst();
            }
        }
    
        // Append remaining elements from the right list
        while (!right.empty()) {
            result.insert(right.retrieve(), right.getFrequency());
            right.remove();
            if (!right.empty()) {
                right.findFirst();
            }
        }
    }
    

}
