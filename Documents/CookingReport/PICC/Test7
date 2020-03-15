#include<pic.h>
#include"delay.h"
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void delaym(int);
void main()
{
	char a;
	char ta[6]={0b0001,0b0110,0b1111,0b1000,0b0010,0b0100};
	ioport();
	while(1)
	{
		PORTB = 0;
		while(RA0 == 0)
		{
			for(a=0;a<=5;a++)
			{
				PORTB=ta[a];p
				delaym(500);
			}
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
	while(a>255)
	{
		DelayMs(255);
		a=a-255;
	}	
	DelayMs(a);
}