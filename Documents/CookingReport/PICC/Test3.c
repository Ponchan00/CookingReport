#include<pic.h>
__CONFIG(HS & WDTDIS & PWRTEN & UNPROTECT);
void ioport(void);
void main()
{
	ioport();
	while(1)
	{
		RB0 = 0;
		RB1 = 1;
		RB2 = 1;
		RB3 = 0;
	}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}