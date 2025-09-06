// Simple IPv6 TCP client that connects to a server, sends keyboard input, and prints server responses

#include <stdio.h>
#include <string.h>
#include <netinet/ip.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

#define PORT 4030

int main(int argc, char** argv)
{
	int sfd, i, n;
	fd_set read_fdset;
	struct sockaddr_in6 server;
	char puffer[128];

	sfd = socket(AF_INET6,SOCK_STREAM,0);
	if(sfd == -1)
	{
		perror("Socket:");
		exit(EXIT_FAILURE);
	}

	server.sin6_family = AF_INET6;
	(void) inet_pton(AF_INET6, "::1", &server.sin6_addr);
	if (argc > 1)
	{
		if (inet_pton(AF_INET6, argv[1], &server.sin6_addr) == -1)
			perror("Inkorrekte Serveradresse:");
	}

	server.sin6_port = htons(PORT);
	
	if(connect(sfd,(struct sockaddr*) &server,sizeof(server)) == -1)
	{
		perror("Fehler bei connect()");
		close(sfd);
		exit(EXIT_FAILURE);
	}
	
	while(1)
	{
		FD_ZERO(&read_fdset);
		FD_SET(sfd,&read_fdset);
		FD_SET(0,&read_fdset);
		
		i = select(sfd+1, &read_fdset, NULL, NULL, NULL);
		if (i == -1)
		{
			perror("select()");
			exit(EXIT_FAILURE);
		}
		else
		{
			if(FD_ISSET(0, &read_fdset))
			{
				n = read(0, puffer, sizeof(puffer));
				if ((n == 0) || ((n >= 4) && (strncmp(puffer, "quit", 4) == 0)))
				{ 
					close(sfd);
					exit(EXIT_SUCCESS);
				}
				else
					write(sfd, puffer, n);
			}
				
			if(FD_ISSET(sfd, &read_fdset))
			{
				n = read(sfd,puffer, sizeof(puffer));
				if (n > 0)	{
					puffer[n] = '\0';
					printf("%s",puffer);
				}
				else
				{
				 	printf("Der Server hat die Verbindung geschlossen!\n");
					exit(EXIT_FAILURE);
				}
			}
		}
	}
}
