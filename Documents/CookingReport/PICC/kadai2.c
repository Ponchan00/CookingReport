#include<pic.h>
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
char a = 0;
int b = 0;
char ta[6]={0b0001,0b0110,0b1111,0b1000,0b0010,0b0100};
void main()
{
	GIE=1;
	T0IE=1;
	T0IF=0;
	TMR0=0;
	T0CS=0;
	PSA=0;
	PS0=1;
	PS1=1;
	PS2=1;
	TRISB=0;
	while(1);
}
interrupt isr()
{
	T0IF=0;
	a=a+1;
	if(a==19)
	{
		PORTB=ta[b];
		b=b+1;
		if(b==6){
			b=0;
			}
			a=0;
		}
}