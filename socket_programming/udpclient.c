// Simple IPv4 UDP client that sends a message and waits for a server reply with timeout and retries

#include <stdio.h>
#include <string.h>
#include <netinet/ip.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/time.h>

#define PORT 4030

int main(int argc, char* argv[])
{
	int i, k, n, sfd;
	struct sockaddr_in server;
	fd_set fds;
	socklen_t len;
	struct timeval tv;
	char puffer[1472];

	sfd = socket(AF_INET,SOCK_DGRAM,0);
	if(sfd == -1)
	{
		perror("Socket kann nicht geöffnet werden");
		exit(EXIT_FAILURE);
	}

	server.sin_family = AF_INET;
	(void) inet_pton(AF_INET, "127.0.0.1", &server.sin_addr);
	if (argc > 1)
	{
		if (inet_pton(AF_INET, argv[1], &server.sin_addr) == -1)
			perror("Inkorrekte Serveradresse:");
	}
	server.sin_port = htons(PORT);

	int versuche = 3;
	while (versuche)
	{
		tv.tv_sec = 5;
		tv.tv_usec = 0;
		strcpy (puffer, "Hallo vom Client!");
		sendto(sfd, puffer, strlen(puffer), 0, (struct sockaddr *) &server, sizeof(server));

		FD_ZERO(&fds);
		FD_SET(sfd, &fds);
		n = select(sfd+1, &fds, NULL, NULL, &tv);
		if (n > 0)
		{
			len = sizeof(server);
			n = recvfrom(sfd, puffer, sizeof(puffer), 0, (struct sockaddr *) &server, &len);
			if (n == -1)
			{
				perror("Fehler bei recvfrom():");
				exit(EXIT_FAILURE);
			}
			puffer[n] = '\n';
			puffer[n+1] = '\0';
			printf(puffer);
			break;		
		} else
		{ 
			printf("Timeout!\n");
			versuche--;
		}
	}	
	if (versuche)
		exit(EXIT_SUCCESS);
	else
		exit(EXIT_FAILURE);	
}
