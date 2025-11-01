package Punto1;

import java.util.ArrayList;

public class SmallerElementsCount {
    class Item {

        int val;
        int index;

        public Item(int val, int index)
        {
            this.val = val;
            this.index = index;
        }
    }

    // Function to count the number of
    // smaller elements on right side
    public ArrayList<Integer> countSmall(int[] A)
    {

        int len = A.length;
        Item[] items = new Item[len];

        for (int i = 0; i < len; i++) {
            items[i] = new Item(A[i], i);
        }

        int[] count = new int[len];
        mergeSort(items, 0, len - 1, count);
        ArrayList<Integer> res = new ArrayList<>();

        for (int i : count) {
            res.add(i);
        }
        return res;
    }

    // Function for Merge Sort
    private void mergeSort(Item[] items,
                           int low, int high,
                           int[] count)
    {

        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;
        mergeSort(items, low, mid, count);
        mergeSort(items, mid + 1, high, count);
        merge(items, low, mid, mid + 1, high, count);
    }

    // Utility function that merge the array
    // and count smaller element on right side
    private void merge(Item[] items, int low,
                       int lowEnd, int high,
                       int highEnd, int[] count)
    {
        int m = highEnd - low + 1;
        Item[] sorted = new Item[m];
        int rightCounter = 0;
        int lowPtr = low, highPtr = high;
        int index = 0;

        // Loop to store the count of smaller
        // Elements on right side when both
        // Array have some elements
        while (lowPtr <= lowEnd && highPtr <= highEnd) {
            if (items[lowPtr].val > items[highPtr].val) {
                rightCounter++;
                sorted[index++] = items[highPtr++];
            }
            else {
                count[items[lowPtr].index] += rightCounter;
                sorted[index++] = items[lowPtr++];
            }
        }

        // Loop to store the count of smaller
        // elements in right side when only
        // left array have some element
        while (lowPtr <= lowEnd) {
            count[items[lowPtr].index] += rightCounter;
            sorted[index++] = items[lowPtr++];
        }

        // Loop to store the count of smaller
        // elements in right side when only
        // right array have some element
        while (highPtr <= highEnd) {
            sorted[index++] = items[highPtr++];
        }

        System.arraycopy(sorted, 0, items, low, m);
    }

    // Utility function that prints
    // out an array on a line
    void printArray(ArrayList<Integer> countList)
    {

        for (Integer i : countList)
            System.out.print(i + " ");

        System.out.println("");
    }

    // Driver Code
    public static void main(String[] args)
    {
        SmallerElementsCount cntSmall = new SmallerElementsCount();
        int arr[] = { 10, 9, 5, 2, 7, 6, 11, 0, 2 };
        int n = arr.length;
        ArrayList<Integer> countList
            = cntSmall.countSmall(arr);
        cntSmall.printArray(countList);
    }
}