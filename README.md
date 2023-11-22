# Authentication Lab

## Goal
The purpose of this laboratory exercise is to provide hands on experience with the development of a user authentication mechanism. Java is the official language of instruction for computer science at DTU Compute, which is why it has been chosen for this assignment.

## Java Authentication
The traditional security mechanisms for the Java Virtual Machine (JVM) provide a means to enforce access controls based on where the code was downloaded from (aka. the code base) and who signed it. These access controls are needed because of the distributed nature of the Java platform where applications may consist of packages that are dynamically downloaded from different software providers and where an applet can be downloaded over a public network and then run locally.

However, the first versions of the Java 2 platform did not provide a way to enforce similar access controls based on the principal who runs the code. To provide this type of access control, the Java 2 security architecture requires the following:

support for authentication of principals (determining who is actually running the code);
extensions to the existing authorization components to enforce new access controls based on the authenticated principal.
The Authentication lab addresses the first of these problems by designing and implementing a simple authentication mechanism for a client/server application. Students who have not previously implemented an RMI application may consult the Java RMI Tutorial from Oracle or consult this short video on YouTube.

## Lab Work
The first task is to write a simple client/server application using RMI. The example used in this lab is a mock-up of a simple authenticated print server, such as a print server installed in a small company.
The print server must support the following operations:

```
print(String filename, String printer);   // prints file filename on the specified printer
queue(String printer);   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
topQueue(String printer, int job);   // moves job to the top of the queue
start();   // starts the print server
stop();   // stops the print server
restart();   // stops the print server, clears the print queue and starts the print server again
status(String printer);  // prints status of printer on the user's display
readConfig(String parameter);   // prints the value of the parameter on the print server to the user's display
setConfig(String parameter, String value);   // sets the parameter on the print server to value
These operations define the interface of the print server, but it is unnecessary to implement any printing capabilities for this lab, i.e. it is sufficient that the print server records the invocation of a particular operation in a logfile or prints it on the console. It must be possible to invoke all the print server operations defined in the interface from the client program.
```

This lab will design and implement a password based authentication mechanism for the print server, i.e. the print server must authenticate all requests from the client. For the purpose of this lab, it is not necessary to consider enrolment of users, i.e. authentication data structures can be populated by hand. The design and implementation of the print server must, however, consider the problems of password storage, password transport and password verification.

## Password Storage
In relation to password storage, three possible solution must be considered: passwords stored in a "system" file, passwords stored in a "public" file, where cryptography is used to ensure confidentiality and integrity of the stored data; and passwords stored in a data base management system; these options are outlined below.

System File Storing passwords in a system file relies on the operating system/file system protection mechanisms are used to ensure confidentiality and integrity of the stored data. This password file is normally not accessible to all users, so some mechanism (e.g. the SetUID mechanism in Unix) or system service is required to provide controlled access to the data stored in the file (similar to the DBMS storage described below.)
Public File Storing passwords in a public file that can be read (but not necessarily written) by all users is the traditional way to store passwords on Unix systems. Confidentiality of the passwords is normally protected by cryptographic means, whereas integrity (e.g. binding users and passwords) may either be protected by the operating system/file system protection mechanism, i.e. normal users have no write access to the file (but how are passwords then updated?) or by cryptographic means.
DBMS Storing passwords in a database (often unencrypted) relies on the security architecture, e.g. the access control mechanism, implemented in the DBMS to provide confidentiality and integrity of the stored passwords.
The analysis must briefly discuss how each of these three solutions may be implemented in the given context and compare and contrast the security offered by each of the proposed implementations (these considerations must be documented in the lab report outlined below). In particular, the report must explain how the chosen solution prevents users from learning or changing the password of other users. Other possible solutions may also be included in the analysis if they are considered relevant. Based on the discussions above, a solution should be selected and the reasons behind documented.

Password Transport
An analysis of the password transport problem must include a discussion of how to implement both individual request authentication and authenticated sessions, where the initial authentication is used to define an authenticated session, which implicitly authenticates messages exchanged. If authentication of invocation requires transfer of any additional parameters between client and server, these should simply be added to the interface defined above.

## Password Verification
The password verification mechanism depends on the choice of password storage and must be explained in the report.

## General Comments
For the purpose of this lab, it is acceptable to assume that secure communication between client and server is ensured by other means. It must, however, be explicitly stated if this assumption is made and the specific guarantees required by the channel (e.g. confidentiality, integrity and/or availability) must be specified. It is also possible to protect communications by cryptographic means, in this case the relevant techniques and technologies must be identified and discussed and it must be implemented correctly in the application. A complete and correct implementation of secure communication will count positively in the assessment of the report, but only if everything else is well implemented and documented.

## Evaluation
This lab is an integral part of the course, which means that the report will be evaluated and contribute to your final grade. All positive contributions count, so it is always better to hand-in something than nothing, even if you are not personally satisfied by your results. The report should document your work, as defined above, and follow the normal structure of areport, and we recommend that you use the following structure:

