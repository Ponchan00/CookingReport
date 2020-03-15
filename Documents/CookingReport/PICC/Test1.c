#include<pic.h>
__CONFIG(0xfff2);
void ioport(void);
void main()
{
	unsigned char port_data;
	ioport();
	while(1)
	{
		port_data = PORTA;
		PORTB = port_data;
		}
}
void ioport(void)
{
	TRISA = 0x0f;
	TRISB = 0;
}