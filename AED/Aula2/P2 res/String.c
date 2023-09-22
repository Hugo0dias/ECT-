#include <strings.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int main(void){
    int op;
    char P[500];
    char S[500];
    char copy[500];
    printf("Escreva a primeira String: ");
    scanf("%s", &P);
    printf("Escreva a segunda String: ");
    scanf("%s", &S);

    printf("Numero de letras : %d \n", LetterCount(P));
    printf("Numero de letras maiusculas : %d \n", UpperCount(S));
    ConvertLetter(P);
    Comparation(P, S);
    printf("Qual a String que pretende copiar?\n");
    printf("(1) - Primeira\n");
    printf("(2) - Segunda\n");
    scanf("%d", &op);
    if(op == 1){
        CopyString(P, copy);
    } else {
        CopyString(S, copy);
    }
    printf("A String copiada foi %s\n", copy);
    ConcatenationStr(S, P);
    
    return 0;
}

int LetterCount(char c[500]) {
    int count = 0;
    for(int i=0; i < strlen(c); i++){
        if(isalpha(c[i])){
            count++;
        }
    }    
    return count;
}

int UpperCount(char c[500]) {
    int count = 0;
    for(int i=0; i < strlen(c); i++){
        if(isupper(c[i])){
            count++;
        }
    }    
    return count;
}

void ConvertLetter(char c[500]){

    for(int i=0; i < strlen(c); i++){
        if(isupper(c[i])){
            c[i] = tolower(c[i]);
        }
    }
    printf("String em minusculas %s \n", c);

}

void Comparation(char c[500], char t[500]){

    ConvertLetter(c);
    ConvertLetter(t);
    int comp = strcmp(c, t);

    if (comp == 0){
        printf("As Strings são identicas \n");
    } else if (comp < 0) {
        printf("A String 1 é maior do que a String 2 \n");
    } else {
        printf("A String 2 é maior do que a String 1 \n");
    }
}


void CopyString(char c[500], char t[500]){
    strcpy(t, c);
}

void ConcatenationStr(char c[500], char t[500]){
    char a[500];
    char b[500];
    strcpy(a, c);
    strcpy(b, c);
    strcat(b, a);
    printf("%c\n", b);
}





// resolver concatenar

/* 

 int* x;
 *x = 1; 
 O ponteiro nao esta definido logo e impossivel afirmar isto

*/
