#include<pic.h>
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
char a = 0;
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
		if(PORTB==0)
		{
			PORTB=0b1111;
		}
		else
		{
			PORTB=0;
			}
			a=0;
		}
}