// Simple IPv4 UDP server that receives messages and replies

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

#define PORT 4030

int main(int argc, char* argv[])
{
	int i, k, n, sfd;
	struct sockaddr_in instruct;
	socklen_t len;
	char puffer[1472];

	sfd = socket(AF_INET,SOCK_DGRAM,0);
	if(sfd == -1)
	{
		perror("Socket kann nicht geöffnet werden");
		exit(EXIT_FAILURE);
	}

	memset(&instruct, 0, sizeof (instruct));
	instruct.sin_family = AF_INET;
	instruct.sin_port = htons(PORT);
	instruct.sin_addr.s_addr = INADDR_ANY;
	
	if(bind(sfd, (struct sockaddr *) &instruct,sizeof(instruct)) != 0)
	{
		perror("Fehler bei bind():");
		close(sfd);
		exit(EXIT_FAILURE);
	}
	
	while (1)
	{
		len = sizeof(instruct);
		n = recvfrom(sfd, puffer, sizeof(puffer), 0, (struct sockaddr *) &instruct, &len);
		if (n == -1)
		{
			perror("Fehler bei recvfrom():");
			exit(EXIT_FAILURE);
		}

		sprintf(puffer, "%s", "Hallo zurück!");
		sendto(sfd, puffer, strlen(puffer), 0, (struct sockaddr *) &instruct, sizeof(instruct));
	}		
}
