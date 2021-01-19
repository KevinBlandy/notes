#include <stdio.h>
#include <stdlib.h>

void printArr(int[], size_t);
int getMinPosition(int[], int, size_t);
void selectSorted(int[], size_t );

int main(int argc, char **argv) {
	int arr[5] = {5,4,7,1,2};
	selectSorted(arr,5);
	printArr(arr,5);
	return EXIT_SUCCESS;
}
void printArr(int arr[],size_t size){
	for(int x = 0 ; x < size ; x++){
		printf("%d\n", arr[x]);
	}
}

int getMinPosition(int arr[], int start, size_t end) {
	int position = start;
	int value = arr[start];
	start++;
	for (; start < end; start++) {
		if (arr[start] < value) {
			position = start;
			value = arr[start];
		}
	}
	return position;
}

void selectSorted(int arr[], size_t size) {
	for (int i = 0; i < size; i++) {
		int index = getMinPosition(arr, i, size);
		int temp = arr[index];
		arr[index] = arr[i];
		arr[i] = temp;

	}
}


//python



def getMinIndex(arr, position):
    
    length = len(arr)
    
    min = arr[position]
    minIndex = position
    
    position += 1
    
    while(position < length):
        if arr[position] < min:
            min = arr[position]
            minIndex = position
        position += 1
    return minIndex


def selectSort(arr):
    length = len(arr)
    for i, v in enumerate(arr):
        minIndex = getMinIndex(arr, i)
        temp = arr[i]
        arr[i] = arr[minIndex]
        arr[minIndex] = temp


arr = [4, 5, 7, 8, 9, 6, 1]
selectSort(arr)
print(arr)
        
