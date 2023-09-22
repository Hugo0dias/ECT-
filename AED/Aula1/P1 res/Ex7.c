#include <stdio.h>


int main() {
    int a[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    print_array("a", a);

    int b[12] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //print_array("b", b);
    cumSum(a, b);
    print_array("b", b);
}

void printArray(char s[], int a[]) {

    printf("%s :\n", s);
    for (int i = 0; i<sizeof(a), i++){
        printf("%d ", a[i]);    
    }
    printf("\n");
}

void cumSum(int a[], int b[]){

    int c = 0;
    for(int i = 0; i<sizeof(a); i++){
        c += a[i];
        b[i] = c;
    }
}


//erro