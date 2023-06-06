## Implementation of Client and Server Data Exchange 

  ## Server Side 
   For the server side implementation, a TCP server was created. This server takes a port as input and listens on that port on localhost for connections. For each connection a task is submitted to a threadpoool to serve that particular client's requests. The threadpool in this case, has 10 threads and therefore currently the server is limited to serve 10 clients at a time. 



 ## Client side
  For the client side, a simple  client has been implemented, that takes in port and host name. This then establishes a connection to the server running on the particular port and host name. The client then starts sending "ping" messages every second to the server and prints "Ping sent" once the message is sent and "Received pong" if it recieves the response "Pong", otherwise throws an exception. 

 ## Prerequisites for running
 - Install gradle by following instructions in [here](https://gradle.org/install/)
 ## Running the Server
   - Run `cd server`
   - Run `gradle run --args="8080"` to start the server.

 ## Running the Client
   - Run `cd client`
   - Run `gradle run --args "localhost 8080"` to start the client. 



 ## Limitations with this code 
  With the current code, only upto 10 clients can be connected to the server at a time. This is because the threadpool is limited to 10 threads currently. 
  
  One of the ways, we can mitigate this is by spawning a seperate thread for each connection but that would eventually consume most of the memeory on the server if many clients connected at once as each thread requires some amount of memory to support. 

  We could possibly fix this by using the new Java virtual threads feature that is in beta currently because virtual threads are much lighter weight than Java threads. We could spawn a virtual thread instead of a real thread for each client. 
  