1. Introduction (max 1 page)
The introduction should provide a general introduction to the problem of authentication in client/server applications. It should define the scope of the answer, i.e. explicitly state what problems are considered, and outline the proposed solution. Finally, it should clearly state which of the identified goals are met by the developed software.
2. Authentication (max 3 pages)
This section should provide a short introduction to the specific problem of password based authentication in client/server systems and analyse the problems relating to password storage (on the server), password transport (between client and server) and password verification.
3. Design and Implementation (max 3 pages including diagrams)
A software design for the proposed solution must be presented and explained, i.e. why is this particular design chosen. The implementation of the designed authentication mechanism in the client server application must also be outlined in this section.
4. Evaluation (max 2 pages)
This section should document that the requirements defined in Section 2 have been satisfied by the implementation. In particular, the evaluation should demonstrate that the user is always authenticated by the server before the service is invoked, e.g. the username and methodname may be written to a logfile every time a service is invoked.
The evaluation should provide a simple summary of which of the requirements are satisfied and which are not.
5. Conclusion (max 1 page)
The conclusions should summarize the problems addressed in the report and clearly identify which of the requirements are satisfied and which are not (a summary of Section 4). The conclusions may also include a brief outline of future work.
The full report should be limited to a maximum of 10 pages, excluding the source code. Source code for this lab should be included in a separate zip-archive.
The report and source code should be handed in electronicallyon Inside as indicated by the Authentication Lab assignment.

