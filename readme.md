Custom process event listener that pushes selected events in the form of messages to a JMS queue set up in the Wildfly/EAP instance that hosts jBPM. The published messages can be consumed by an external system to build the required audit logging as required. 

Also, demostrated in here is a scrip task that overides a process variable with the value obtained by JNDI look up for the given JNDI namespace that has been created in Wildfly using the below listed command. 

$ ./jboss-cli.sh
You are disconnected at the moment. Type 'connect' to connect to the server or 'help' for the list of supported commands.
[disconnected /] connect
[standalone@localhost:9990 /] /subsystem=naming/binding=java\:global\/ws\/props\/app-version:add(binding-type=simple,type=java.lang.String,value="1.2.5p3")


