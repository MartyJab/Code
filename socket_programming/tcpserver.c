// IPv6 TCP server that accepts multiple clients and relays messages between them using select()

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
	int i, k, n, listenSocket, clientSocket, sfd_max;
	struct sockaddr_in6 in6struct;
	socklen_t len;
	fd_set original_fdset, read_fdset;
	char puffer[1460];
	char sender[INET6_ADDRSTRLEN + 1];
	char port[9];
	const int eins = 1;

	listenSocket = socket(AF_INET6,SOCK_STREAM,0);
	if(listenSocket == -1)
	{
		perror("Socket kann nicht geöffnet werden:");
		exit(EXIT_FAILURE);
	}

	setsockopt(listenSocket, SOL_SOCKET, SO_REUSEADDR, &eins, sizeof(int));

	memset(&in6struct, 0, sizeof (in6struct));
	in6struct.sin6_family = AF_INET6;
	in6struct.sin6_port = htons(PORT);
	in6struct.sin6_addr = in6addr_any;
	
	if(bind(listenSocket, (struct sockaddr *) &in6struct,sizeof(in6struct)) != 0)
	{
		perror("Fehler bei bind():");
		close(listenSocket);
		exit(EXIT_FAILURE);
	}
	
	if (listen(listenSocket,10) == -1)
	{
		perror("Fehler bei listen():");
		close(listenSocket);
		exit(EXIT_FAILURE);
	}

	sfd_max = listenSocket;
	FD_ZERO (&original_fdset);
	FD_SET (listenSocket, &original_fdset);

	while (1)
	{
		read_fdset = original_fdset;
		n = select (sfd_max+1, &read_fdset, NULL, NULL, NULL);
		if (n == -1)
		{
			perror("Fehler bei select():");
			exit(EXIT_FAILURE);
		}
		
		if (FD_ISSET (listenSocket, &read_fdset))
		{
			len = sizeof(in6struct);
			clientSocket = accept (listenSocket,(struct sockaddr *) &in6struct,&len);
			if (clientSocket < 0)
			{
				perror ("accept");
				exit (EXIT_FAILURE);
			}
			inet_ntop (AF_INET6,&in6struct.sin6_addr,puffer,sizeof(puffer));
			fprintf (stderr,"TCP-Server: Neuer Client %s.\n",puffer);
			FD_SET(clientSocket, &original_fdset);
			if(clientSocket >= sfd_max)
				sfd_max = clientSocket;
		}
		
		for(int i = listenSocket + 1; i <= sfd_max; i++)
		{
			if(FD_ISSET(i,&read_fdset))
			{
				n = read(i, puffer, sizeof(puffer));
				if (n < 0)
					perror("Fehler bei read():");

				if (n == 0)
				{
					close(i);
					FD_CLR(i, &original_fdset);
					fprintf(stderr, "TCP-Server: Socket %d geschlossen.\n", i);
				}

				if (n > 0)
				{
					len = sizeof(in6struct);
					getpeername(i, (struct sockaddr *)&in6struct, &len);
					inet_ntop (AF_INET6, &in6struct.sin6_addr, sender, sizeof(sender));	
					sprintf(port, ",%d> ",ntohs(in6struct.sin6_port));

					for(k = listenSocket + 1; k <= sfd_max; k++)
					{
						if(FD_ISSET(k,&original_fdset) && (k != i))
						{
							(void) write(k, sender, strlen(sender));
							(void) writ
