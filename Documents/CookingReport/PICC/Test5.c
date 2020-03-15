#include<pic.h>
#include"delay.h"
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void delaym(int);
int indel=900;
void main()
{
	ioport();
	while(1)
	{
		PORTB = 0b1111;
		while(RA0 == 0)
		{
			PORTB=0;
			delaym(indel);
			PORTB=0b1111;
			delaym(indel);
		}
	}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}
void delaym(int a)
{
	while()a>255
	{
		DelayMs(255);
		a=a-255;
	}	
	DelayMs(a);
}
