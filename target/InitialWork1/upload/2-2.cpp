
#include <stdio.h>
#include<time.h>
int main()
{
    int x;
    int n=0;
    int min;
    int max;
    int s=0;
    /*int x,n=0, min, max, s=0;*/
    while(scanf("%d", &x)==1){
        s+=x;
        if(x<min)min=x;
        if(x>max)max=x;
        n++;
    }


    printf("%d %d %.3f\n",min, max, (double)s/n);
    return 0;

}
