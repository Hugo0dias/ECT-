#include <stdio.h>
#include <conio.h>
int main(void)
{

  printf(" --- TIPO ---|--- BYTES ---\n");
  printf(" void .......: %d bytes\n", sizeof(void));
  printf(" void *......: %d bytes\n", sizeof(void *));
  printf(" int.........: %d bytes\n", sizeof(int));
  printf(" long........: %d bytes\n", sizeof(long));
  printf(" float ......: %d bytes\n", sizeof(float));
  printf(" double......: %d bytes\n", sizeof(double));
  printf(" char........: %d bytes\n", sizeof(char));
  printf(" long long...: %d bytes\n", sizeof(long long));
  printf(" short.......: %d bytes\n", sizeof(short));
  
  getch();
  return 0;
}


//não percebi a arquitetura 64 bits, como é que altero