## Useful Links
[Java RMI Tutorial](https://docs.oracle.com/javase/tutorial/rmi/)

[Quick video introduction to RMI](https://www.youtube.com/watch?v=X-bL0S8b6C4)

---


# Access Control

## Goal
The purpose of this laboratory exercise is twofold: to provide hands on experience with the specification and enforcement of authorization policies and to provide an experimental framework for discussing access control policies. Students are assumed to have completed the Authentication lab.

## Access Control Scenario
Consider the print server scenario defined in the Authentication lab. The print server supports the following operations:

print(String filename, String printer);   // prints file filename on the specified printer
queue(String printer);   // lists the print queue on the user's display in lines of the form <job number>   <file name>
topQueue(String printer, int job);   // moves job to the top of the queue
start();   // starts the print server
stop();   // stops the print server
restart();   // stops the print server, clears the print queue and starts the print server again
status(String printer);  // prints status of printer on the user's display
readConfig(String parameter);   // prints the value of the parameter on the user's display
setConfig(String parameter, String value);   // sets the parameter to value
Not everybody working in the company has the same rights to access the print server. Alice is managing the print server, so she has the rights to perform all operations. Bob is the janitor who doubles as service technician, he has the rights to start, stop and restart the print server as well as inspect and modify the service parameters, i.e., invoke the status, readConfig and setConfig operations. Cecilia is a power user, who is allowed to print files and manage the print queue, i.e., use queue and topQueue as well as restart the print server when everything seems to be stuck. Finally, David, Erica, Fred and George are ordinary users who are only allowed to print files and display the print queue.

## Lab Work
The first task is to modify the prototype print server developed in the Authentication lab, so that it implements the necessary code to enforce the access control policy outlined above. This means that all registered users must be included in the password-file/-database defined in the Authentication lab. This first implementation should be based on an access control list for the print server, i.e. the print server is considered as a single object with the different methods as the possible operations. The access control list must be specified in an external policy file, or another form of external media, that is loaded when the print server starts, i.e. the policy must not be hard-coded into the program.

The second task is to identify roles and define a role hierarchy and permissions for each role, so that the access control policy outlined above can be implemented. The third task is to develop a second prototype, based on the prototype developed in the authentication lab, which enforces the access control policy using Role Based Access Control, i.e. based on the role hierarchy and permissions defined in Task 2. The role hierarchy must be specified in one or more external policy files, or the same form of external media used in Task 1, that is/are loaded when the print server starts.

Now consider the situation where Bob leaves the company and George takes over the responsibilities as service technician. At the same time, two new employees are hired: Henry, who should be granted the privileges of an ordinary user, and Ida who is a power user and should be given the same privileges as Cecilia.

The final task is to implement the necessary changes in the access control policy specifications of the two prototypes developed in this lab, so that they reflect the organisational changes in the company. The experience gained from these modifications will allow you to compare the management support for the two policy enforcement mechanisms, i.e. the flexibility and facility with which organisational changes can be reflected in the access control policy. This comparison must highlight the strengths and weaknesses of each implementation and discuss the expressive power and the ease of management supported by the two policy specification abstractions, e.g., which organisational changes are easily captured in both implementations and which are more easily specified in one implementation than in the other. 

## Evaluation
This lab is a mandatory part of the course, which means that you have to hand in a small report, which will be evaluated and counts toward your final grade. The report should follow the normal structure of a report, and we recommend that you use the following structure:

1. Introduction (max 1 page)
The introduction should provide a general introduction to the problem of access control in client/server applications. It should define the scope of the answer, i.e. explicitly state what problems are considered, and outline the proposed solution. Finally, it should clearly state which of the identified goals are met by the developed software.
2. Access Control Lists (max 2 pages)
This section should provide a short overview of the implementation of the access control lists and the motivation behind all non-trivial design choices.
Role Based Access Control (max 3 pages including diagrams)
This section should document the results of the role mining process performed in in Task 2 and provide a short overview of the implementation of the role based access control mechanism implemented in Task 3 along with the motivation behind all non-trivial design choices. In particular, it must describe the syntax used to specify the RBAC policy.
3. Evaluation (max 4 pages)
This section should document that the prototype enforces the access control policies defined in this assignment; both ACL and RBAC and both before and after the changes.
The evaluation should provide a simple summary of which of the requirements are satisfied and which are not.
4. Discussion (max 2 page)
This section documents the reflections and discussions of the final task.
5. Conclusion (max 1 page)
The conclusions should summarize the problems addressed in the report and clearly identify which of the requirements are satisfied and which are not (a summary of Section 4). The conclusions may also include a brief outline of future work.
The laboratory work will be assessed in the same way as the other reports on the course (i.e., you are welcome to hand in the report in groups). The page numbers indicated above are indicative only, but the full report should be limited to a maximum of around 15 pages, excluding the source code. Source code for this lab should be included in a separate zip-archive. 

The report and source code should be handed in electronically, using Campusnet, before 23.59 on Friday 1 December.

## Useful Links
[Role Based Access Control](http://csrc.nist.gov/groups/SNS/rbac/)

---

# How to run

run ApplicationServer.java, then Printer.java, finally Client.java


# Approach

## Authentication Lab

We will assume a safe communication between client and server, hence we will send unencrypted data (like plain-text passwords) in the channel.

1. the client is sending an authentication request to the server: **username password**
2. the server is authenticating the client, it will look in a system-file if the provided credentials are valid. The system-file serves as a database, it will contain information of username and password in every line: **username hash(password) salt**
3. the server generates a JWT (JSON Web Token) for the user and stores it in a hash map with a TTL: { hash(username + timestamp): timestamp + 10800000 (3h in ms) }
4. the server sends the generated JWT in plain text to the client
5. the client will use the received JWT to send every request (action)
6. with every request the server will look in the JWS hash map, to see if the current time stamp is less or equal then the timestamp registered (aka. if the JWS is still valid). If the timestamp is higher the server will throw an error and will send a response "authentication expired" to the client. The client will need to send another authentication request to the server and generate another JWT.

## Access Control

1. task 1: add new users to the database.txt file. Create an access control policy file (a users/operations matrix with users as rows and print server methods as columns, with 1/0 if the specific method is allowed or not fot that user). Implementation: load the file on server start, map the position of each method so that we can search later for a specific user and just look at the boolean in the location N (the same location of the method the user is trying to invoke). On user login, create a user hashmap with the generated token for that user, with key: method name, value: 1/0. We can verify if the user has permission checking this hashmap upon every method call in the server. (build a new hashmap for every user (taking the generated token as reference), the server should be able to handle multiple connections from clients at the same time). We are gonna have a permissionMap { token: userMap: { method name: 1/0} }

MENTION IN THE REPORT! Why we are not loading the entire policy file (as well as the user file) at runtime in a hashmap? because it doesn't scale if the database is very big, containing a lot of users.
2. task 2: define role hierarchy for access control:
	1. Manager (e.g., Alice): All operations: start(), stop(), restart(), status(), readConfig(), setConfig(), print(), queue(), topQueue()
	2. Technician (e.g., Bob): start(), stop(), restart(), status(), readConfig(), setConfig()
	3. PowerUser (e.g., Cecilia): print(), queue(), topQueue(), restart()
	4. User (e.g., David, Erica, Fred, George): print(), queue()

MENTION IN THE REPORT! In the report we should write those roles explicitly, showing the hierarchy maybe with a graph or something.
3. task 3: create a role based access control policy file (a roles/operations matrix with roles as rows and print server methods as columns, similar to the one from task 1). The implementation and how to read this file is also similar.
4. task 4: change the policy files manually:
   1. Bob deleted: UAC -> delete the row. RBAC/DB -> delete the row (maybe we want to keep it in the db for legacy reasons, but assign a new role like "Inactive").
   2. George becomes Technician: UAC -> change values for George (need to look at technician role permissions and copy past in George row). RBAC/DB -> change role to George user from "User" to "Technician"
   3. Create user Henry with role "User": UAC -> add row for Henry and add User permissions. RBAC/DB -> add new user to DB, generate salt and password
   4. Create user Ida with same role as Cecilia (PowerUser): UAC -> copy past Cecilia and change the username to Ida. RBAC/DB -> add new user to DB, generate salt and password

# Code structure

### Server

```
implements these methods:
	start()
	stop()
	restart()
	status()
	readConfig()
	setConfig()
	print()
	queue()
	topQueue()
	--- added by us:
	login()
	log()


VerificationService
```

### Client

```
connects to the server and executes methods
```

### Printer

```
handles the printing queues for every printer managed by the server
```


### Authentication
```
handles user authentication on the server, token generation and storage
```

### TokenVerifier
```
verifies authentication tokens generated for open sessions
```